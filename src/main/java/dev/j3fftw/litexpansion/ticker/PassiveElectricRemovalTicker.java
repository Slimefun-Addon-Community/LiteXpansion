package dev.j3fftw.litexpansion.ticker;

import dev.j3fftw.litexpansion.items.PassiveElectricRemoval;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class PassiveElectricRemovalTicker implements Runnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ItemStack is : player.getInventory().getContents()) {
                if (is != null && is.hasItemMeta()) {
                    final SlimefunItem item = SlimefunItem.getByItem(is);

                    if (item instanceof PassiveElectricRemoval) {
                        final PassiveElectricRemoval per = (PassiveElectricRemoval) item;

                        PassiveElectricRemoval.tick(is, per);
                    }
                }
            }
        }
    }
}
