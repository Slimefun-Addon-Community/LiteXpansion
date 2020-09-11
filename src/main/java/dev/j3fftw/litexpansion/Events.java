package dev.j3fftw.litexpansion;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.items.DiamondDrill;
import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.MiningDrill;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.utils.Utils;
import dev.j3fftw.litexpansion.weapons.NanoBlade;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

public class Events implements Listener {

    ArrayList<Material> drillableBlocks = new ArrayList<>(Arrays.asList( Material.STONE, Material.ANDESITE, Material.DIORITE, Material.GRANITE ));

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getFoodLevel() < p.getFoodLevel()) {
            checkAndConsume(p, e);
        }
    }

    @EventHandler
    public void onPlayerDamageDeal(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            final NanoBlade nanoBlade = (NanoBlade) SlimefunItem.getByID(Items.NANO_BLADE.getItemId());
            if (nanoBlade.isItem(itemInHand)
                && itemInHand.containsEnchantment(Enchantment.getByKey(Constants.GLOW_ENCHANT))
                && nanoBlade.removeItemCharge(itemInHand, 10)
            ) {
                e.setDamage(e.getDamage() * 1.75);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && ((Player) e.getEntity()).getEquipment() != null) {
            Player p = (Player) e.getEntity();
            ItemStack chestplate = p.getEquipment().getChestplate();
            final ElectricChestplate electricChestplate = (ElectricChestplate)
                SlimefunItem.getByID(Items.ELECTRIC_CHESTPLATE.getItemId());
            if (chestplate != null
                && electricChestplate.isItem(chestplate)
                && electricChestplate.removeItemCharge(chestplate, (float) (e.getDamage() / 1.75))
            ) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHungerDamage(EntityDamageEvent e) {
        if (Items.FOOD_SYNTHESIZER == null || Items.FOOD_SYNTHESIZER.getItem().isDisabled()
            || !(e.getEntity() instanceof Player)) {
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            checkAndConsume((Player) e.getEntity(), null);
        }
    }

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onStoneDrill(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) return;

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();

        final MiningDrill miningDrill = (MiningDrill) SlimefunItem.getByID(Items.MINING_DRILL.getItemId());
        if (drillableBlocks.contains(blockType) && miningDrill.isItem(e.getItem())
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(),
            blockLocation, ProtectableAction.BREAK_BLOCK)
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);

            if (slimefunItem == null && ((Rechargeable) SlimefunItem.getByItem(e.getItem()))
                .removeItemCharge(e.getItem(), 0.5F)) {
                // This allows other plugins to register broken block as player broken
                block.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                e.getPlayer().playSound(blockLocation, Sound.BLOCK_STONE_BREAK, 1.5F, 1F);

                // This has to be done because the item in the main hand is not a pickaxe
                if (blockType == Material.STONE) {
                    blockLocation.getWorld().dropItem(blockLocation, new ItemStack(Material.COBBLESTONE));

                } else {
                    blockLocation.getWorld().dropItem(blockLocation, new ItemStack(blockType));
                }
            }
        }
    }

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onObsidiaDrill(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) return;

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();

        final DiamondDrill diamondDrill = (DiamondDrill) SlimefunItem.getByID(Items.DIAMOND_DRILL.getItemId());
        if (blockType.equals(Material.OBSIDIAN) && diamondDrill.isItem(e.getItem())
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), blockLocation, ProtectableAction.BREAK_BLOCK)
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);

            if (slimefunItem == null && ((Rechargeable) SlimefunItem.getByItem(e.getItem()))
                .removeItemCharge(e.getItem(), 1.5F)) {
                // This allows other plugins to register broken block as player broken
                block.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                e.getPlayer().playSound(blockLocation, Sound.BLOCK_STONE_BREAK, 1.5F, 1F);

                blockLocation.getWorld().dropItem(blockLocation, new ItemStack(Material.OBSIDIAN));
            }
        }
    }

    @EventHandler
    public void onDiamondDrillUpgrade(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        // 1.16 inventory, compare strings
        final DiamondDrill diamondDrill = (DiamondDrill) SlimefunItem.getByID(Items.DIAMOND_DRILL.getItemId());
        if (diamondDrill.isItem(e.getCurrentItem()) && p.getOpenInventory().getType().toString().equals("SMITHING")) {

            e.setCancelled(true);
            Utils.send(p, "&cYou can not upgrade your Diamond Drill!");
        }
    }

    public void checkAndConsume(Player p, FoodLevelChangeEvent e) {
        FoodSynthesizer foodSynth = (FoodSynthesizer) Items.FOOD_SYNTHESIZER.getItem();
        for (ItemStack item : p.getInventory().getContents()) {
            if (foodSynth.isItem(item) && foodSynth.removeItemCharge(item, 3F)) {
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.5F, 1F);
                p.setFoodLevel(20);
                p.setSaturation(5);
                if (e != null) {
                    e.setFoodLevel(20);
                }
            }
        }
    }
}
