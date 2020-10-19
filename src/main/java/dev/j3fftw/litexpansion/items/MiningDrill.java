package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * The {@link MiningDrill} is a {@link SimpleSlimefunItem} that breaks
 * stone quickly
 *
 * @author FluffyBear
 */
public class MiningDrill extends SimpleSlimefunItem<ItemUseHandler> implements Listener, Rechargeable {

    public MiningDrill() {
        super(Items.LITEXPANSION, Items.MINING_DRILL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON,
            SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.IRON_PICKAXE), SlimefunItems.REINFORCED_PLATE,
            null, SlimefunItems.MEDIUM_CAPACITOR, null
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
