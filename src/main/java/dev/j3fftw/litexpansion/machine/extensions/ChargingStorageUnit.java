package dev.j3fftw.litexpansion.machine.extensions;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class ChargingStorageUnit extends Capacitor implements InventoryBlock {

    private static final int INPUT_SLOT = 13;

    private final int jPerTick;

    @ParametersAreNonnullByDefault
    protected ChargingStorageUnit(Category category, int capacity, int jPerTick, SlimefunItemStack item,
                              RecipeType recipeType, ItemStack[] recipe
    ) {
        super(category, capacity, item, recipeType, recipe);
        this.jPerTick = jPerTick;

        setupInv();
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                doTick(b);
            }

            public boolean isSynchronized() {
                return false;
            }
        });
    }

    public abstract String getTitle();

    @Override
    public int[] getInputSlots() {
        return new int[] { INPUT_SLOT };
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    private void setupInv() {
        createPreset(this, getTitle(), blockMenuPreset -> {
            for (int i = 0; i < 27; i++) {
                if (i == INPUT_SLOT) continue;
                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
        });

        registerBlockHandler(this.getId(), (p, b, tool, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            if (inv != null) {
                inv.dropItems(b.getLocation(), this.getInputSlots());
            }
            return true;
        });
    }

    private void doTick(@Nonnull Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b);
        if (inv == null) {
            return;
        }

        final ItemStack input = inv.getItemInSlot(INPUT_SLOT);
        final SlimefunItem item = SlimefunItem.getByItem(input);
        if (item instanceof Rechargeable) {
            ((Rechargeable) item).addItemCharge(input, this.jPerTick);
        }
    }
}





