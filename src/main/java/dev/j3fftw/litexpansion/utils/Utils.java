package dev.j3fftw.litexpansion.utils;

import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class Utils {

    private Utils() {}

    public static void send(Player player, String message) {
        player.sendMessage(ChatColor.GRAY + "[LiteXpansion] " + ChatColors.color(message));
    }
}
