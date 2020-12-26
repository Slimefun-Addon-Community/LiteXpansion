package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Macerator extends AContainer {

    public static final int ENERGY_CONSUMPTION = 20_000 / 26;
    public static final int CAPACITY = ENERGY_CONSUMPTION * 5;
    public static final int TIME = 5;

    public Macerator() {
        super(Items.LITEXPANSION, Items.MACERATOR, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
                new ItemStack(Material.FLINT), new ItemStack(Material.FLINT), new ItemStack(Material.FLINT),
                new ItemStack(Material.COBBLESTONE), Items.MACHINE_BLOCK, new ItemStack(Material.COBBLESTONE),
                null, Items.ELECTRONIC_CIRCUIT, null
            });
    }

    protected void registerDefaultRecipes() {
        addRecipe(TIME, new ItemStack(Material.BLAZE_ROD), new ItemStack(Material.BLAZE_POWDER, 5));
        addRecipe(TIME, new ItemStack(Material.COAL_BLOCK), new CustomItem(Items.COAL_DUST, 9));
        addRecipe(TIME, new ItemStack(Material.REDSTONE_BLOCK), new ItemStack(Material.REDSTONE, 9));
        addRecipe(TIME, new ItemStack(Material.QUARTZ_BLOCK), new ItemStack(Material.QUARTZ, 4));
        addRecipe(TIME, new ItemStack(Material.BONE_BLOCK), new ItemStack(Material.BONE_MEAL, 9));
        addRecipe(TIME, new ItemStack(Material.BONE), new ItemStack(Material.BONE_MEAL, 4));
        addRecipe(TIME, new ItemStack(Material.CLAY), new ItemStack(Material.CLAY_BALL));
        addRecipe(TIME, new ItemStack(Material.COAL), new CustomItem(Items.COAL_DUST));
        addRecipe(TIME, new ItemStack(Material.COBBLESTONE), new ItemStack(Material.SAND));
        addRecipe(TIME, new ItemStack(Material.GLOWSTONE), new ItemStack(Material.GLOWSTONE_DUST, 4));
        addRecipe(TIME, new ItemStack(Material.GRAVEL), new ItemStack(Material.FLINT));
        addRecipe(TIME, new ItemStack(Material.ICE), new ItemStack(Material.SNOWBALL, 2));
        addRecipe(TIME, new ItemStack(Material.QUARTZ_STAIRS), new ItemStack(Material.QUARTZ, 6));
        addRecipe(TIME, new ItemStack(Material.SANDSTONE), new ItemStack(Material.SAND));
        addRecipe(TIME, new ItemStack(Material.STONE), new ItemStack(Material.COBBLESTONE));
        addRecipe(TIME, new ItemStack(Material.WHITE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.ORANGE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.MAGENTA_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.LIGHT_BLUE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.YELLOW_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.LIME_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.PINK_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.GRAY_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.LIGHT_GRAY_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.CYAN_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.PURPLE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.BLUE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.BROWN_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.GREEN_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.RED_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.BLACK_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(TIME, new ItemStack(Material.GRANITE), new ItemStack(Material.GRAVEL));
        addRecipe(TIME, new ItemStack(Material.ANDESITE), new ItemStack(Material.GRAVEL));
        addRecipe(TIME, new ItemStack(Material.DIORITE), new ItemStack(Material.GRAVEL));
    }

    private void addRecipe(int seconds, ItemStack input, ItemStack output) {
        registerRecipe(seconds, new ItemStack[] {input}, new ItemStack[] {output});
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
        return CAPACITY;
    }

    @Override
    public int getEnergyConsumption() {
        return ENERGY_CONSUMPTION;
    }

    @Override
    public int getSpeed() {
        return 1;
    }
}
