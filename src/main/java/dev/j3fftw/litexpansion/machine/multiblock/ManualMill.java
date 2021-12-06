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

import javax.annotation.Nonnull;

public class ManualMill extends CraftingMultiBlock {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiteXpansion.getInstance(), "manual_mill"),
        Items.MANUAL_MILL,
        "",
        "&7Used to Forge Metals"
    );

    private static final ItemStack anvil = new ItemStack(Material.ANVIL);
    private static final ItemStack ironBlock = new ItemStack(Material.IRON_BLOCK);

    public ManualMill() {
        super(Items.LITEXPANSION, Items.MANUAL_MILL, new ItemStack[] {
            anvil, new ItemStack(Material.STONE_BRICK_WALL), anvil,
            ironBlock, new ItemStack(Material.DISPENSER), ironBlock,
            null, ironBlock, null
        }, new ItemStack[0], BlockFace.DOWN);
    }

    @Override
    public void onSuccessfulCraft(@Nonnull Block b) {
        b.getRelative(BlockFace.DOWN, 2).setType(Material.AIR);
    }
}
