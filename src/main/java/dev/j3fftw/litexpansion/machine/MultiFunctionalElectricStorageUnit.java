package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.inventory.ItemStack;

public class MultiFunctionalElectricStorageUnit extends Capacitor {

    public MultiFunctionalElectricStorageUnit() {
        super(Items.LITEXPANSION, 4_000_000, Items.MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT,
            RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.GOLD_CABLE, Items.ENERGY_CRYSTAl, Items.GOLD_CABLE,
                Items.ENERGY_CRYSTAl, Items.MACHINE_BLOCK, Items.ENERGY_CRYSTAl,
                Items.GOLD_CABLE, Items.ENERGY_CRYSTAl, Items.GOLD_CABLE
            });
    }
}
