package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.InvUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MetalForge extends MultiBlockMachine {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiteXpansion.getInstance(), "metal_forge"),
        Items.METAL_FORGE,
        "",
        "&7Used to Forge Metals"
    );

    private static final ItemStack anvil = new ItemStack(Material.ANVIL);
    private static final ItemStack ironBlock = new ItemStack(Material.IRON_BLOCK);

    public MetalForge() {
        super(Items.LITEXPANSION, Items.METAL_FORGE, new ItemStack[] {
            anvil, new ItemStack(Material.STONE_BRICK_WALL), anvil,
            ironBlock, new ItemStack(Material.DISPENSER), ironBlock,
            null, new ItemStack(Material.DIAMOND_BLOCK), null
        }, new ItemStack[0], BlockFace.DOWN);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < recipes.size() - 1; i += 2) {
            items.add(recipes.get(i)[0]);
            items.add(recipes.get(i + 1)[0]);
        }

        return items;
    }

    @Override
    public void onInteract(Player p, Block b) {
        Block dispBlock = b.getRelative(BlockFace.DOWN);
        Dispenser disp = (Dispenser) dispBlock.getState();
        Inventory inv = disp.getInventory();
        final List<ItemStack[]> inputs = RecipeType.getRecipeInputList(this);

        for (int i = 0; i < inputs.size(); i++) {
            if (canCraft(inv, inputs, i)) {
                final ItemStack output = RecipeType.getRecipeOutputList(this, inputs.get(i)).clone();

                if (Slimefun.hasUnlocked(p, output, true)) {
                    final Inventory outputInv = findOutputInventory(output, dispBlock, inv);

                    if (outputInv != null) {
                        craft(p, b, inv, inputs.get(i), output, outputInv);
                    } else
                        SlimefunPlugin.getLocalization().sendMessage(p, "machines.full-inventory", true);
                }
                return;
            }
        }

        SlimefunPlugin.getLocalization().sendMessage(p, "machines.unknown-material", true);
    }

    private void craft(Player p, Block b, Inventory inv, ItemStack[] recipe, ItemStack output, Inventory outputInv) {
        for (ItemStack removing : recipe) {
            if (removing != null) {
                InvUtils.removeItem(inv, removing.getAmount(), true, stack ->
                    SlimefunUtils.isItemSimilar(stack, removing, true));
            }
        }

        outputInv.addItem(output);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);

        Block diamondBlock = b.getRelative(BlockFace.DOWN, 2);
        diamondBlock.setType(Material.AIR);
    }

    private boolean canCraft(Inventory inv, List<ItemStack[]> inputs, int i) {
        for (ItemStack converting : inputs.get(i)) {
            if (converting != null) {
                for (int j = 0; j < inv.getContents().length; j++) {
                    if (j == (inv.getContents().length - 1)
                        && !SlimefunUtils.isItemSimilar(converting,
                        inv.getContents()[j], true)) {
                        return false;
                    } else if (SlimefunUtils.isItemSimilar(inv.getContents()[j], converting, true)) break;
                }
            }
        }

        return true;
    }

}
