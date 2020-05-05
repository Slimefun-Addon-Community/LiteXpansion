package dev.j3fftw.litexpansion.weapons;

import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ChargableItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class NanoBlade extends ChargableItem {

    public NanoBlade() {
        super(Items.LITEXPANSION, Items.NANO_BLADE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, Items.MAG_THOR, null,
                null, Items.MAG_THOR, null,
                null, SlimefunItems.ADVANCED_CIRCUIT_BOARD, null
            }
        );

        addItemHandler((ItemUseHandler) event -> {
            final Enchantment enchantment = Enchantment.getByKey(Constants.NANO_BLADE_ACTIVE_ENCHANT);
            final int removesEnchantmentLvl = event.getItem().removeEnchantment(enchantment);
            if (removesEnchantmentLvl == 0)
                event.getItem().addEnchantment(enchantment, 1);
        });
    }
}
