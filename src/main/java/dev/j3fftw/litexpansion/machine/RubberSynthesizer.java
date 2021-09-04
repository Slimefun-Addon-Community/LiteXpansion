package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.machine.api.PoweredMachine;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class RubberSynthesizer extends AContainer implements RecipeDisplayItem, PoweredMachine {

    public static final int ENERGY_CONSUMPTION = 20_000 / 26;
    public static final int CAPACITY = ENERGY_CONSUMPTION * 5;

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiteXpansion.getInstance(), "rubber_synthesizer"), Items.RUBBER_SYNTHESIZER_MACHINE
    );

    public RubberSynthesizer() {
        super(Items.LITEXPANSION, Items.RUBBER_SYNTHESIZER_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.REINFORCED_PLATE,
                new ItemStack(Material.PISTON), Items.MACHINE_BLOCK, new ItemStack(Material.PISTON),
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.REINFORCED_PLATE
            });
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(13, new ItemStack[] {new CustomItemStack(SlimefunItems.OIL_BUCKET)},
            new ItemStack[] {new CustomItemStack(Items.RUBBER, 8), new ItemStack(Material.BUCKET)});
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 2);

        for (MachineRecipe recipe : recipes) {
            displayRecipes.add(recipe.getInput()[0]);
            displayRecipes.add(recipe.getOutput()[recipe.getOutput().length - 1]);
        }

        return displayRecipes;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FIRE_CHARGE);
    }

    @Nonnull
    @Override
    public String getInventoryTitle() {
        return "&6Rubber Synthesizer";
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "RUBBER_SYNTHESIZER";
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public int getEnergyConsumption() {
        return getFinalEnergyConsumption();
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public int getDefaultEnergyConsumption() {
        return 20_000 / 26;
    }
}
