package dev.j3fftw.litexpansion.ticker;

import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.items.PassiveElectricRemoval;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/*
 * This class iterates through every item in every player's inv and will look for a PassiveElectricRemoval item.
 * If it finds it, it adds to a set with some other data and continues.
 * At the end it will tick all of these items on the main thread.
 * This is hacky but I don't have the time to make a better way currently
 */
public final class PassiveElectricRemovalTicker implements Runnable {

    @Override
    public void run() {
        final Set<TickerDataHolder> set = new HashSet<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            // This creates a copy and translates all the NMS items into CB items. This is pretty expensive
            final ItemStack[] contents = player.getInventory().getContents();

            for (int i = 0; i < contents.length; i++) {
                final ItemStack is = contents[i];
                if (is != null && is.hasItemMeta()) {
                    final SlimefunItem item = SlimefunItem.getByItem(is);

                    if (item instanceof PassiveElectricRemoval) {
                        final PassiveElectricRemoval per = (PassiveElectricRemoval) item;

                        set.add(new TickerDataHolder(player.getUniqueId(), i, is, per));
                    }
                }
            }
        }

        Bukkit.getScheduler().runTask(LiteXpansion.getInstance(), () -> {
            for (TickerDataHolder holder : set) {
                PassiveElectricRemoval.tick(holder.item, holder.per);
            }
        });
    }

    private static class TickerDataHolder {

        private final UUID playerUuid;
        private final int slot;
        private final ItemStack item;
        private final PassiveElectricRemoval per;

        public TickerDataHolder(UUID playerUuid, int slot, ItemStack itemStack, PassiveElectricRemoval per) {
            this.playerUuid = playerUuid;
            this.slot = slot;
            this.item = itemStack;
            this.per = per;
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerUuid, slot, item);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TickerDataHolder) {
                final TickerDataHolder tdh = (TickerDataHolder) obj;
                return this.playerUuid.equals(tdh.playerUuid)
                    && this.slot == tdh.slot
                    && this.item.equals(tdh.item)
                    && this.per.equals(tdh.per);
            } else {
                return false;
            }
        }
    }
}
