package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.utils.Utils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class RubberSynthesizer extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiteXpansion.getInstance(), "scrap_machine"), Items.RUBBER_SYNTHESIZER_MACHINE
    );

    private static final int INPUT_SLOT = 11;
    private static final int OUTPUT_SLOT = 15;
    private static final int PROGRESS_SLOT = 13;
    private static final int PROGRESS_AMOUNT = 26; // Divide by 2 for seconds it takes
    public static final int ENERGY_CONSUMPTION = 20_000 / PROGRESS_AMOUNT;
    // this line below keeps going to the top whenever hitting ctrl+alt+L
    // I don't know how to fix
    public static final int CAPACITY = ENERGY_CONSUMPTION * 5;
    private static final Map<BlockPosition, Integer> progress = new HashMap<>();

    private static final CustomItem progressItem = new CustomItem(Material.FIRE_CHARGE, "&7Idle");

    public RubberSynthesizer() {
        super(Items.LITEXPANSION, Items.RUBBER_SYNTHESIZER_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.REINFORCED_PLATE,
                new ItemStack(Material.PISTON), Items.MACHINE_BLOCK, new ItemStack(Material.PISTON),
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.REINFORCED_PLATE
            });
        setupInv();
    }

    private void setupInv() {
        createPreset(this, "&6Rubber Synthesizer", blockMenuPreset -> {
            for (int i = 0; i < 27; i++)
                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());

            blockMenuPreset.addItem(INPUT_SLOT, null, (player, i, itemStack, clickAction) -> true);
            Utils.putOutputSlot(blockMenuPreset, OUTPUT_SLOT);

            blockMenuPreset.addItem(PROGRESS_SLOT, progressItem);
        });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                RubberSynthesizer.this.tick(b);
            }

            public boolean isSynchronized() {
                return false;
            }
        });
    }

    private void tick(@Nonnull Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b);
        if (inv == null) return;

        @Nullable final ItemStack input = inv.getItemInSlot(INPUT_SLOT);
        @Nullable final ItemStack output = inv.getItemInSlot(OUTPUT_SLOT);
        if (input == null || input.getType() == Material.AIR
            || !SlimefunUtils.isItemSimilar(input, SlimefunItems.OIL_BUCKET, true)
            || (output != null
            && (output.getType() != Items.RUBBER.getType()
            || output.getAmount() == output.getMaxStackSize()
            || !Items.RUBBER.getItem().isItem(output)))
        ) return;

        final BlockPosition pos = new BlockPosition(b.getWorld(), b.getX(), b.getY(), b.getZ());
        int currentProgress = progress.getOrDefault(pos, -1);

        // Process first tick - remove an input and put it in map.
        if (currentProgress == -1 && takePower(b)) {
            progress.put(pos, 0);
            return;
        }

        // No progress and no input item, no tick needed. Or if there was no power (but can be processed)
        if (currentProgress == -1 || !takePower(b)) return;

        if (currentProgress == PROGRESS_AMOUNT) {
            inv.consumeItem(INPUT_SLOT);
            if (output != null && output.getAmount() > 0)
                output.setAmount(output.getAmount() + 1);
            else {
                inv.replaceExistingItem(OUTPUT_SLOT, Items.RUBBER.clone());
            }
            progress.remove(pos);
            ChestMenuUtils.updateProgressbar(inv, PROGRESS_SLOT, PROGRESS_AMOUNT, PROGRESS_AMOUNT, progressItem);
        } else {
            progress.put(pos, ++currentProgress);
            ChestMenuUtils.updateProgressbar(inv, PROGRESS_SLOT, PROGRESS_AMOUNT - currentProgress, PROGRESS_AMOUNT,
                progressItem);
        }
    }

    private boolean takePower(@Nonnull Block b) {
        if (getCharge(b.getLocation()) < ENERGY_CONSUMPTION) return false;
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
        return new int[] {INPUT_SLOT};
    }

    @Override
    public int[] getOutputSlots() {
        return new int[] {OUTPUT_SLOT};
    }
}
