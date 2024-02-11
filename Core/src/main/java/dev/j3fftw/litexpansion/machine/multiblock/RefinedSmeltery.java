package dev.j3fftw.litexpansion.machine.multiblock;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.machine.extensions.CraftingMultiBlock;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
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

    private static final ItemStack STONE_BRICKS = new ItemStack(Material.STONE_BRICKS);

    public RefinedSmeltery() {
        super(Items.LITEXPANSION, Items.REFINED_SMELTERY, new ItemStack[] {
            null, new ItemStack(Material.STONE_BRICK_WALL), null,
            STONE_BRICKS, new ItemStack(Material.DISPENSER), STONE_BRICKS,
            null, new ItemStack(Material.FLINT_AND_STEEL), null
        }, new ItemStack[0], BlockFace.DOWN);
    }

    @Override
    public Block getSpecialBlock(Block dispenser) {
        return dispenser.getRelative(BlockFace.DOWN);
    }

    @Override
    public boolean removeSpecialBlock() {
        return true;
    }
}

