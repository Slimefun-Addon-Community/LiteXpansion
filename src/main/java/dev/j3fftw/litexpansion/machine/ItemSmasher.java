package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.machine.api.PoweredMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemSmasher extends AContainer implements PoweredMachine {

    public static final ItemStack concrete = new ItemStack(Material.WHITE_CONCRETE);

    public ItemSmasher() {
        super(Items.LITEXPANSION, Items.ITEM_SMASHER, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
                concrete, new ItemStack(Material.PISTON), concrete,
                concrete, Items.ADVANCED_MACHINE_BLOCK, concrete,
                concrete, SlimefunItems.MEDIUM_CAPACITOR, concrete
            });
    }


    @Override
    protected void registerDefaultRecipes() {
        List<Material> mobDrops = Arrays.asList(
            Material.LEATHER, Material.BEEF, Material.STRING, Material.FEATHER, Material.CHICKEN, Material.COD,
            Material.SALMON, Material.PUFFERFISH, Material.RABBIT, Material.RABBIT_HIDE, Material.BONE,
            Material.INK_SAC, Material.SPIDER_EYE, Material.ROTTEN_FLESH, Material.GUNPOWDER, Material.SPIDER_EYE,
            Material.SLIME_BALL, Material.WHITE_WOOL
        );

        for (Material mobDrop : mobDrops) {
            registerRecipe(1, new ItemStack[] {new ItemStack(mobDrop)},
                new ItemStack[] {new SlimefunItemStack(Items.MOB_ESSENCE, 1), new SlimefunItemStack(Items.MOB_SCRAP, 1)}
            );
        }
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FIRE_CHARGE);
    }

    @Nonnull
    @Override
    public String getInventoryTitle() {
        return "&7Item Smasher";
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "ITEM_SMASHER";
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
