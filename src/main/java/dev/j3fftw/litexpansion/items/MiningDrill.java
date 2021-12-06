package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

    public MiningDrill(Type type) {
        super(Items.LITEXPANSION, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
    }

    @Nonnull
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 1000;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        MINING(Items.MINING_DRILL, new ItemStack[] {
            null, Items.IRON_PLATE, null,
            Items.IRON_PLATE, Items.IRON_PLATE, Items.IRON_PLATE,
            Items.IRON_PLATE, Items.POWER_UNIT, Items.IRON_PLATE
        }),
        DIAMOND(Items.DIAMOND_DRILL, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.DIAMOND), null,
            new ItemStack(Material.DIAMOND), Items.MINING_DRILL, new ItemStack(Material.DIAMOND)
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}
