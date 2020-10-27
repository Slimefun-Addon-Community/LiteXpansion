package dev.j3fftw.litexpansion.objects;

import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * A class that can be used to register {@link SlimefunItem}
 * that use the Dye material. Items registered using
 * this class can not interact with blocks or entities.
 *
 * @author NCBPFluffyBear
 */
public class DyeItem extends UnplaceableBlock {

    public DyeItem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }
}
