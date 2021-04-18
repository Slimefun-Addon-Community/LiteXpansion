package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.machine.api.PoweredMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LiquidCleaner extends AContainer implements PoweredMachine {

    public static final ItemStack wool = new ItemStack(Material.WHITE_WOOL);

    public LiquidCleaner() {
        super(Items.LITEXPANSION, Items.LIQUID_CLEANER, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
                wool, new ItemStack(Material.BUCKET), wool,
                wool, Items.ADVANCED_MACHINE_BLOCK, wool,
                wool, SlimefunItems.LARGE_CAPACITOR, wool
            });
    }

    @Override
    protected void registerDefaultRecipes() {
        addRecipe(Items.MOB_ESSENCE, Items.LIQUID_EXP);
    }

    private void addRecipe(ItemStack input, ItemStack output) {
        registerRecipe(Macerator.TIME, new ItemStack[] {input}, new ItemStack[] {output});
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FIRE_CHARGE);
    }

    @Nonnull
    @Override
    public String getInventoryTitle() {
        return "&7Liquid Cleaner";
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "Liquid Cleaner";
    }

    @Override
    public int getCapacity() {
        return getDefaultEnergyConsumption() * 5;
    }

    @Override
    public int getDefaultEnergyConsumption() {
        return 20_000 / 26;
    }

    @Override
    public int getEnergyConsumption() {
        return this.getFinalEnergyConsumption();
    }

    @Override
    public int getSpeed() {
        return 1;
    }

}
