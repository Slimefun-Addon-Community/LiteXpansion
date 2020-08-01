package dev.j3fftw.litexpansion.weapons;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.utils.Constants;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;

public class NanoBlade extends SimpleSlimefunItem<ItemUseHandler> implements Rechargeable {

    public NanoBlade() {
        super(Items.LITEXPANSION, Items.NANO_BLADE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, Items.MAG_THOR, null,
                null, Items.MAG_THOR, null,
                null, SlimefunItems.ADVANCED_CIRCUIT_BOARD, null
            }
        );
    }

    @Override
    public float getMaxItemCharge(ItemStack item) {
        return 500;
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return event -> {
            final Enchantment enchantment = Enchantment.getByKey(Constants.NANO_BLADE_ACTIVE_ENCHANT);
            final int removedLevel = event.getItem().removeEnchantment(enchantment);

            if (removedLevel == 0) {
                event.getItem().addEnchantment(enchantment, 1);
            }
        };
    }
}
