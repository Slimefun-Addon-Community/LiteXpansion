package dev.j3fftw.litexpansion.armor;

import dev.j3fftw.litexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ChargableItem;
import org.bukkit.inventory.ItemStack;

public class ElectricChestplate extends ChargableItem {

    public ElectricChestplate() {
        super(Items.LITEXPANSION, Items.ELECTRIC_CHESTPLATE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MAG_THOR, null, Items.MAG_THOR,
            Items.MAG_THOR, Items.MAG_THOR, Items.MAG_THOR,
            Items.MAG_THOR, Items.MAG_THOR, Items.MAG_THOR
        });
    }
}
