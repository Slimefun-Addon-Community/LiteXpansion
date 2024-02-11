package dev.j3fftw.litexpansion.nms;

import com.google.common.base.Preconditions;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class XEnchantment extends Enchantment {

    private final Set<String> ids = new HashSet<>();
    public XEnchantment(String[] ids){
        super(Rarity.COMMON,EnchantmentCategory.WEAPON,EquipmentSlot.values());
        this.ids.addAll(Arrays.asList(ids));
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int var0) {
        return 0;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }


    @Override
    public int getMaxCost(int var0) {
        return 0;
    }

    @Override
    public boolean isTreasureOnly() {
        return false;
    }

    @Override
    public boolean isCurse() {
        return false;
    }

    @Override
    protected boolean checkCompatibility(Enchantment var0) {
        return false;
    }

    @Override
    public boolean canEnchant(ItemStack var0) {
        org.bukkit.inventory.ItemStack item = CraftItemStack.asBukkitCopy(var0);
        if (item.hasItemMeta()) {
            final ItemMeta itemMeta = item.getItemMeta();
            Preconditions.checkNotNull(itemMeta, "can not be null");
            final Optional<String> id = Slimefun.getItemDataService().getItemData(itemMeta);

            if (id.isPresent()) {
                return ids.contains(id.get());
            }
        }
        return false;
    }

}
