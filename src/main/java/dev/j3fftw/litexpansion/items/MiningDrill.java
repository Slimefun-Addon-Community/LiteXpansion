package dev.j3fftw.litexpansion.items;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
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
 * The {@link MiningDrill} is a {@link SimpleSlimefunItem} that breaks
 * stone quickly
 *
 * @author FluffyBear
 */
public class MiningDrill extends SimpleSlimefunItem<ItemUseHandler> implements Listener, Rechargeable {

    ArrayList<Material> breakableBlocks = new ArrayList<>(Arrays.asList( Material.STONE, Material.ANDESITE, Material.DIORITE, Material.GRANITE ));

    public MiningDrill() {
        super(Items.LITEXPANSION, Items.MINING_DRILL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON,
            SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.IRON_PICKAXE), SlimefunItems.REINFORCED_PLATE,
            null, SlimefunItems.MEDIUM_CAPACITOR, null
        });

        Bukkit.getPluginManager().registerEvents(this, LiteXpansion.getInstance());
    }

    @Nonnull
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onStoneDrill(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) return;

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();

        if (breakableBlocks.contains(blockType) && isItem(e.getItem())
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), blockLocation, ProtectableAction.BREAK_BLOCK) 
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);
            
            if (slimefunItem == null && removeItemCharge(e.getItem(), 0.5F)) {
                // This allows other plugins to register broken block as player broken
                block.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());

                // This has to be done because the item in the main hand is not a pickaxe
                if (blockType == Material.STONE) {
                    blockLocation.getWorld().dropItem(blockLocation, new ItemStack(Material.COBBLESTONE));

                } else {
                    blockLocation.getWorld().dropItem(blockLocation, new ItemStack(blockType));
                }
            }
        }
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 1000;
    }
}
