package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.inventory.ItemStack;

public class MultiFunctionalStorageUnit extends Capacitor {

    public MultiFunctionalStorageUnit() {
        super(Items.LITEXPANSION, 40_000_000, Items.MULTI_FUNCTIONAL_STORAGE_UNIT,
            RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.LAPOTRON_CRYSTAL, Items.ADVANCED_CIRCUIT, Items.LAPOTRON_CRYSTAL,
                Items.LAPOTRON_CRYSTAL, Items.MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT, Items.LAPOTRON_CRYSTAL,
                Items.LAPOTRON_CRYSTAL, Items.ADVANCED_MACHINE_BLOCK, Items.LAPOTRON_CRYSTAL
            });
    }
}
