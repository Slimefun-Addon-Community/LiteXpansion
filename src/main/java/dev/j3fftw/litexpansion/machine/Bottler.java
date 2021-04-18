package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.machine.api.PoweredMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Bottler extends AContainer implements PoweredMachine {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiteXpansion.getInstance(), "bottler"),
        Items.BOTTLER,
        "",
        "&7Used to Bottle"
    );

    public static final ItemStack concrete = new ItemStack(Material.WHITE_CONCRETE);

    public Bottler() {
        super(Items.LITEXPANSION, Items.BOTTLER, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
                concrete, new ItemStack(Material.BUCKET), concrete,
                concrete, Items.ADVANCED_MACHINE_BLOCK, concrete,
                concrete, SlimefunItems.LARGE_CAPACITOR, concrete
            });
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(1, new ItemStack[] {Items.LIQUID_EXP, new ItemStack(Material.GLASS_BOTTLE)},
            new ItemStack[] {new ItemStack(Material.EXPERIENCE_BOTTLE), new ItemStack(Material.BUCKET)}
        );
    }


    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FIRE_CHARGE);
    }

    @Nonnull
    @Override
    public String getInventoryTitle() {
        return "&7Bottler";
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "BOTTLER";
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
