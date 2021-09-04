package dev.j3fftw.litexpansion.armor;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.machine.multiblock.MetalForge;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.inventory.ItemStack;

public class ElectricChestplate extends SlimefunItem implements Rechargeable {

    public ElectricChestplate() {
        super(Items.LITEXPANSION, Items.ELECTRIC_CHESTPLATE, MetalForge.RECIPE_TYPE, new ItemStack[] {
            Items.MAG_THOR, null, Items.MAG_THOR,
            Items.MAG_THOR, SlimefunItems.BIG_CAPACITOR, Items.MAG_THOR,
            Items.MAG_THOR, Items.MAG_THOR, Items.MAG_THOR
        });
    }

    @Override
    public float getMaxItemCharge(ItemStack item) {
        return 8192;
    }
}
