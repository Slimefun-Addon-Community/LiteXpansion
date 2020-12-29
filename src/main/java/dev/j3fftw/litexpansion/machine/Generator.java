package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.generators.CoalGenerator;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

public class Generator extends CoalGenerator {

    public Generator() {
        super(Items.LITEXPANSION, Items.GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            null, Items.RE_BATTERY, null,
            null, Items.MACHINE_BLOCK, null,
            null, new ItemStack(Material.FURNACE), null
        });
    }

    @Nonnull
    @Override
    public String getInventoryTitle() {
        return "&7Generator";
    }

    @Nonnull
    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FIRE_CHARGE);
    }

    @Override
    public int getEnergyProduction() {
        return 10;
    }

    @Override
    protected void registerDefaultFuelTypes() {
        super.registerDefaultFuelTypes();
        
        super.registerFuel(new MachineFuel(100, new ItemStack(Material.LAVA_BUCKET)));
        super.registerFuel(new MachineFuel(8, new ItemStack(Material.CHARCOAL)));
        super.registerFuel(new MachineFuel(6, new ItemStack(Material.SCAFFOLDING)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_AXE)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_HOE)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_PICKAXE)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_SHOVEL)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_SWORD)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.LADDER)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.CRAFTING_TABLE)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.CARTOGRAPHY_TABLE)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.FLETCHING_TABLE)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.SMITHING_TABLE)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.LOOM)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.BOOKSHELF)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.LECTERN)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.COMPOSTER)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.CHEST)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.TRAPPED_CHEST)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.BARREL)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.DAYLIGHT_DETECTOR)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.JUKEBOX)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.NOTE_BLOCK)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.BROWN_MUSHROOM_BLOCK)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.RED_MUSHROOM_BLOCK)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.MUSHROOM_STEM)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.CROSSBOW)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.BOW)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.FISHING_ROD)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.BOWL)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.STICK)));
        super.registerFuel(new MachineFuel(1, new ItemStack(Material.BAMBOO)));

        // Banners
        for (Material mat : Tag.ITEMS_BANNERS.getValues()) {
            super.registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        // Carpets
        for (Material mat : Tag.CARPETS.getValues()) {
            super.registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        // Saplings
        for (Material mat : Tag.SAPLINGS.getValues()) {
            super.registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        // Wool
        for (Material mat : Tag.WOOL.getValues()) {
            super.registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

    }

    @Override
    public int getCapacity() {
        return 4000;
    }
}
