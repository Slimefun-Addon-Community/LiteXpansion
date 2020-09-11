package dev.j3fftw.litexpansion.items;

import javax.annotation.Nonnull;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.utils.Utils;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

/**
 * The {@link DiamondDrill} is a {@link SimpleSlimefunItem} that is
 * an upgrade from the {@link MiningDrill} and breaks obsidian quickly
 *
 * @author FluffyBear
 */
public class DiamondDrill extends SimpleSlimefunItem<ItemUseHandler> implements Listener, Rechargeable {

    public DiamondDrill() {
        super(Items.LITEXPANSION, Items.DIAMOND_DRILL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND),
            SlimefunItems.REINFORCED_PLATE, Items.MINING_DRILL, SlimefunItems.REINFORCED_PLATE,
            null, Items.ADVANCED_CIRCUIT, null
        });

        Bukkit.getPluginManager().registerEvents(this, LiteXpansion.getInstance());
    }

    @Nonnull
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onObsidiaDrill(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) return;

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();

        if (blockType.equals(Material.OBSIDIAN) && isItem(e.getItem())
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), blockLocation, ProtectableAction.BREAK_BLOCK) 
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);
            
            if (slimefunItem == null && removeItemCharge(e.getItem(), 1.5F)) {
                // This allows other plugins to register broken block as player broken
                block.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());

                blockLocation.getWorld().dropItem(blockLocation, new ItemStack(Material.OBSIDIAN));
            }
        }
    }

    @EventHandler
    public void onDiamondUpgrade(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        // 1.16 inventory, compare strings
        if (isItem(e.getCurrentItem()) && p.getOpenInventory().getType().toString().equals("SMITHING")) {

            e.setCancelled(true);
            Utils.send(p, "&cYou can not upgrade your Diamond Drill!");
        }
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 1000;
    }
}
