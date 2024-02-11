package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * The {@link GlassCutter} is a {@link SimpleSlimefunItem} that breaks
 * glass and glass panes quickly.
 *
 * @author FluffyBear
 */
public class GlassCutter extends SimpleSlimefunItem<ItemUseHandler> implements Listener, Rechargeable {

    public GlassCutter() {
        super(Items.LITEXPANSION, Items.GLASS_CUTTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
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
    public void onGlassCut(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();

        if (e.getAction() == Action.LEFT_CLICK_BLOCK
            && SlimefunTag.GLASS.isTagged(blockType) && isItem(e.getItem())
            && Slimefun.getProtectionManager().hasPermission(e.getPlayer(), blockLocation,
            Interaction.BREAK_BLOCK)
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);

            if (slimefunItem == null && removeItemCharge(e.getItem(), 0.5F)) {
                blockLocation.getWorld().dropItemNaturally(blockLocation,
                    new ItemStack(blockType));
                block.setType(Material.AIR);
            }
        }
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 300;
    }
}
