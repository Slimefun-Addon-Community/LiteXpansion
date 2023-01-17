package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.machine.api.PoweredMachine;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Converter extends AContainer implements PoweredMachine {

    public static final int TIME = 5;

    public Converter() {
        super(Items.LITEXPANSION, Items.CONVERTER, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
                new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE),
                new ItemStack(Material.COBBLESTONE), Items.ADVANCED_MACHINE_BLOCK, new ItemStack(Material.COBBLESTONE),
                null, Items.ADVANCED_CIRCUIT, null
            });
    }

    @Override
    protected void registerDefaultRecipes() {
        addRecipe(new ItemStack(Material.COPPER_INGOT), new ItemStack(SlimefunItems.COPPER_INGOT));
        addRecipe(new ItemStack(SlimefunItems.COPPER_INGOT), new ItemStack(Material.COPPER_INGOT));
    }

    private void addRecipe(ItemStack input, ItemStack output) {
        registerRecipe(Converter.TIME, new ItemStack[] {input}, new ItemStack[] {output});
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FIRE_CHARGE);
    }

    @Nonnull
    @Override
    public String getInventoryTitle() {
        return "&6Macerator";
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "MACERATOR";
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
