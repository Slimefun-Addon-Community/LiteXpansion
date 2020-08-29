package dev.j3fftw.litexpansion.utils;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * This utility class provides a few handy methods and constants to build the lore of any
 * {@link SlimefunItemStack}. It is mostly used directly inside the class {@link SlimefunItems}.
 *
 * @author TheBusyBiscuit
 *
 * @see SlimefunItems
 *
 */
public final class LoreBuilderDynamic {

    private static final int t = SlimefunPlugin.getCfg().getInt("URID.custom-ticker-delay");

    public static String powerBuffer(int power) {
        return power(power, " Buffer");
    }

    public static String powerPerTick(double power) {
        if (t == 0) {
            return power( (int) (20 * power), "/s");
        } else {
            return power( (1 / ( (double) t / 20) * power), "/s");
        }
    }

    public static String power(double power, String suffix) {
        return "&8\u21E8 &e\u26A1 &7" + (int) power + " J" + suffix;
    }
}
