package dev.j3fftw.litexpansion.utils;

import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import org.bukkit.NamespacedKey;

public final class Constants {

    public static final int SERVER_TICK_RATE = 20;

    public static final int CUSTOM_TICKER_DELAY = SlimefunPlugin.getCfg().getInt("URID.custom-ticker-delay");

    public static final boolean MACHINE_BREAK_REQUIRES_WRENCH = LiteXpansion.getCfg().getBoolean("options.need-wrench-to-break-machines");

    public static final double WRENCH_FAIL_CHANCE = LiteXpansion.getCfg().getDouble("options.wrench-failure-chance");

    public static final NamespacedKey GLOW_ENCHANT = new NamespacedKey(LiteXpansion.getInstance(),
        "glow_enchant");

    private Constants() {}

}
