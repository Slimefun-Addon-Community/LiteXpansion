package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
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

    @Override
    protected void registerDefaultRecipes() {
        addRecipe(new ItemStack(Material.BLAZE_ROD), new ItemStack(Material.BLAZE_POWDER, 5));
        addRecipe(new ItemStack(Material.COAL_BLOCK), new CustomItem(Items.COAL_DUST, 9));
        addRecipe(new ItemStack(Material.REDSTONE_BLOCK), new ItemStack(Material.REDSTONE, 9));
        addRecipe(new ItemStack(Material.QUARTZ_BLOCK), new ItemStack(Material.QUARTZ, 4));
        addRecipe(new ItemStack(Material.BONE_BLOCK), new ItemStack(Material.BONE_MEAL, 9));
        addRecipe(new ItemStack(Material.BONE), new ItemStack(Material.BONE_MEAL, 4));
        addRecipe(new ItemStack(Material.CLAY), new ItemStack(Material.CLAY_BALL));
        addRecipe(new ItemStack(Material.COAL), new CustomItem(Items.COAL_DUST));
        addRecipe(new ItemStack(Material.COBBLESTONE), new ItemStack(Material.SAND));
        addRecipe(new ItemStack(Material.GLOWSTONE), new ItemStack(Material.GLOWSTONE_DUST, 4));
        addRecipe(new ItemStack(Material.GRAVEL), new ItemStack(Material.FLINT));
        addRecipe(new ItemStack(Material.ICE), new ItemStack(Material.SNOWBALL, 2));
        addRecipe(new ItemStack(Material.QUARTZ_STAIRS), new ItemStack(Material.QUARTZ, 6));
        addRecipe(new ItemStack(Material.SANDSTONE), new ItemStack(Material.SAND));
        addRecipe(new ItemStack(Material.STONE), new ItemStack(Material.COBBLESTONE));
        addRecipe(new ItemStack(Material.WHITE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.ORANGE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.MAGENTA_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.LIGHT_BLUE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.YELLOW_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.LIME_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.PINK_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.GRAY_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.LIGHT_GRAY_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.CYAN_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.PURPLE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.BLUE_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.BROWN_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.GREEN_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.RED_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.BLACK_WOOL), new ItemStack(Material.STRING, 4));
        addRecipe(new ItemStack(Material.GRANITE), new ItemStack(Material.GRAVEL));
        addRecipe(new ItemStack(Material.ANDESITE), new ItemStack(Material.GRAVEL));
        addRecipe(new ItemStack(Material.DIORITE), new ItemStack(Material.GRAVEL));
        addRecipe(new ItemStack(Material.IRON_ORE), new CustomItem(SlimefunItems.IRON_DUST, 2));
        addRecipe(new ItemStack(Material.COAL_ORE), new CustomItem(Items.COAL_DUST, 2));
        addRecipe(new ItemStack(Material.LAPIS_ORE), new CustomItem(Items.LAPIS_DUST, 2));
        addRecipe(new ItemStack(Material.GOLD_ORE), new CustomItem(SlimefunItems.GOLD_DUST, 2));
        addRecipe(new ItemStack(Material.REDSTONE_ORE), new CustomItem(Items.REDSTONE_DUST, 2));
        addRecipe(new ItemStack(Material.DIAMOND_ORE), new CustomItem(Items.DIAMOND_DUST, 2));
        addRecipe(new ItemStack(Material.EMERALD_ORE), new CustomItem(Items.EMERALD_DUST, 2));
        addRecipe(new ItemStack(Material.NETHER_QUARTZ_ORE), new CustomItem(Items.QUARTZ_DUST, 2));

        if (SlimefunPlugin.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)) {
            addRecipe(new ItemStack(Material.NETHER_GOLD_ORE), new CustomItem(SlimefunItems.GOLD_DUST, 2));
            addRecipe(new ItemStack(Material.ANCIENT_DEBRIS), new CustomItem(Items.ANCIENT_DEBRIS_DUST, 2));
        }
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
