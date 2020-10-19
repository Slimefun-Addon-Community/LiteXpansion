package dev.j3fftw.litexpansion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowEnchant extends Enchantment {

    private final Set<String> ids = new HashSet<>();

    public GlowEnchant(@Nonnull NamespacedKey key, @Nonnull String[] applicableItems) {
        super(key);
        ids.addAll(Arrays.asList(applicableItems));
    }

    @Nonnull
    @Override
    @Deprecated
    public String getName() {
        return "LX_Glow";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Nonnull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
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
    public boolean conflictsWith(@Nonnull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item.hasItemMeta()) {
            final ItemMeta itemMeta = item.getItemMeta();
            final Optional<String> id = SlimefunPlugin.getItemDataService().getItemData(itemMeta);

            if (id.isPresent()) {
                return ids.contains(id.get());
            }
        }
        return false;
    }

}
