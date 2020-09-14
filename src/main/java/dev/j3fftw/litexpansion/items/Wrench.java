package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.utils.Utils;
import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.cargo.TrashCan;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import javax.annotation.Nonnull;

/**
 * The {@link Wrench} is a {@link SimpleSlimefunItem} that breaks
 * machines, cargo nodes, and capacitors instantly.
 *
 * @author FluffyBear
 */
public class Wrench extends SimpleSlimefunItem<ItemUseHandler> implements Listener, DamageableItem {

    public Wrench() {
        super(Items.LITEXPANSION, Items.WRENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON,
            new ItemStack(Material.SHEARS), Items.ADVANCED_CIRCUIT, new ItemStack(Material.SHEARS),
            null, Items.CARBON_PLATE, null
        });

        Bukkit.getPluginManager().registerEvents(this, LiteXpansion.getInstance());
    }

    @Nonnull
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onWrenchUse(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        ItemStack wrench = p.getInventory().getItemInMainHand();
        final Block block = e.getClickedBlock();

        if (block == null) return;

        if (e.getHand() == EquipmentSlot.HAND && isItem(p.getInventory().getItemInMainHand())) {

            SlimefunItem sfBlock = BlockStorage.check(block);

            if (sfBlock != null && (sfBlock instanceof EnergyNetComponent
                || sfBlock.getID().startsWith("CARGO_NODE")
                || sfBlock instanceof TrashCan)
            ) {

                BlockBreakEvent breakEvent = new BlockBreakEvent(block, p);
                Bukkit.getServer().getPluginManager().callEvent(breakEvent);
                damageItem(p, wrench);

            } else {
                Utils.send(p, "&cYou can not use the wrench on this block!");
            }
        }
    }

    @Override
    public boolean isDamageable() {
        return true;
    }
}
