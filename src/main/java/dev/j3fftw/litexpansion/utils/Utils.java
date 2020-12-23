package dev.j3fftw.litexpansion.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class Utils {

    private static final DecimalFormat powerFormat = new DecimalFormat("###,###.##",
        DecimalFormatSymbols.getInstance(Locale.ROOT));

    private Utils() {}

    public static void send(Player p, String message) {
        p.sendMessage(ChatColor.GRAY + "[LiteXpansion] " + ChatColors.color(message));
    }
}
