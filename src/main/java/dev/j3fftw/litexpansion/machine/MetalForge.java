package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.InvUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

    protected Inventory createVirtualInventory(Inventory inv) {
        Inventory fakeInv = Bukkit.createInventory(null, 9, "Fake Inventory");

        for (int j = 0; j < inv.getContents().length; j++) {
            ItemStack stack = inv.getContents()[j] != null && inv.getContents()[j].getAmount() > 1 ?
                new CustomItem(inv.getContents()[j], inv.getContents()[j].getAmount() - 1) : null;
            fakeInv.setItem(j, stack);
        }

        return fakeInv;
    }

    @Override
    public void onInteract(Player p, Block b) {
        Block dispBlock = b.getRelative(BlockFace.DOWN);
        Dispenser disp = (Dispenser) dispBlock.getState();
        Inventory inv = disp.getInventory();
        final List<ItemStack[]> inputs = RecipeType.getRecipeInputList(this);

        for (ItemStack[] input : inputs) {
            if (canCraft(inv, input)) {
                final ItemStack output = RecipeType.getRecipeOutputList(this, input).clone();

                if (Slimefun.hasUnlocked(p, output, true)) {
                    final Inventory fakeInv = createVirtualInventory(inv);
                    final Inventory outputInv = findOutputInventory(output, dispBlock, inv, fakeInv);

                    if (outputInv != null) {
                        craft(p, b, inv, input, output, outputInv);
                    } else {
                        SlimefunPlugin.getLocalization().sendMessage(p, "machines.full-inventory", true);
                    }
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

    private boolean canCraft(Inventory inv, ItemStack[] recipe) {
        int counter = 0;
        for (int j = 0; j < inv.getContents().length; j++) {

            SlimefunItem sfItemInv = SlimefunItem.getByItem(inv.getContents()[j]);
            SlimefunItem sfItemRecipe = SlimefunItem.getByItem(recipe[j]);
            if (sfItemInv == null && sfItemRecipe == null) {
                counter++;
            } else if (sfItemInv != null && sfItemRecipe != null
                && sfItemInv.getId().equals(sfItemRecipe.getId())) {
                counter++;
            }
        }
        return counter == inv.getContents().length;
    }

}
