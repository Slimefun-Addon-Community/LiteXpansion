package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AGenerator;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Generator extends AGenerator {

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

        registerFuel(new MachineFuel(80, new ItemStack(Material.COAL_BLOCK)));
        registerFuel(new MachineFuel(12, new ItemStack(Material.BLAZE_ROD)));
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)));

        // Coal & Charcoal
        registerFuel(new MachineFuel(8, new ItemStack(Material.COAL)));
        registerFuel(new MachineFuel(8, new ItemStack(Material.CHARCOAL)));

        // Logs
        for (Material mat : Tag.LOGS.getValues()) {
            registerFuel(new MachineFuel(2, new ItemStack(mat)));
        }

        // Wooden Planks
        for (Material mat : Tag.PLANKS.getValues()) {
            registerFuel(new MachineFuel(1, new ItemStack(mat)));
        }
    }

    @Override
    public int getCapacity() {
        return 4000;
    }
}
