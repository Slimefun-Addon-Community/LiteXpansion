package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class Wrench extends SimpleSlimefunItem<ItemUseHandler> implements Listener {

    public Wrench() {
        super(Items.LITEXPANSION, Items.WRENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.REFINED_IRON, SlimefunItems.REINFORCED_PLATE, Items.REFINED_IRON,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.ALUMINUM_INGOT, SlimefunItems.REINFORCED_PLATE,
                Items.REFINED_IRON, SlimefunItems.REINFORCED_PLATE, Items.REFINED_IRON
        });

        Bukkit.getPluginManager().registerEvents(this, LiteXpansion.getInstance());
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @EventHandler
    public void onWrenchInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block interactedBlock = e.getClickedBlock();

            if (SlimefunUtils.isItemSimilar(e.getItem(), Items.WRENCH, true, false)) {
                e.setCancelled(true);

                final SlimefunItem slimefunBlock = BlockStorage.check(e.getClickedBlock());

                if (BlockStorage.hasBlockInfo(interactedBlock)) {

                    ItemStack slimefunBlockDrop = slimefunBlock.getItem();

                    BlockStorage.clearBlockInfo(interactedBlock);
                    interactedBlock.getLocation().getWorld().dropItemNaturally(interactedBlock.getLocation(), slimefunBlockDrop);

                    if (BlockStorage.hasInventory(interactedBlock)) {
                        p.sendMessage("This is an inventory block");
                        BlockMenu blockInventory = BlockStorage.getInventory(interactedBlock);
                        int[] inputSlots = ((InventoryBlock) interactedBlock).getInputSlots();
                        for (int slot : inputSlots) {
                            p.sendMessage("The input slots are " + slot);
                            p.sendMessage("The item in this slot is " + blockInventory.getItemInSlot(slot));
                            interactedBlock.getLocation().getWorld().dropItemNaturally(interactedBlock.getLocation(), blockInventory.getItemInSlot(slot));
                        }
                    }
                    interactedBlock.setType(Material.AIR);
                }
            }
        }
    }
}
