package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.generators.CoalGenerator;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.generators.LavaGenerator;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

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

    LavaGenerator
    @Override
    protected void registerDefaultFuelTypes() {

        registerFuel(new MachineFuel(100, new ItemStack(Material.LAVA_BUCKET)));
        registerFuel(new MachineFuel(80, new ItemStack(Material.COAL_BLOCK)));
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)));
        registerFuel(new MachineFuel(12, new ItemStack(Material.BLAZE_ROD)));
        registerFuel(new MachineFuel(8, new ItemStack(Material.COAL)));
        registerFuel(new MachineFuel(8, new ItemStack(Material.CHARCOAL)));

        for (Material mat : Tag.ITEMS_BOATS.getValues()) {
            registerFuel(new MachineFuel(6, new ItemStack(mat)));
        }

        registerFuel(new MachineFuel(6, new ItemStack(Material.SCAFFOLDING)));

        for (Material mat : Tag.LOGS.getValues()) {
            registerFuel(new MachineFuel(2, new ItemStack(mat)));
        }

        for (Material mat : Tag.PLANKS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.WOODEN_SLABS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.WOODEN_BUTTONS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.WOODEN_FENCES.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.WOODEN_TRAPDOORS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.WOODEN_PRESSURE_PLATES.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }
        for (Material mat : Tag.WOODEN_DOORS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.BANNERS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.CARPETS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.SIGNS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.SAPLINGS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        for (Material mat : Tag.WOOL.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }

        registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_AXE)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_HOE)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_PICKAXE)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_SHOVEL)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.WOODEN_SWORD)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.LADDER)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.CRAFTING_TABLE)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.CARTOGRAPHY_TABLE)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.FLETCHING_TABLE)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.SMITHING_TABLE)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.LOOM)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.BOOKSHELF)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.LECTERN)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.COMPOSTER)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.CHEST)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.TRAPPED_CHEST)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.BARREL)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.DAYLIGHT_DETECTOR)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.JUKEBOX)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.NOTE_BLOCK)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.BROWN_MUSHROOM_BLOCK)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.RED_MUSHROOM_BLOCK)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.MUSHROOM_STEM)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.CROSSBOW)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.BOW)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.FISHING_ROD)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.BOWL)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.STICK)));
        registerFuel(new MachineFuel(1, new ItemStack(Material.BAMBOO)));

    }

    @Override
    public int getCapacity() {
        return 4000;
    }
}
