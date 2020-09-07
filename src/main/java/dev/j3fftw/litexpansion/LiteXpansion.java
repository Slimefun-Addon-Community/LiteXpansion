package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.resources.ThoriumResource;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.uumatter.UUMatter;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class LiteXpansion extends JavaPlugin implements SlimefunAddon {

    private static LiteXpansion instance;

    @Override
    public void onEnable() {
        instance = this;

        if (!new File(getDataFolder(), "config.yml").exists())
            saveDefaultConfig();

        final Metrics metrics = new Metrics(this, 7111);
        setupCustomMetrics(metrics);

        if (getConfig().getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "J3fftw1/LiteXpansion/master").start();
        }

        getServer().getPluginManager().registerEvents(new Events(), this);

        // Enchantment
        try {
            if (!Enchantment.isAcceptingRegistrations()) {
                Field accepting = Enchantment.class.getDeclaredField("acceptingNew");
                accepting.setAccessible(true);
                accepting.set(null, true);
            }
        } catch (IllegalAccessException | NoSuchFieldException ignored) {
            getLogger().warning("Failed to register enchantment. Seems the 'acceptingNew' field changed monkaS");
        }
        Enchantment.registerEnchantment(new GlowEnchant(Constants.GLOW_ENCHANT));

        // Category
        Items.LITEXPANSION.register();

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

        // Tools
        /*

        registerItem(Items.TREETAP, RecipeType.ENHANCED_CRAFTING_TABLE,
            null, new ItemStack(Material.OAK_PLANKS), null,
            new ItemStack(Material.OAK_PLANKS), new ItemStack(Material.OAK_PLANKS), new ItemStack(Material.OAK_PLANKS),
            new ItemStack(Material.OAK_PLANKS), null, null
        );
        */

        // Armor
        new ElectricChestplate().register(this);

        UUMatter.INSTANCE.register();

        setupResearches();
        new ThoriumResource().register();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void setupResearches() {
        new Research(new NamespacedKey(this, "sanitizing_foots"),
            696969, "Sanitizing  foots since 2k10", 45)
            .addItems(Items.FOOD_SYNTHESIZER)
            .register();

        new Research(new NamespacedKey(this, "superalloys"),
            696970, "Superalloys", 35)
            .addItems(Items.THORIUM, Items.MAG_THOR)
            .register();

        new Research(new NamespacedKey(this, "super_hot_fire"),
            696971, "Super Hot Fire", 31)
            .addItems(Items.NANO_BLADE)
            .register();
    }

    private void setupCustomMetrics(@Nonnull Metrics metrics) {
        metrics.addCustomChart(new Metrics.AdvancedPie("blocks_placed", () -> {
            final Map<String, Integer> data = new HashMap<>();
            try {
                Class<?> blockStorage = Class.forName("me.mrCookieSlime.Slimefun.api.BlockStorage");

                for (World world : Bukkit.getWorlds()) {
                    final BlockStorage storage = BlockStorage.getStorage(world);
                    if (storage == null) continue;

                    final Field f = blockStorage.getDeclaredField("storage");
                    f.setAccessible(true);
                    @SuppressWarnings("unchecked") final Map<Location, Config> blocks =
                        (Map<Location, Config>) f.get(storage);

                    for (Map.Entry<Location, Config> entry : blocks.entrySet()) {
                        final SlimefunItem item = SlimefunItem.getByID(entry.getValue().getString("id"));
                        if (item == null || !(item.getAddon() instanceof LiteXpansion)) continue;

                        data.merge(item.getID(), 1, Integer::sum);
                    }
                }
            } catch (ReflectiveOperationException e) {
                getLogger().log(Level.WARNING, "Failed to load placed blocks", e);
            }
            return data;
        }));
    }

    @Nonnull
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
