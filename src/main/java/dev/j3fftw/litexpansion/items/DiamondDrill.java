package dev.j3fftw.litexpansion.items;

import javax.annotation.Nonnull;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.event.Event;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;

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
    }

    @Nonnull
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 1000;
    }
}
