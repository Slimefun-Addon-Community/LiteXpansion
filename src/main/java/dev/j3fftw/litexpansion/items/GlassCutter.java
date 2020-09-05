package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The {@link GlassCutter} is a {@link SimpleSlimefunItem} that breaks
 * glass and glass panes quickly.
 *
 * @author FluffyBear
 *
 */
public class GlassCutter extends SimpleSlimefunItem<ItemUseHandler> implements Listener, Rechargeable {

    private static float COST = 0.5F;

    public GlassCutter() {
        super(Items.LITEXPANSION, Items.GLASS_CUTTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON,
            new ItemStack(Material.SHEARS), Items.CARBON_PLATE, new ItemStack(Material.SHEARS),
            null, Items.CARBON_PLATE, null
        });

        Bukkit.getPluginManager().registerEvents(this, LiteXpansion.getInstance());
    }

    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onGlassCut(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.LEFT_CLICK_BLOCK && isItem(e.getItem())) {
            e.setCancelled(true);

            SlimefunItem slimefunItem = BlockStorage.check(e.getClickedBlock());

            if (slimefunItem != null) {
                return;
            }

            if ((e.getClickedBlock().getType() == Material.GLASS
                || e.getClickedBlock().getType().name().endsWith("_GLASS")
                || e.getClickedBlock().getType().name().endsWith("_PANE"))
                && removeItemCharge(e.getItem(), COST)) {
                e.getClickedBlock().getLocation().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(),
                    new ItemStack(e.getClickedBlock().getType()));
                e.getClickedBlock().setType(Material.AIR);
            }
        }
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 300;
    }
}
