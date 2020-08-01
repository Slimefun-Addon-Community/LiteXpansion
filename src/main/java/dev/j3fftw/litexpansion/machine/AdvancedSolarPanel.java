package dev.j3fftw.litexpansion.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public class AdvancedSolarPanel extends SlimefunItem implements InventoryBlock, EnergyNetProvider {

    private static final int PROGRESS_SLOT = 4;

    private static final CustomItem generatingItem = new CustomItem(Material.GREEN_STAINED_GLASS_PANE,
        "&cNot Generating..."
    );

    private final Type type;

    public AdvancedSolarPanel(Type type) {
        super(Items.LITEXPANSION, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;

        createPreset(this, type.getItem().getImmutableMeta().getDisplayName().orElse("&7Solar Panel"),
            blockMenuPreset -> {
                for (int i = 0; i < 9; i++)
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());

                blockMenuPreset.addItem(PROGRESS_SLOT, generatingItem);
            });
    }
    
    @Override
    public int getGeneratedOutput(Location l, Config data) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return 0;

        final int stored = ChargableBlock.getCharge(l);
        final boolean canGenerate = stored < getCapacity() && !inv.getBlock().isBlockPowered();
        final int rate = canGenerate ? getGeneratingAmount(inv.getBlock(), l.getWorld()) : 0;

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
            inv.replaceExistingItem(PROGRESS_SLOT,
                canGenerate ? new CustomItem(Material.GREEN_STAINED_GLASS_PANE, "&aGenerating",
                    "", "&7Generating at &6" + rate + " J",
                    "", "&7Stored: &6" + (stored + rate) + " J"
                )
                    : new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cNot Generating",
                    "", "&7Not generating power, already at maximum capacity.",
                    "", "&7Stored: &6" + stored + " J")
            );
        }

        return rate;
    }
    
    @Override
    public boolean willExplode(Location l, Config data) {
        return false;
    }

    private int getGeneratingAmount(@Nonnull Block b, @Nonnull World world) {
        if (world.getEnvironment() == World.Environment.NETHER) return this.type.getDayGenerationRate();
        if (world.getEnvironment() == World.Environment.THE_END) return this.type.getNightGenerationRate();

        // Note: You need to get the block above for the light check, the block itself is always 0
        if (world.isThundering() || world.hasStorm() || world.getTime() >= 13000
            || b.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15
        )
            return this.type.getNightGenerationRate();
        else
            return this.type.getDayGenerationRate();
    }

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

        ADVANCED(Items.ADVANCED_SOLAR_PANEL, 80, 10, 320, 320_000, new ItemStack[] {
            Items.REINFORCED_GLASS, Items.REINFORCED_GLASS, Items.REINFORCED_GLASS,
            Items.ADVANCED_ALLOY, SlimefunItems.SOLAR_PANEL, Items.ADVANCED_ALLOY,
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.ADVANCED_MACHINE_BLOCK, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        HYBRID(Items.HYBRID_SOLAR_PANEL, 640, 80, 1200, 1_000_000, new ItemStack[] {
            Items.CARBON_PLATE, new ItemStack(Material.LAPIS_BLOCK), Items.CARBON_PLATE,
            Items.IRIDIUM_PLATE, Items.ADVANCED_MACHINE_BLOCK, Items.IRIDIUM_PLATE,
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.IRIDIUM_PLATE, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        ULTIMATE(Items.ULTIMATE_SOLAR_PANEL, 5120, 640, 5120, 10_000_000, new ItemStack[] {
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
