package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.machine.extensions.ChargingStorageUnit;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.inventory.ItemStack;

public class MultiFunctionalElectricStorageUnit extends ChargingStorageUnit {

    public MultiFunctionalElectricStorageUnit() {
        super(Items.LITEXPANSION, 4_000_000, 12, Items.MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT,
            RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.GOLD_CABLE, SlimefunItems.POWER_CRYSTAL, Items.GOLD_CABLE,
                SlimefunItems.POWER_CRYSTAL, Items.MACHINE_BLOCK, SlimefunItems.POWER_CRYSTAL,
                Items.GOLD_CABLE, SlimefunItems.POWER_CRYSTAL, Items.GOLD_CABLE
            });
    }

    @Override
    public String getTitle() {
        return "&8MFE";
    }
}
