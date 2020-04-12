package dev.j3fftw.litexpansion;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getFoodLevel() < p.getFoodLevel()) {
            for (ItemStack item : p.getInventory().getContents()) {
                if (SlimefunUtils.isItemSimilar(item, Items.FOOD_SYNTHESIZER, false)
                    && ItemEnergy.getStoredEnergy(item) >= 3) {
                    ItemEnergy.chargeItem(item, -3F);
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.5F, 1F);
                    e.setFoodLevel(20);
                    p.setSaturation(5);
                    break;
                }
            }
        }
    }
}
