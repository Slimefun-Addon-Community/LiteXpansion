package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.MagThor;
import dev.j3fftw.litexpansion.items.Thorium;
import dev.j3fftw.litexpansion.machine.MassFabricator;
import dev.j3fftw.litexpansion.machine.ScrapMachine;
import dev.j3fftw.litexpansion.resources.ThoriumResource;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.uumatter.UUMatter;
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
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;

public class LiteXpansion extends JavaPlugin implements SlimefunAddon {

    private static LiteXpansion instance;

    @Override
    public void onEnable() {
        instance = this;

        if (!new File(getDataFolder(), "config.yml").exists())
            saveDefaultConfig();

        new Metrics(this, 7111);

        if (getConfig().getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "J3fftw1/LiteXpansion/master").start();
        }

        getServer().getPluginManager().registerEvents(new Events(), this);

        // Category
        Items.LITEXPANSION.register();

        // Items
        new FoodSynthesizer().register(this);
        new MagThor().register(this);
        new Thorium().register(this);

        ItemSetup.INSTANCE.init();

        /*
        registerItem(Items.ENERGY_CRYSTAl, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE),
            new ItemStack(Material.REDSTONE), new ItemStack(Material.DIAMOND), new ItemStack(Material.REDSTONE),
            new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE)
        );

        registerItem(Items.LAPOTRON_CRYSTAL, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack(Material.LAPIS_LAZULI), Items.ELECTRONIC_CIRCUIT, new ItemStack(Material.LAPIS_LAZULI),
            new ItemStack(Material.LAPIS_LAZULI), Items.ENERGY_CRYSTAl, new ItemStack(Material.LAPIS_LAZULI),
            new ItemStack(Material.LAPIS_LAZULI), Items.ELECTRONIC_CIRCUIT, new ItemStack(Material.LAPIS_LAZULI)
        );

        registerItem(Items.REINFORCED_STONE, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            new ItemStack(Material.STONE), Items.ADVANCED_ALLOY, new ItemStack(Material.STONE),
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE)
        );

        registerItem(Items.REINFORCED_DOOR, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.REINFORCED_STONE, Items.REINFORCED_STONE, null,
            Items.REINFORCED_STONE, Items.REINFORCED_STONE, null,
            Items.REINFORCED_STONE, Items.REINFORCED_STONE, null
        );
        */

        // Weapon
        new NanoBlade().register(this);

        // Tools
        /*
        registerItem(Items.WRENCH, RecipeType.ENHANCED_CRAFTING_TABLE,
            SlimefunItems.BRONZE_INGOT, null, SlimefunItems.BRONZE_INGOT,
            null, SlimefunItems.BRONZE_INGOT, null,
            null, SlimefunItems.BRONZE_INGOT, null
        );

        registerItem(Items.TREETAP, RecipeType.ENHANCED_CRAFTING_TABLE,
            null, new ItemStack(Material.OAK_PLANKS), null,
            new ItemStack(Material.OAK_PLANKS), new ItemStack(Material.OAK_PLANKS), new ItemStack(Material.OAK_PLANKS),
            new ItemStack(Material.OAK_PLANKS), null, null
        );
        */

        // Armor
        new ElectricChestplate().register(this);

        // Machines
        new ScrapMachine().register(this);
        new MassFabricator().register(this);

        UUMatter.INSTANCE.register();

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
        new ThoriumResource().register();

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void registerItem(@Nonnull SlimefunItemStack result, @Nonnull RecipeType type,
                              @Nonnull ItemStack... items) {
        ItemStack[] recipe;
        if (items.length == 1) {
            recipe = new ItemStack[] {
                null, null, null,
                null, items[0], null,
                null, null, null
            };
            new SlimefunItem(Items.LITEXPANSION, result, type, recipe).register(this);

            // make shapeless
            for (int i = 0; i < 9; i++) {
                if (i == 4) continue;
                final ItemStack[] recipe2 = new ItemStack[9];
                recipe2[i] = items[0];
                type.register(recipe2, result);
            }

            return;
        }

        if (items.length < 9) {
            recipe = new ItemStack[9];
            System.arraycopy(items, 0, recipe, 0, items.length);
        } else
            recipe = items;

        new SlimefunItem(Items.LITEXPANSION, result, type, recipe).register(this);
    }

    // Haha shapeless recipe bitches!!!! <3 <3 <3
    // DEAL WITH IT KIDDOS HAHAHAHHAHAHAHAHAH
    private void registerRecipe(@Nonnull SlimefunItemStack result, @Nonnull SlimefunItemStack item) {
        for (int i = 0; i < 9; i++) {
            final ItemStack[] recipe = new ItemStack[9];
            recipe[i] = item;
            RecipeType.ENHANCED_CRAFTING_TABLE.register(recipe, result);
        }
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

    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public String getBugTrackerURL() {
        return "https://github.com/J3fftw1/LiteXpansion/issues";
    }

    public static LiteXpansion getInstance() {
        return instance;
    }
}
