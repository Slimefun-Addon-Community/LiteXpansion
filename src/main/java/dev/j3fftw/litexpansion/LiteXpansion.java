package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.resources.ThoriumResource;
import dev.j3fftw.litexpansion.service.MetricsService;
import dev.j3fftw.litexpansion.ticker.PassiveElectricRemovalTicker;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.utils.Reflections;
import dev.j3fftw.litexpansion.uumatter.UUMatter;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;
import org.bstats.MetricsBase;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;

public class LiteXpansion extends JavaPlugin implements SlimefunAddon {

    private static LiteXpansion instance;

    private final MetricsService metricsService = new MetricsService();

    @Override
    public void onEnable() {
        setInstance(this);

        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        final Metrics metrics = new Metrics(this, 7111);
        metricsService.setup(metrics);

        if (getConfig().getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "J3fftw1/LiteXpansion/master").start();
        }

        registerEnchantments();

        if (getConfig().getBoolean("options.nerf-other-addons", true)) {
            getServer().getScheduler().runTask(this, this::nerfCrap);
        }

        ItemSetup.INSTANCE.init();

        getServer().getPluginManager().registerEvents(new Events(), this);

        UUMatter.INSTANCE.register();

        setupResearches();
        new ThoriumResource().register();

        final PassiveElectricRemovalTicker perTicker = new PassiveElectricRemovalTicker();
        getServer().getScheduler().runTaskTimerAsynchronously(this, perTicker, 20, 20);
    }

    @Override
    public void onDisable() {
        setInstance(null);
    }

    private void registerEnchantments() {
        if (!Enchantment.isAcceptingRegistrations()) {
            Reflections.setStaticField(Enchantment.class, "acceptingNew", true);
        }

        Enchantment glowEnchantment = new GlowEnchant(Constants.GLOW_ENCHANT, new String[] {
            "ADVANCED_CIRCUIT", "NANO_BLADE", "GLASS_CUTTER", "LAPOTRON_CRYSTAL",
            "ADVANCEDLX_SOLAR_HELMET", "HYBRID_SOLAR_HELMET", "ULTIMATE_SOLAR_HELMET",
            "DIAMOND_DRILL"
        });

        // Prevent double-registration errors
        if (Enchantment.getByKey(glowEnchantment.getKey()) == null) {
            Enchantment.registerEnchantment(glowEnchantment);
        }
    }

    private void nerfCrap() {
        // Vanilla SF
        final SlimefunItem energizedPanel = SlimefunItem.getById("SOLAR_GENERATOR_4");
        if (energizedPanel != null) {
            Reflections.setField(energizedPanel, "dayEnergy", 64);
            Reflections.setField(energizedPanel, "nightEnergy", 32);
        }

        // InfinityExpansion - Halved all values and made infinite panel + infinity reactor much less
        Reflections.setField(SlimefunItem.getById("ADVANCED_PANEL"), "generation", 75);
        Reflections.setField(SlimefunItem.getById("CELESTIAL_PANEL"), "generation", 250);
        Reflections.setField(SlimefunItem.getById("VOID_PANEL"), "generation", 1200);
        Reflections.setField(SlimefunItem.getById("INFINITE_PANEL"), "generation", 20_000);
        Reflections.setField(SlimefunItem.getById("INFINITY_REACTOR"), "gen", 50_000);

        if (getConfig().getBoolean("options.nerf-elemental-reactor", true) {
            // SlimefunWarfare - Halved all values
            Reflections.setField(SlimefunItem.getById("ELEMENTAL_REACTOR"), "energyProducedPerTick", 8_192);
        }

        // Galactifun
        Reflections.setField(SlimefunItem.getById("FUSION_REACTOR"), "energyProducedPerTick", 8_192);

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
            .addItems(Items.METAL_FORGE, Items.REFINED_SMELTERY, Items.RUBBER_SYNTHESIZER_MACHINE, Items.MANUAL_MILL,
                Items.GENERATOR)
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
            .addItems(Items.IRIDIUM_PLATE, Items.COPPER_PLATE, Items.TIN_PLATE, Items.DIAMOND_PLATE, Items.IRON_PLATE,
                Items.GOLD_PLATE, Items.THORIUM_PLATE)
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
            .addItems(Items.UNINSULATED_COPPER_CABLE, Items.COPPER_CABLE,
                Items.UNINSULATED_COPPER_CABLE, Items.TIN_CABLE)
            .register();

        new Research(new NamespacedKey(this, "triple_a"),
            696984, "Triple a", 20)
            .addItems(Items.RE_BATTERY)
            .register();

        new Research(new NamespacedKey(this, "casing"),
            696985, "S 340", 20)
            .addItems(Items.TIN_ITEM_CASING, Items.COPPER_ITEM_CASING)
            .register();

        new Research(new NamespacedKey(this, "solar_helmets"),
            696986, "More solar helmets", 30)
            .addItems(Items.HYBRID_SOLAR_HELMET, Items.ADVANCED_SOLAR_HELMET, Items.ADVANCEDLX_SOLAR_HELMET,
                Items.CARBONADO_SOLAR_HELMET, Items.ENERGIZED_SOLAR_HELMET, Items.ULTIMATE_SOLAR_HELMET)
            .register();
    }

    private void forceMetricsPush(@Nonnull Metrics metrics) {
        MetricsBase base = (MetricsBase) Reflections.getField(Metrics.class, metrics, "metricsBase");
        Reflections.invoke(MetricsBase.class, base, "submitData");
    }

    @Nonnull
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public String getBugTrackerURL() {
        return "https://github.com/Slimefun-Addon-Community/LiteXpansion/issues";
    }

    public static LiteXpansion getInstance() {
        return instance;
    }

    private static void setInstance(LiteXpansion ins) {
        instance = ins;
    }
}
