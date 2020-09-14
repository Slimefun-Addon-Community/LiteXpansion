package dev.j3fftw.litexpansion.items;

import javax.annotation.Nonnull;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.utils.Utils;
import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.cargo.TrashCan;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

/**
 * The {@link Wrench} is a {@link SimpleSlimefunItem} that breaks
 * machines, cargo nodes, and capacitors instantly. Server owners
 * can manually configure if a wrench is required to break machines
 * and the chance that it fails.
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

        if (e.getHand() == EquipmentSlot.HAND && isItem(p.getInventory().getItemInMainHand())
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(),
            block.getLocation(), ProtectableAction.BREAK_BLOCK)) {

            SlimefunItem sfBlock = BlockStorage.check(block);

            if (sfBlock instanceof EnergyNetComponent) {

                if (Constants.MACHINE_BREAK_REQUIRES_WRENCH) {

                    double random = Math.random();
                    wrenchBlock(p, block, random <= Constants.WRENCH_FAIL_CHANCE, true);

                } else {
                    wrenchBlock(p, block, false, true);
                }

                damageItem(p, wrench);

            } else if (sfBlock != null && (sfBlock.getID().startsWith("CARGO_NODE")
                || sfBlock instanceof TrashCan)) {

                wrenchBlock(p, block, false, true);
                damageItem(p, wrench);

            } else {
                Utils.send(p, "&cYou can not use the wrench on this block!");
            }
        }
    }

    public static void wrenchBlock(Player p, Block block, boolean fail, boolean byInteract) {

        if (fail) {

            World blockWorld = block.getWorld();
            Location blockLocation = block.getLocation();
            SlimefunItem slimefunBlock = BlockStorage.check(block);
            BlockMenu blockInventory = BlockStorage.getInventory(block);

            if (BlockStorage.hasInventory(block)) {
                int[] inputSlots = ((InventoryBlock) slimefunBlock).getInputSlots();
                for (int slot : inputSlots) {

                    if (blockInventory.getItemInSlot(slot) != null) {
                        blockWorld.dropItemNaturally(blockLocation, blockInventory.getItemInSlot(slot));
                    }
                }
                int[] outputSlots = ((InventoryBlock) slimefunBlock).getOutputSlots();
                for (int slot : outputSlots) {
                    if (blockInventory.getItemInSlot(slot) != null) {
                        blockWorld.dropItemNaturally(blockLocation, blockInventory.getItemInSlot(slot));
                    }
                }
            }

            BlockStorage.clearBlockInfo(block);
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), Items.MACHINE_BLOCK.clone());

            if (byInteract) {
                Utils.send(p, "&cOh no! Your wrench failed!");
            }

        } else {
            BlockBreakEvent breakEvent = new BlockBreakEvent(block, p);
            Bukkit.getServer().getPluginManager().callEvent(breakEvent);
        }
    }

    @Override
    public boolean isDamageable() {
        return true;
    }
}
