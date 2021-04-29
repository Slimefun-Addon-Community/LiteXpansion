package dev.j3fftw.litexpansion.uumatter;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public final class UUMatter {

    public static final UUMatter INSTANCE = new UUMatter();

    private boolean registered;

    private UUMatter() {}

    private Category UuMatterCategory;

    public void register() {
        if (registered) {
            return;
        }

        final File uuMatterFile = new File(LiteXpansion.getInstance().getDataFolder(), "uumatter.yml");
        if (!uuMatterFile.exists()) {
            try {
                Files.copy(this.getClass().getResourceAsStream("/uumatter.yml"), uuMatterFile.toPath());
            } catch (IOException e) {
                LiteXpansion.getInstance().getLogger().log(Level.SEVERE, "Failed to copy default uumatter.yml file", e);
            }
        }
        createCategory();

        Config config = new Config(LiteXpansion.getInstance(), "uumatter.yml");

        // Counter to display how many recipes registered
        int counter = 0;

        for (String key : config.getKeys("recipes")) {
            final int idx = key.indexOf(':');
            final String id = key.toUpperCase().replace(' ', '_').substring(0, idx == -1 ? key.length() : idx);
            final int amount = NumberUtils.getInt(key.substring(idx + 1), 1);

            final SlimefunItemStack output = getOutputItem(id, amount);
            if (output == null) {
                continue;
            }

            final ItemStack[] recipe = new ItemStack[9];
            parseRecipe(config, key, recipe);

            counter++;
            addUuMatterRecipe(output, amount, recipe);
        }
        LiteXpansion.getInstance().getLogger().log(Level.INFO, "Loaded {0} UU-Matter recipes", new Object[] {
            counter
        });


        registered = true;
    }

    @Nullable
    private SlimefunItemStack getOutputItem(String id, int amount) {
        SlimefunItemStack output;

        // Make id unique, so it won't have conflict with other SF items
        String UniqueID = "UU_" + id;

        try{
            output = new SlimefunItemStack(UniqueID, Objects.requireNonNull(Material.getMaterial(id)), "");
        } catch (NullPointerException e){
            try{
                output = new SlimefunItemStack(UniqueID, Objects.requireNonNull(SlimefunItem.getByID(id)).getItem(), "");
            } catch (NullPointerException ex){
                LiteXpansion.getInstance().getLogger().log(Level.WARNING, "Unable to create recipe, unknown output item: {0}", new Object[] {id});
                return null;
            }
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
            for (char c : chars) {
                if (c == 'x') {
                    recipe[i++] = Items.UU_MATTER.clone();
                } else {
                    recipe[i++] = null;
                }
            }
        }
    }

    // Creating new category
    public void createCategory(){
        CustomItem categoryItem = new CustomItem(SkullItem.fromHash("54d39df0f813b7424406462854eb7249f8c76d80ce56f3af410e35a287062589"), "&5UU-Matter Recipes");
        this.UuMatterCategory = new Category(new NamespacedKey(LiteXpansion.getInstance(), "uumatter_category"), categoryItem);
    }

    public void addUuMatterRecipe(@Nonnull SlimefunItemStack item, int amount, @Nonnull ItemStack[] recipe) {
        if (recipe.length < 9) {
            // Make the new length 9 and fill it with nulls
            recipe = Arrays.copyOf(recipe, 9);
        }

        // Creating SlimeFunItem
        SlimefunItem sfItem = new SlimefunItem(UuMatterCategory, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        // Set output amount
        sfItem.setRecipeOutput(new SlimefunItemStack(item, amount));
        // Allowing to act like vanilla item
        sfItem.setUseableInWorkbench(true);
        // Register item
        sfItem.register(LiteXpansion.getInstance());

    }
}
