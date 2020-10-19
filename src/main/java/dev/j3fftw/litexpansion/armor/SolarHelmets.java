package dev.j3fftw.litexpansion.armor;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.gadgets.SolarHelmet;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class SolarHelmets extends SolarHelmet {

    private final Type type;

    public SolarHelmets(Type type) {
        super(Items.LITEXPANSION, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe(),
            type.getGenerationRate()
        );
        this.type = type;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        ADVANCED(Items.ADVANCED_SOLAR_HELMET, 1, new ItemStack[] {
            Items.IRON_PLATE, SlimefunItems.SOLAR_GENERATOR_2, Items.IRON_PLATE,
            Items.IRON_PLATE, null, Items.IRON_PLATE,
            null, null, null
        }),

        CARBONADO(Items.CARBONADO_SOLAR_HELMET, 2, new ItemStack[] {
            Items.COPPER_PLATE, SlimefunItems.SOLAR_GENERATOR_3, Items.COPPER_PLATE,
            Items.COPPER_PLATE, null, Items.COPPER_PLATE,
            null, null, null
        }),
        ENERGIZED(Items.ENERGIZED_SOLAR_HELMET, 3, new ItemStack[] {
            Items.GOLD_PLATE, SlimefunItems.SOLAR_GENERATOR_3, Items.GOLD_PLATE,
            Items.GOLD_PLATE, null, Items.GOLD_PLATE,
            null, null, null
        }),
        ADVANCEDLX(Items.ADVANCEDLX_SOLAR_HELMET, 5, new ItemStack[] {
            Items.DIAMOND_PLATE, SlimefunItems.SOLAR_GENERATOR_4, Items.DIAMOND_PLATE,
            Items.DIAMOND_PLATE, null, Items.DIAMOND_PLATE,
            null, null, null
        }),
        HYBRID(Items.HYBRID_SOLAR_HELMET, 10, new ItemStack[] {
            Items.THORIUM_PLATE, Items.ADVANCED_SOLAR_PANEL, Items.THORIUM_PLATE,
            Items.THORIUM_PLATE, null, Items.THORIUM_PLATE,
            null, null, null
        }),
        ULTIMATE(Items.ULTIMATE_SOLAR_HELMET, 20, new ItemStack[] {
            Items.IRIDIUM_PLATE, Items.ULTIMATE_SOLAR_PANEL, Items.IRIDIUM_PLATE,
            Items.IRIDIUM_PLATE, null, Items.IRIDIUM_PLATE,
            null, null, null
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final int GenerationRate;

        @Nonnull
        private final ItemStack[] recipe;
    }
}

