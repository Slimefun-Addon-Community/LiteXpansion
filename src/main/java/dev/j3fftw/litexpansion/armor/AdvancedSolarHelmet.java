package dev.j3fftw.litexpansion.armor;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.gadgets.SolarHelmet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AdvancedSolarHelmet extends SolarHelmet {

    @Nonnull
    private final Type type;

    public AdvancedSolarHelmet(Type type) {
        super(Items.LITEXPANSION, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe(),
            type.getGenerationRate()
        );
        this.type = type;
        Items.ADVANCED_SOLAR_HELMET.addEnchantment(Enchantment.DURABILITY, 1);
        Items.CARBONADO_SOLAR_HELMET.addEnchantment(Enchantment.DURABILITY, 2);
        Items.ENERGIZED_SOLAR_HELMET.addEnchantment(Enchantment.DURABILITY, 3);
        Items.ADVANCEDLX_SOLAR_HELMET.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        Items.HYBRID_SOLAR_HELMET.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        Items.ULTIMATE_SOLAR_HELMET.addUnsafeEnchantment(Enchantment.DURABILITY, 6);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        ADVANCED(Items.ADVANCED_SOLAR_HELMET, 5, new ItemStack[] {
            null, SlimefunItems.SOLAR_GENERATOR, null,
            Items.IRON_PLATE, SlimefunItems.SOLAR_HELMET, Items.IRON_PLATE,
            Items.IRON_PLATE, null, Items.IRON_PLATE
        }),

        CARBONADO(Items.CARBONADO_SOLAR_HELMET, 10, new ItemStack[] {
            null, SlimefunItems.SOLAR_GENERATOR_2, null,
            Items.COPPER_PLATE, Items.ADVANCED_SOLAR_HELMET, Items.COPPER_PLATE,
            Items.COPPER_PLATE, null, Items.COPPER_PLATE
        }),
        ENERGIZED(Items.ENERGIZED_SOLAR_HELMET, 20, new ItemStack[] {
            null, SlimefunItems.SOLAR_GENERATOR_3, null,
            Items.GOLD_PLATE, Items.CARBONADO_SOLAR_HELMET, Items.GOLD_PLATE,
            Items.GOLD_PLATE, null, Items.GOLD_PLATE
        }),
        ADVANCEDLX(Items.ADVANCEDLX_SOLAR_HELMET, 50, new ItemStack[] {
            null, SlimefunItems.SOLAR_GENERATOR_4, null,
            Items.DIAMOND_PLATE, Items.ENERGIZED_SOLAR_HELMET, Items.DIAMOND_PLATE,
            Items.DIAMOND_PLATE, null, Items.DIAMOND_PLATE
        }),
        HYBRID(Items.HYBRID_SOLAR_HELMET, 100, new ItemStack[] {
            null, Items.ADVANCED_SOLAR_PANEL, null,
            Items.THORIUM_PLATE, Items.ADVANCEDLX_SOLAR_HELMET, Items.THORIUM_PLATE,
            Items.THORIUM_PLATE, null, Items.THORIUM_PLATE
        }),
        ULTIMATE(Items.ULTIMATE_SOLAR_HELMET, 250, new ItemStack[] {
            null, Items.ULTIMATE_SOLAR_PANEL, null,
            Items.IRIDIUM_PLATE, Items.HYBRID_SOLAR_HELMET, Items.IRIDIUM_PLATE,
            Items.IRIDIUM_PLATE, null, Items.IRIDIUM_PLATE
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final int generationRate;

        @Nonnull
        private final ItemStack[] recipe;
    }
}

