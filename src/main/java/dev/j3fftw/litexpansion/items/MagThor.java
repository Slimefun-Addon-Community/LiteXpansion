package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.inventory.ItemStack;

public class MagThor extends UnplaceableBlock {

    public MagThor() {
        super(Items.LITEXPANSION, Items.MAG_THOR, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT, Items.THORIUM, SlimefunItems.MAGNESIUM_INGOT,
                SlimefunItems.ZINC_INGOT, null, null,
                null, null, null
            }
        );
    }
}
