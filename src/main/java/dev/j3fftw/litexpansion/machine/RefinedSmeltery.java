package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class RefinedSmeltery extends CraftingMultiBlock {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiteXpansion.getInstance(), "refined_smeltery"),
        Items.REFINED_SMELTERY,
        "",
        "&7Used to refine ingots"
    );

    private static final ItemStack stone_bricks = new ItemStack(Material.STONE_BRICKS);

    public RefinedSmeltery() {
        super(Items.LITEXPANSION, Items.REFINED_SMELTERY, new ItemStack[] {
            null, new ItemStack(Material.STONE_BRICK_WALL), null,
            stone_bricks, new ItemStack(Material.DISPENSER), stone_bricks,
            null, new ItemStack(Material.FLINT_AND_STEEL), null
        }, new ItemStack[0], BlockFace.DOWN);
    }

    @Override
    public void onSuccessfulCraft(@Nonnull Block b) {
        Block fire = b.getRelative(BlockFace.DOWN, 2);
        fire.setType(Material.AIR);
    }
}

