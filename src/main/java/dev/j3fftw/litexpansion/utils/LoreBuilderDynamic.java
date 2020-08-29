package dev.j3fftw.litexpansion.utils;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * This utility class provides a few handy methods and constants to build the lore of any
 * {@link SlimefunItemStack}. It is mostly used directly inside the class {@link SlimefunItems}.
 *
 * Modified TheBusyBiscuit's {@link LoreBuilder} to calculate power input/output based on server tick rate
 *
 * @see SlimefunItems
 *
 */
public final class LoreBuilderDynamic {

    private static final int SERVER_TICK_RATE = 20;

    private static final int CUSTOM_TICKER_DELAY = SlimefunPlugin.getCfg().getInt("URID.custom-ticker-delay");

    private static final DecimalFormat powerFormat = new DecimalFormat("###,###.##", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private static String decimalFade(String str) {
        if (str.indexOf('.') != -1) {
            return str.substring(0, str.indexOf('.')) + ChatColor.DARK_GRAY + str.substring(str.indexOf('.')) + ChatColor.GRAY;
        } else {
            return str;
        }
    }

    public static String powerBuffer(int power) {
        return power(power, " Buffer");
    }

    public static String powerPerTick(double power) {
        if (CUSTOM_TICKER_DELAY == 0) {
            return power( (SERVER_TICK_RATE * power), "/s");
        } else {
            return power( (1 / ( (double) CUSTOM_TICKER_DELAY / SERVER_TICK_RATE) * power), "/s");
        }
    }

    public static String power(double power, String suffix) {
        return "&8\u21E8 &e\u26A1 &7" + decimalFade(powerFormat.format(power)) + " J" + suffix;
    }
}
