package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.MagThor;
import dev.j3fftw.litexpansion.items.NanoBlade;
import dev.j3fftw.litexpansion.items.Thorium;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class LiteXpansion extends JavaPlugin implements SlimefunAddon {

    private static LiteXpansion instance;

    public static LiteXpansion getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;

        new Metrics(this, 7111);

        getServer().getPluginManager().registerEvents(new Events(), this);

        // Category
        Items.LITEXPANSION.register();

        // Items
        new FoodSynthesizer().register(this);
        new MagThor().register(this);
        new Thorium().register(this);

        // Weapon
        new NanoBlade().register(this);

        setupResearches();
    }

    private void setupResearches() {
        Slimefun.registerResearch(new Research(new NamespacedKey(this, "sanitizing_foots"),
                696969, "Sanitizing  foots since 2k10", 45),
            Items.FOOD_SYNTHESIZER);
    }

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
