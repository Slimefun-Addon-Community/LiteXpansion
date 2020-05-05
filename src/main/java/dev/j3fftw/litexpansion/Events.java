package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.utils.Constants;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getFoodLevel() < p.getFoodLevel()) {
            for (ItemStack item : p.getInventory().getContents()) {
                if (SlimefunUtils.isItemSimilar(item, Items.FOOD_SYNTHESIZER, false)
                    && ItemEnergy.getStoredEnergy(item) >= 3
                ) {
                    ItemEnergy.chargeItem(item, -3F);
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.5F, 1F);
                    e.setFoodLevel(20);
                    p.setSaturation(5);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamageDeal(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if (SlimefunUtils.isItemSimilar(itemInHand, Items.NANO_BLADE, false)
                && itemInHand.containsEnchantment(Enchantment.getByKey(Constants.NANO_BLADE_ACTIVE_ENCHANT))
                && ItemEnergy.getStoredEnergy(itemInHand) >= 5
            ) {
                e.setDamage(e.getDamage() * 1.75);
                ItemEnergy.chargeItem(itemInHand, (float) -2.5);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            ItemStack chestplate = p.getEquipment().getChestplate();
            if (p.getEquipment() != null
                && chestplate != null
                && SlimefunUtils.isItemSimilar(chestplate, Items.ELECTRIC_CHESTPLATE, false)
            ) {

                if (ItemEnergy.getStoredEnergy(chestplate) >= 5) {
                    p.getEquipment().setChestplate(ItemEnergy.chargeItem(chestplate,
                        (float) (e.getDamage() / -1.75)));

                    e.setCancelled(true);
                }
            }
        }
    }


}
