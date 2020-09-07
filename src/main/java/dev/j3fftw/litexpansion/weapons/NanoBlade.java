package dev.j3fftw.litexpansion.weapons;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.utils.Constants;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

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

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return event -> {
            final ItemMeta nanoBladeMeta = event.getItem().getItemMeta();
            final Enchantment enchantment = Enchantment.getByKey(Constants.GLOW_ENCHANT);

            if (!nanoBladeMeta.removeEnchant(enchantment)) {
                nanoBladeMeta.addEnchant(enchantment, 1, false);
                nanoBladeMeta.setDisplayName(ChatColor.DARK_GREEN + "Nano Blade" + ChatColor.GREEN + " (On)");
            } else {
                nanoBladeMeta.setDisplayName(ChatColor.DARK_GREEN + "Nano Blade" + ChatColor.RED + " (Off)");
            }
            event.getItem().setItemMeta(nanoBladeMeta);
        };
    }
}
