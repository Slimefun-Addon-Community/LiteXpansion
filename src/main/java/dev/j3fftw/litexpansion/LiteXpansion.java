package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.MagThor;
import dev.j3fftw.litexpansion.items.Thorium;
import dev.j3fftw.litexpansion.machine.MassFabricator;
import dev.j3fftw.litexpansion.machine.ScrapMachine;
import dev.j3fftw.litexpansion.resources.MagThorResource;
import dev.j3fftw.litexpansion.resources.ThoriumResource;
import dev.j3fftw.litexpansion.weapons.NanoBlade;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class LiteXpansion extends JavaPlugin implements SlimefunAddon {

    private static LiteXpansion instance;

    public static LiteXpansion getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        if (!new File(getDataFolder(), "config.yml").exists())
            saveDefaultConfig();

        new Metrics(this, 7111);

        getServer().getPluginManager().registerEvents(new Events(), this);

        // Category
        Items.LITEXPANSION.register();

        // Items
        new FoodSynthesizer().register(this);
        new MagThor().register(this);
        new Thorium().register(this);
        registerItem(Items.SCRAP, ScrapMachine.RECIPE_TYPE, new CustomItem(Material.COBBLESTONE, "&7Any Item!"));
        registerItem(Items.UU_MATTER, MassFabricator.RECIPE_TYPE, Items.SCRAP);

        // Weapon
        new NanoBlade().register(this);

        // Armor
        new ElectricChestplate().register(this);

        // Machines
        new ScrapMachine().register(this);
        new MassFabricator().register(this);

        // Register all UU-Matter recipes
        registerUuMatterRecipes();

        try {
            if (!Enchantment.isAcceptingRegistrations()) {
                Field accepting = Enchantment.class.getDeclaredField("acceptingNew");
                accepting.setAccessible(true);
                accepting.set(null, true);
            }
        } catch (IllegalAccessException | NoSuchFieldException ignored) {
            getLogger().warning("Failed to register enchantment. Seems the 'acceptingNew' field changed monkaS");
        }
        Enchantment.registerEnchantment(new NanoBladeActiveEnchant(Constants.NANO_BLADE_ACTIVE_ENCHANT));

        setupResearches();
        new ThoriumResource(getConfig().getBoolean("options.thorium-via-geo-miner")).register();
        new MagThorResource(getConfig().getBoolean("options.mag-thor-via-geo-miner")).register();
    }

    private void registerItem(@Nonnull SlimefunItemStack item, @Nonnull RecipeType type, @Nonnull ItemStack... items) {
        ItemStack[] recipe;
        if (items.length == 1) {
            recipe = new ItemStack[] {
                null, null, null,
                null, items[0], null,
                null, null, null
            };
        } else if (items.length < 9) {
            recipe = new ItemStack[9];
            System.arraycopy(items, 0, recipe, 0, items.length);
            Arrays.fill(recipe, null);
        } else
            recipe = items;

        new SlimefunItem(Items.LITEXPANSION, item, type, recipe).register(this);
    }

    private void registerUuRecipe(@Nonnull SlimefunItemStack item, @Nonnull ItemStack[] recipe) {
        this.registerUuRecipe(item.clone(), recipe);
    }

    private void registerUuRecipe(@Nonnull SlimefunItemStack item, int amount, @Nonnull ItemStack[] recipe) {
        final ItemStack clone = item.clone();
        clone.setAmount(amount);
        this.registerUuRecipe(clone, recipe);
    }

    private void registerUuRecipe(@Nonnull ItemStack result, @Nonnull ItemStack[] recipe) {
        RecipeType.ENHANCED_CRAFTING_TABLE.register(recipe, result);
    }

    private void registerUuMatterRecipes() {
        registerUuRecipe(Items.IRIDIUM, new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.COAL, 20), new ItemStack[] {
            null, null, Items.UU_MATTER,
            Items.UU_MATTER, null, null,
            null, null, Items.UU_MATTER
        });

        registerUuRecipe((SlimefunItemStack) SlimefunItems.COPPER_DUST, 5, new ItemStack[] {
            null, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, null, null
        });

        registerUuRecipe(new ItemStack(Material.DIAMOND), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.GOLD_ORE, 2), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.IRON_ORE, 2), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.LAPIS_LAZULI, 9), new ItemStack[] {
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.REDSTONE, 24), new ItemStack[] {
            null, null, null,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        registerUuRecipe((SlimefunItemStack) SlimefunItems.TIN_DUST, 5, new ItemStack[] {
            null, null, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, null, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.CHISELED_STONE_BRICKS, 48), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null
        });

        registerUuRecipe(new ItemStack(Material.CLAY_BALL, 48), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.GLASS, 32), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.GLOWSTONE, 8), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.GRASS_BLOCK, 16), new ItemStack[] {
            null, null, null,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, null, null
        });

        registerUuRecipe(new ItemStack(Material.MOSSY_COBBLESTONE, 16), new ItemStack[] {
            null, null, null,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.MYCELIUM, 24), new ItemStack[] {
            null, null, null,
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.NETHERRACK, 16), new ItemStack[] {
            null, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null
        });

        registerUuRecipe(new ItemStack(Material.OBSIDIAN, 12), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, null, null
        });

        registerUuRecipe(new ItemStack(Material.SANDSTONE, 16), new ItemStack[] {
            null, null, null,
            null, null, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.SNOW_BLOCK, 4), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, null, null,
            null, null, null
        });

        registerUuRecipe(new ItemStack(Material.STONE, 16), new ItemStack[] {
            null, null, null,
            null, Items.UU_MATTER, null,
            null, null, null
        });

        registerUuRecipe(new ItemStack(Material.OAK_LOG, 8), new ItemStack[] {
            null, Items.UU_MATTER, null,
            null, null, null,
            null, null, null
        });

        registerUuRecipe(new ItemStack(Material.WHITE_WOOL, 12), new ItemStack[] {
            Items.UU_MATTER, null, Items.UU_MATTER,
            null, null, null,
            null, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.BONE, 32), new ItemStack[] {
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, null, null
        });

        registerUuRecipe(new ItemStack(Material.CACTUS, 48), new ItemStack[] {
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.COCOA_BEANS, 32), new ItemStack[] {
            Items.UU_MATTER, Items.UU_MATTER, null,
            null, null, Items.UU_MATTER,
            Items.UU_MATTER, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.FEATHER, 32), new ItemStack[] {
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.FLINT, 32), new ItemStack[]{
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.GUNPOWDER, 15), new ItemStack[]{
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.INK_SAC, 48), new ItemStack[]{
            null, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.LAVA_BUCKET), new  ItemStack[]{
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null
        });

        registerUuRecipe(new ItemStack(Material.SNOWBALL, 16), new ItemStack[]{
            null, null, null,
            null, null, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });

        //todo add sticky resin or rubber

        registerUuRecipe(new ItemStack(Material.SUGAR_CANE, 48), new ItemStack[]{
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER,
            Items.UU_MATTER, null, Items.UU_MATTER
        });

        registerUuRecipe(new ItemStack(Material.VINE, 24), new ItemStack[]{
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, null, null,
            Items.UU_MATTER, null, null
        });

        registerUuRecipe(new ItemStack(Material.WATER_BUCKET), new ItemStack[]{
            null, null, null,
            null, Items.UU_MATTER, null,
            null, Items.UU_MATTER, null
        });

    }

    private void setupResearches() {
        Slimefun.registerResearch(new Research(new NamespacedKey(this, "sanitizing_foots"),
                696969, "Sanitizing  foots since 2k10", 45),
            Items.FOOD_SYNTHESIZER);

        Slimefun.registerResearch(new Research(new NamespacedKey(this, "superalloys"),
            696970, "Superalloys", 35), Items.THORIUM, Items.MAG_THOR);

        Slimefun.registerResearch(new Research(new NamespacedKey(this, "super_hot_fire"),
                696971, "Super Hot Fire", 31),
            Items.NANO_BLADE);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public String getBugTrackerURL() {
        return "https://github.com/J3fftw1/LiteXpansion/issues";
    }
}
