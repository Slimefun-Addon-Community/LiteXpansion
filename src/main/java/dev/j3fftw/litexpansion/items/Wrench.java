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

    protected static final int[] cargoSlots = { 19, 20, 21, 28, 29, 30, 37, 38, 39 };
    protected static final String[] wrenchableBlockIds = { SlimefunItems.CARGO_INPUT_NODE.getItemId(), SlimefunItems.CARGO_OUTPUT_NODE.getItemId(), SlimefunItems.CARGO_OUTPUT_NODE_2.getItemId(), SlimefunItems.CARGO_CONNECTOR_NODE.getItemId(), SlimefunItems.SMALL_CAPACITOR.getItemId(), SlimefunItems.MEDIUM_CAPACITOR.getItemId(), SlimefunItems.LARGE_CAPACITOR.getItemId(), SlimefunItems.BIG_CAPACITOR.getItemId(), SlimefunItems.CARBONADO_EDGED_CAPACITOR.getItemId(), SlimefunItems.TRASH_CAN.getItemId() };

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
            if (SlimefunUtils.isItemSimilar(e.getItem(), Items.WRENCH, true, false)) {
                e.setCancelled(true);

                Block interactedBlock = e.getClickedBlock();
                SlimefunItem slimefunBlock = BlockStorage.check(e.getClickedBlock());

                if (slimefunBlock == null) {
                    p.sendMessage(ChatColor.RED + "Hey buddy boy this can only be used on cargo nodes and capacitors!");
                    return;
                }

                for (String wrenchableBlockId : wrenchableBlockIds) {
                    if (slimefunBlock.getID() == wrenchableBlockId) {

                        ItemStack slimefunBlockDrop = slimefunBlock.getItem();

                        BlockStorage.clearBlockInfo(interactedBlock);
                        interactedBlock.getLocation().getWorld().dropItemNaturally(interactedBlock.getLocation(), slimefunBlockDrop);

                        if (slimefunBlock.getID() == SlimefunItems.CARGO_INPUT_NODE.getItemId() || slimefunBlock.getID() == SlimefunItems.CARGO_OUTPUT_NODE_2.getItemId()) {
                            for (int slot : cargoSlots) {
                                ItemStack cargoDrop = BlockStorage.getInventory(interactedBlock).getItemInSlot(slot);
                                if (cargoDrop != null) {
                                    interactedBlock.getLocation().getWorld().dropItemNaturally(interactedBlock.getLocation(), cargoDrop);
                                }
                            }

                        }
                        interactedBlock.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
