package dev.j3fftw.litexpansion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class NanoBladeActiveEnchant extends Enchantment {

    public NanoBladeActiveEnchant(NamespacedKey key) {
        super(key);
    }

    @Nonnull
    @Override
    @Deprecated
    public String getName() {
        return "Active";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    @Deprecated
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item.hasItemMeta()) {
            final ItemMeta itemMeta = item.getItemMeta();
            Optional<String> id = SlimefunPlugin.getItemDataService().getItemData(itemMeta);
            if (id.isPresent()) {
                return id.get().equals((Items.NANO_BLADE).getItemId());
            }
        }
        return false;
    }
}
