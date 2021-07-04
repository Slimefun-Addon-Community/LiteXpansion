package dev.j3fftw.litexpansion.utils;

import java.util.Optional;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class Utils {

    private Utils() {}

    public static void send(Player player, String message) {
        player.sendMessage(ChatColor.GRAY + "[LiteXpansion] " + ChatColors.color(message));
    }

    public static Optional<Boolean> getOptionalBoolean(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key) {
        final Byte b = meta.getPersistentDataContainer().get(key, PersistentDataType.BYTE);

        if (b == null) {
            return Optional.empty();
        } else {
            return Optional.of(b == 1);
        }
    }
}
