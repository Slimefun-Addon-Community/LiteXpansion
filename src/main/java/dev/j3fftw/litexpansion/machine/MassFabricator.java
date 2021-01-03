package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;
import dev.j3fftw.extrautils.utils.Utils;
import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class MassFabricator extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiteXpansion.getInstance(), "mass_fabricator"), Items.MASS_FABRICATOR_MACHINE
    );

    public static final int ENERGY_CONSUMPTION = 16_666;
    public static final int CAPACITY = ENERGY_CONSUMPTION * 3;
    private static final int[] INPUT_SLOTS = new int[] {10, 11};
    private static final int OUTPUT_SLOT = 15;
    private static final int PROGRESS_SLOT = 13;
    private static final int PROGRESS_AMOUNT = 200; // Divide by 2 for seconds it takes

    private static final Map<BlockPosition, Integer> progress = new HashMap<>();

    private static final CustomItem progressItem = new CustomItem(Items.UU_MATTER.getType(), "&7Progress");

    private static final ItemStack plate = SlimefunItems.REINFORCED_PLATE;
    private static final ItemStack circuitBoard = SlimefunItems.ADVANCED_CIRCUIT_BOARD;

    public MassFabricator() {
        super(Items.LITEXPANSION, Items.MASS_FABRICATOR_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            plate, circuitBoard, plate,
            circuitBoard, Items.MACHINE_BLOCK, circuitBoard,
            plate, circuitBoard, plate
        });
        setupInv();
    }

    private void setupInv() {
        createPreset(this, "&5Mass Fabricator", blockMenuPreset -> {
            for (int i = 0; i < 27; i++) {
                if (i == INPUT_SLOTS[0] || i == INPUT_SLOTS[1]) continue;
                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            Utils.putOutputSlot(blockMenuPreset, OUTPUT_SLOT);
            blockMenuPreset.addItem(PROGRESS_SLOT, progressItem);
            blockMenuPreset.addMenuClickHandler(PROGRESS_SLOT, ChestMenuUtils.getEmptyClickHandler());
        });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                MassFabricator.this.tick(b);
            }

            public boolean isSynchronized() {
                return false;
            }
        });
    }

    private void tick(@Nonnull Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b);
        if (inv == null) {
            return;
        }

        // yes this is ugly shush
        @Nullable ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);
        @Nullable ItemStack input2 = inv.getItemInSlot(INPUT_SLOTS[1]);
        @Nullable final ItemStack output = inv.getItemInSlot(OUTPUT_SLOT);
        if (output != null && (output.getType() != Items.UU_MATTER.getType()
            || output.getAmount() == output.getMaxStackSize()
            || !Items.UU_MATTER.getItem().isItem(output))) {
            return;
        }

        if (!Items.SCRAP.getItem().isItem(input)) {
            input = null;
        }
        if (!Items.SCRAP.getItem().isItem(input2)) {
            input2 = null;
        }
        if (input == null && input2 == null) {
            return;
        }

        final BlockPosition pos = new BlockPosition(b.getWorld(), b.getX(), b.getY(), b.getZ());
        int currentProgress = progress.getOrDefault(pos, 0);

        if (!takePower(b)) {
            return;
        }

        // Process first tick - remove an input and put it in map.
        if (currentProgress != PROGRESS_AMOUNT) {
            if (input != null)
                inv.consumeItem(INPUT_SLOTS[0]);
            else
                inv.consumeItem(INPUT_SLOTS[1]);
            progress.put(pos, ++currentProgress);
            ChestMenuUtils.updateProgressbar(inv, PROGRESS_SLOT, PROGRESS_AMOUNT - currentProgress,
                PROGRESS_AMOUNT, progressItem);
        } else {
            if (output != null && output.getAmount() > 0) {
                output.setAmount(output.getAmount() + 1);
            } else {
                inv.replaceExistingItem(OUTPUT_SLOT, Items.UU_MATTER.clone());
            }
            progress.remove(pos);
            ChestMenuUtils.updateProgressbar(inv, PROGRESS_SLOT, PROGRESS_AMOUNT, PROGRESS_AMOUNT, progressItem);
        }
    }

    private boolean takePower(@Nonnull Block b) {
        if (getCharge(b.getLocation()) < ENERGY_CONSUMPTION) {
            return false;
        }
        removeCharge(b.getLocation(), ENERGY_CONSUMPTION);
        return true;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return new int[] {OUTPUT_SLOT};
    }
}
