package dev.j3fftw.litexpansion.utils;

import dev.j3fftw.litexpansion.LiteXpansion;

import javax.annotation.Nonnull;

public final class Log {

    private Log() {}

    public static void info(@Nonnull String message) {
        LiteXpansion.getInstance().getLogger().info(message);
    }

    public static void info(@Nonnull String message, @Nonnull Object... values) {
        info(replaceVariablesInMessage(message, values));
    }

    public static void warn(@Nonnull String message) {
        LiteXpansion.getInstance().getLogger().warning(message);
    }

    public static void warn(@Nonnull String message, @Nonnull Object... values) {
        warn(replaceVariablesInMessage(message, values));
    }

    public static void error(@Nonnull String message) {
        LiteXpansion.getInstance().getLogger().severe(message);
    }

    public static void error(@Nonnull String message, @Nonnull Object... values) {
        error(replaceVariablesInMessage(message, values));
    }

    private static String replaceVariablesInMessage(@Nonnull String message, @Nonnull Object... values) {
        int idx = 0;
        int next;
        int off = 0;
        while ((next = message.indexOf("{}", off)) != -1) {
            if (idx == values.length) break; // Ran out of values

            message = message.substring(0, next) + values[idx++].toString() + message.substring(next + 2);
            off = next + 1;
        }

        return message;
    }
}
