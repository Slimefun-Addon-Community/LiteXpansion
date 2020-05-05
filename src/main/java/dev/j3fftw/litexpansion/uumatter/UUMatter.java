package dev.j3fftw.litexpansion.uumatter;

import dev.j3fftw.litexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class UUMatter {

    public static final UUMatter INSTANCE = new UUMatter();

    private final Map<ItemStack, ItemStack[]> recipes = new LinkedHashMap<>();

    private boolean registered;

    private UUMatter() {}

    public void register() {
        if (registered) return;

        registerUuMatterRecipes();
        UuMatterCategory.INSTANCE.register();

        registered = true;
    }

    private void registerUuMatterRecipes() {
        addUuMatterRecipe(new ItemStack(Material.COAL, 20), new ItemStack[] {
            null, null, Items.UU_MATTER,
            Items.UU_MATTER, null, null,
            null, null, Items.UU_MATTER
        });

        addUuMatterRecipe((SlimefunItemStack) SlimefunItems.COPPER_DUST, 5, new ItemStack[] {
            null, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.DIAMOND), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.GOLD_ORE, 2), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.IRON_ORE, 2), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.LAPIS_LAZULI, 9), new ItemStack[] {
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.REDSTONE, 24), new ItemStack[] {
            null, null, null,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        addUuMatterRecipe((SlimefunItemStack) SlimefunItems.TIN_DUST, 5, new ItemStack[] {
            null, null, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.CHISELED_STONE_BRICKS, 48), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null
        });

        addUuMatterRecipe(new ItemStack(Material.CLAY_BALL, 48), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.GLASS, 32), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.GLOWSTONE, 8), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.GRASS_BLOCK, 16), new ItemStack[] {
            null, null, null,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, null, null
        });

        addUuMatterRecipe(new ItemStack(Material.MOSSY_COBBLESTONE, 16), new ItemStack[] {
            null, null, null,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.MYCELIUM, 24), new ItemStack[] {
            null, null, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.NETHERRACK, 16), new ItemStack[] {
            null, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null
        });

        addUuMatterRecipe(new ItemStack(Material.OBSIDIAN, 12), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.SANDSTONE, 16), new ItemStack[] {
            null, null, null,
            null, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.SNOW_BLOCK, 4), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.STONE, 16), new ItemStack[] {
            null, null, null,
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.OAK_LOG, 8), new ItemStack[] {
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.WHITE_WOOL, 12), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, null, null,
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.BONE, 32), new ItemStack[] {
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null
        });

        addUuMatterRecipe(new ItemStack(Material.CACTUS, 48), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.COCOA_BEANS, 32), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, null,
            null, null, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.FEATHER, 32), new ItemStack[] {
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.FLINT, 32), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.GUNPOWDER, 15), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.INK_SAC, 48), new ItemStack[] {
            null, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.LAVA_BUCKET), new ItemStack[] {
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null
        });

        addUuMatterRecipe(new ItemStack(Material.SNOWBALL, 16), new ItemStack[] {
            null, null, null,
            null, null, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });


        addUuMatterRecipe(new ItemStack(Material.SUGAR_CANE, 48), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        addUuMatterRecipe(new ItemStack(Material.VINE, 24), new ItemStack[] {
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, null, null
        });

        addUuMatterRecipe(new ItemStack(Material.WATER_BUCKET), new ItemStack[] {
            null, null, null,
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null
        });

        if (Bukkit.getPluginManager().getPlugin("SlimyTreeTaps") != null)
            addUuMatterRecipe((SlimefunItemStack) SlimefunItem.getByID("STICKY_RESIN").getItem(), 21, new ItemStack[] {
                Items.UU_MATTER, null, Items.UU_MATTER,
                null, null, null,
                Items.UU_MATTER, null, Items.UU_MATTER
            });
    }

    public void addUuMatterRecipe(@Nonnull SlimefunItemStack item, int amount, @Nonnull ItemStack[] recipe) {
        final ItemStack clone = item.clone();
        clone.setAmount(amount);
        this.addUuMatterRecipe(clone, recipe);
    }

    public void addUuMatterRecipe(@Nonnull ItemStack result, @Nonnull ItemStack[] recipe) {
        if (recipe.length < 9) {
            // Make the new length 9 and fill it with nulls
            recipe = Arrays.copyOf(recipe, 9);
        }

        // Register to the enhanced crafting table
        RecipeType.ENHANCED_CRAFTING_TABLE.register(recipe, result);
        // Add to our recipes set, this is used for the GUI
        this.recipes.put(result, recipe);
    }

    public Map<ItemStack, ItemStack[]> getRecipes() {
        return Collections.unmodifiableMap(recipes);
    }
}
