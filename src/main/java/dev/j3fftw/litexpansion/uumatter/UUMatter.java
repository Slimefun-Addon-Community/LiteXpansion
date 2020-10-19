package dev.j3fftw.litexpansion.uumatter;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class UUMatter {

    public static final UUMatter INSTANCE = new UUMatter();

    private final Map<ItemStack, ItemStack[]> recipes = new LinkedHashMap<>();

    private boolean registered;

    private UUMatter() {}

    public void register() {
        if (registered) return;

        final File uuMatterFile = new File(LiteXpansion.getInstance().getDataFolder(), "uumatter.yml");
        if (!uuMatterFile.exists()) {
            try {
                Files.copy(this.getClass().getResourceAsStream("/uumatter.yml"), uuMatterFile.toPath());
            } catch (IOException e) {
                LiteXpansion.getInstance().getLogger().log(Level.SEVERE, "Failed to copy default uumatter.yml file", e);
            }
        }

        Config config = new Config(LiteXpansion.getInstance(), "uumatter.yml");

        for (String key : config.getKeys("recipes")) {
            final int idx = key.indexOf(':');
            final String id = key.toUpperCase().replace(' ', '_').substring(0, idx == -1 ? key.length() : idx);
            final int amount = NumberUtils.getInt(key.substring(idx + 1), 1);

            final ItemStack output = getOutputItem(id, amount);
            if (output == null) {
                continue;
            }

            final ItemStack[] recipe = new ItemStack[9];
            parseRecipe(config, key, recipe);

            this.recipes.put(output, recipe);
            addUuMatterRecipe(output, recipe);
        }
        LiteXpansion.getInstance().getLogger().log(Level.INFO, "Loaded {0} UU-Matter recipes", new Object[] {
            this.recipes.size()
        });

        UuMatterCategory.INSTANCE.register();

        registered = true;
    }

    @Nullable
    private ItemStack getOutputItem(String id, int amount) {
        ItemStack output;

        final Material mat = Material.getMaterial(id);
        if (mat != null) {
            output = new ItemStack(mat, amount);
        } else {
            SlimefunItem item = SlimefunItem.getByID(id);
            if (item == null) {
                LiteXpansion.getInstance().getLogger().log(Level.WARNING,
                    "Unable to create recipe, unknown output item: {0}", new Object[] {id});
                return null;
            }
            output = item.getItem().clone();
            output.setAmount(amount);
        }
        return output;
    }

    private void parseRecipe(@Nonnull Config config, @Nonnull String key, @Nonnull ItemStack[] recipe) {
        int i = 0;
        final List<String> recipeList = config.getStringList("recipes." + key);
        for (String line : recipeList.subList(0, Math.min(recipeList.size(), 3))) {
            if (line.length() < 3) {
                LiteXpansion.getInstance().getLogger().log(Level.WARNING,
                    "Failed to load recipe for {0}, recipe length expected is 3 but got {1}",
                    new Object[] {key, line.length()}
                );
                return;
            }

            final char[] chars = new char[3];
            System.arraycopy(line.toCharArray(), 0, chars, 0, 3);
            for (char c : chars)
                if (c == 'x') {
                    recipe[i++] = Items.UU_MATTER.clone();
                } else {
                    recipe[i++] = null;
                }
        }
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
