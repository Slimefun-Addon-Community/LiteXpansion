package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.resources.ThoriumResource;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.uumatter.UUMatter;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

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

        registerEnchantments();

        ItemSetup.INSTANCE.init();

        // Armor
        new ElectricChestplate().register(this);

        UUMatter.INSTANCE.register();

        setupResearches();
        new ThoriumResource().register();

//        if (Wrench.wrenchFailChance.getValue() < 0
//            || Wrench.wrenchFailChance.getValue() > 1
//        ) {
//            getLogger().log(Level.SEVERE, "The wrench failure chance must be or be between 0 and 1!");
//            getServer().getPluginManager().disablePlugin(this);
//        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }
    
    private void registerEnchantments() {
        Enchantment glowEnchantment = new GlowEnchant(Constants.GLOW_ENCHANT, new String[] {
                "ADVANCED_CIRCUIT", "NANO_BLADE", "GLASS_CUTTER"
        });
        
        // Prevent double-registration errors
        if (Enchantment.getByKey(glowEnchantment.getKey()) == null) {
            Enchantment.registerEnchantment(glowEnchantment);
        }
    }

    private void setupResearches() {
        new Research(new NamespacedKey(this, "sanitizing_foots"),
            696969, "Sanitizing  foots since 2k10", 45)
            .addItems(Items.FOOD_SYNTHESIZER)
            .register();

        new Research(new NamespacedKey(this, "superalloys"),
            696970, "Superalloys", 35)
            .addItems(Items.THORIUM, Items.MAG_THOR, Items.IRIDIUM, Items.ADVANCED_ALLOY, Items.MIXED_METAL_INGOT,
                Items.REFINED_IRON)
            .register();

        new Research(new NamespacedKey(this, "super_hot_fire"),
            696971, "Super Hot Fire", 31)
            .addItems(Items.NANO_BLADE, Items.ELECTRIC_CHESTPLATE)
            .register();

        new Research(new NamespacedKey(this, "machinereee"),
            696972, "Machinereeeeee", 30)
            .addItems(Items.METAL_FORGE, Items.REFINED_SMELTERY, Items.RUBBER_SYNTHESIZER_MACHINE)
            .register();

        new Research(new NamespacedKey(this, "the_better_panel"),
            696973, "These are the better panels", 45)
            .addItems(Items.ADVANCED_SOLAR_PANEL, Items.ULTIMATE_SOLAR_PANEL, Items.HYBRID_SOLAR_PANEL)
            .register();

        new Research(new NamespacedKey(this, "does_this_even_matter"),
            696974, "Does this even matter", 150)
            .addItems(Items.UU_MATTER, Items.SCRAP, Items.MASS_FABRICATOR_MACHINE, Items.RECYCLER)
            .register();

        new Research(new NamespacedKey(this, "what_a_configuration"),
            696975, "What a configuration", 39)
            .addItems(Items.CARGO_CONFIGURATOR)
            .register();

        new Research(new NamespacedKey(this, "platings"),
            696976, "Platings", 40)
            .addItems(Items.IRIDIUM_PLATE)
            .register();

        new Research(new NamespacedKey(this, "rubber"),
            696977, "Rubber", 25)
            .addItems(Items.RUBBER)
            .register();

        new Research(new NamespacedKey(this, "circuits"),
            696978, "Circuits", 25)
            .addItems(Items.ELECTRONIC_CIRCUIT, Items.ADVANCED_CIRCUIT)
            .register();

        new Research(new NamespacedKey(this, "reinforcement_is_coming"),
            696979, "Reinforcement is coming", 15)
            .addItems(Items.REINFORCED_DOOR, Items.REINFORCED_GLASS, Items.REINFORCED_STONE)
            .register();

        new Research(new NamespacedKey(this, "only_glass"),
            696980, "Only glass", 40)
            .addItems(Items.GLASS_CUTTER)
            .register();

        new Research(new NamespacedKey(this, "machine_blocks"),
            696981, "Machine Blocks", 35)
            .addItems(Items.MACHINE_BLOCK, Items.ADVANCED_MACHINE_BLOCK)
            .register();

        new Research(new NamespacedKey(this, "coal_mesh"),
            696982, "Coal mesh", 30)
            .addItems(Items.COAL_DUST, Items.RAW_CARBON_MESH, Items.RAW_CARBON_FIBRE, Items.CARBON_PLATE)
            .register();

        new Research(new NamespacedKey(this, "what_are_these_cables"),
            696983, "What are these cables", 25)
            .addItems(Items.UNINSULATED_COPPER_CABLE, Items.COPPER_CABLE)
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

                        data.merge(item.getId(), 1, Integer::sum);
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

    public static FileConfiguration getCfg() {
        return instance.getConfig();
    }
}
