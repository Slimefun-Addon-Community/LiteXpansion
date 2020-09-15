package dev.j3fftw.litexpansion.items;


import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;

import javax.annotation.Nonnull;

/**
 * The {@link Wrench} is a {@link SimpleSlimefunItem} that breaks
 * machines, cargo nodes, and capacitors instantly. Server owners
 * can manually configure if a wrench is required to break machines
 * and the chance that it fails.
 *
 * @author FluffyBear
 */
public class Wrench extends SimpleSlimefunItem<ItemUseHandler> implements DamageableItem {

    public Wrench() {
        super(Items.LITEXPANSION, Items.WRENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.COPPER_INGOT, null, SlimefunItems.COPPER_INGOT,
            null, SlimefunItems.COPPER_INGOT, null,
            null, SlimefunItems.COPPER_INGOT, null
        });
    }

    @Nonnull
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }
}
