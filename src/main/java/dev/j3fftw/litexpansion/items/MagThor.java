package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

public class MagThor extends SlimefunItem {

    public MagThor() {
        super(Items.LITEXPANSION, Items.MAG_THOR, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT, Items.THORIUM, SlimefunItems.MAGNESIUM_INGOT,
                SlimefunItems.ZINC_INGOT, null, null,
                null, null, null
            }
        );
    }

}
