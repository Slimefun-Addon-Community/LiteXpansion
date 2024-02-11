package dev.j3fftw.litexpansion.machine.generators;

import com.google.common.base.Preconditions;
import dev.j3fftw.extrautils.interfaces.InventoryBlock;
import dev.j3fftw.extrautils.utils.Utils;
import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AdvancedSolarPanel extends SlimefunItem implements InventoryBlock, EnergyNetProvider {

    public static final int ADVANCED_DAY_RATE = 80;
    public static final int ADVANCED_NIGHT_RATE = 10;
    public static final int ADVANCED_OUTPUT = 320;
    public static final int ADVANCED_STORAGE = 320_000;
    public static final int HYBRID_DAY_RATE = 640;
    public static final int HYBRID_NIGHT_RATE = 80;
    public static final int HYBRID_OUTPUT = 1200;
    public static final int HYBRID_STORAGE = 1_000_000;
    public static final int ULTIMATE_DAY_RATE = 5120;
    public static final int ULTIMATE_NIGHT_RATE = 640;
    public static final int ULTIMATE_OUTPUT = 5120;
    public static final int ULTIMATE_STORAGE = 10_000_000;
    private static final int PROGRESS_SLOT = 4;
    private static final CustomItemStack generatingItem = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
        "&cNot Generating..."
    );
    private final Type type;

    public AdvancedSolarPanel(Type type) {
        super(Items.LITEXPANSION, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;

        createPreset(this, type.getItem().getItemMetaSnapshot().getDisplayName().orElse("&7Solar Panel"),
            blockMenuPreset -> {
                for (int i = 0; i < 9; i++) {
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
                blockMenuPreset.addItem(PROGRESS_SLOT, generatingItem);
            });
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) {
            return 0;
        }

        final int stored = getCharge(l);
        final boolean canGenerate = stored < getCapacity();
        Preconditions.checkNotNull(l.getWorld());
        final int rate = canGenerate ? getGeneratingAmount(inv.getBlock(), l.getWorld()) : 0;

        String generationType = "&4Unknown";

        if (l.getWorld().getEnvironment() == World.Environment.NETHER) {
            generationType = "&cNether &e(Day)";
        } else if (l.getWorld().getEnvironment() == World.Environment.THE_END) {
            generationType = "&5End &8(Night)";
        } else if (rate == this.type.getDayGenerationRate()) {
            generationType = "&aOverworld &e(Day)";
        } else if (rate == this.type.getNightGenerationRate()) {
            generationType = "&aOverworld &8(Night)";
        }

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
            inv.replaceExistingItem(PROGRESS_SLOT,
                canGenerate ? new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aGenerating",
                    "", "&bRate: " + generationType,
                    "&7Generating at &6" + Utils.powerFormatAndFadeDecimals(Utils.perTickToPerSecond(rate)) + " J/s " +
                        "&8(" + rate + " J/t)",
                    "", "&7Stored: &6" + Utils.powerFormatAndFadeDecimals((double) stored + rate) + " J"
                )
                    : new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&cNot Generating",
                    "", "&7Generator has reached maximum capacity.",
                    "", "&7Stored: &6" + Utils.powerFormatAndFadeDecimals(stored) + " J")
            );
        }

        return rate;
    }

    @Override
    public boolean willExplode(@Nonnull Location l, @Nonnull Config data) {
        return false;
    }

    private int getGeneratingAmount(@Nonnull Block b, @Nonnull World world) {
        if (world.getEnvironment() == World.Environment.NETHER) {
            return this.type.getDayGenerationRate();
        }
        if (world.getEnvironment() == World.Environment.THE_END) {
            return this.type.getNightGenerationRate();
        }

        // Note: You need to get the block above for the light check, the block itself is always 0
        if (world.isThundering() || world.hasStorm() || world.getTime() >= 13000
            || b.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15
        ) {
            return this.type.getNightGenerationRate();
        } else {
            return this.type.getDayGenerationRate();
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int getCapacity() {
        return this.type.getStorage();
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        ADVANCED(Items.ADVANCED_SOLAR_PANEL, ADVANCED_DAY_RATE, ADVANCED_NIGHT_RATE, ADVANCED_OUTPUT,
            ADVANCED_STORAGE, new ItemStack[] {
            Items.REINFORCED_GLASS, Items.REINFORCED_GLASS, Items.REINFORCED_GLASS,
            Items.ADVANCED_ALLOY, SlimefunItems.SOLAR_GENERATOR_4, Items.ADVANCED_ALLOY,
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.ADVANCED_MACHINE_BLOCK, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        HYBRID(Items.HYBRID_SOLAR_PANEL, HYBRID_DAY_RATE, HYBRID_NIGHT_RATE, HYBRID_OUTPUT, HYBRID_STORAGE,
            new ItemStack[] {
                Items.CARBON_PLATE, new ItemStack(Material.LAPIS_BLOCK), Items.CARBON_PLATE,
                Items.IRIDIUM_PLATE, Items.ADVANCED_MACHINE_BLOCK, Items.IRIDIUM_PLATE,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.IRIDIUM_PLATE, SlimefunItems.ADVANCED_CIRCUIT_BOARD
            }),

        ULTIMATE(Items.ULTIMATE_SOLAR_PANEL, ULTIMATE_DAY_RATE, ULTIMATE_NIGHT_RATE, ULTIMATE_OUTPUT,
            ULTIMATE_STORAGE, new ItemStack[] {
            Items.HYBRID_SOLAR_PANEL, Items.HYBRID_SOLAR_PANEL, Items.HYBRID_SOLAR_PANEL,
            Items.HYBRID_SOLAR_PANEL, SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.HYBRID_SOLAR_PANEL,
            Items.HYBRID_SOLAR_PANEL, Items.HYBRID_SOLAR_PANEL, Items.HYBRID_SOLAR_PANEL,
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int dayGenerationRate;
        private final int nightGenerationRate;
        private final int output;
        private final int storage;

        @Nonnull
        private final ItemStack[] recipe;
    }
}
