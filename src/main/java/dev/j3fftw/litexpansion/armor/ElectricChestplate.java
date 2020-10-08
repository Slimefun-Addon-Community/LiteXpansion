package dev.j3fftw.litexpansion.armor;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.machine.MetalForge;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

public class ElectricChestplate extends SlimefunItem implements Rechargeable {

    public ElectricChestplate() {
        super(Items.LITEXPANSION, Items.ELECTRIC_CHESTPLATE, MetalForge.RECIPE_TYPE, new ItemStack[] {
            Items.MAG_THOR, null, Items.MAG_THOR,
            Items.MAG_THOR, SlimefunItems.SMALL_CAPACITOR, Items.MAG_THOR,
            Items.MAG_THOR, Items.MAG_THOR, Items.MAG_THOR
        });
    }

    @Override
    public float getMaxItemCharge(ItemStack item) {
        return 250;
    }
}
