package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ChargableItem;
import org.bukkit.inventory.ItemStack;

public class NanoBlade extends ChargableItem {

    public NanoBlade() {
        super(Items.LITEXPANSION, Items.NANO_BLADE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, Items.MAG_THOR, null,
                null, Items.MAG_THOR, null,
                null, SlimefunItems.ADVANCED_CIRCUIT_BOARD, null
            }
        );
    }

}
