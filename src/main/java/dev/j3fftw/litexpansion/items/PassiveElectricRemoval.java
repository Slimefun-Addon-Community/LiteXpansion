package dev.j3fftw.litexpansion.items;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public interface PassiveElectricRemoval {

    String LORE_PREFIX = ChatColors.color("&8\u21E8 &e\u26A1 &7");
    Pattern REGEX = Pattern.compile(ChatColors.color("(&c&o)?" + LORE_PREFIX)
        + "[0-9.]+ / [0-9.]+ J");

    float getRemovedChargePerTick();

    float getCapacity();

    boolean isEnabled(@Nonnull ItemMeta meta);

    static void tick(@Nonnull ItemStack is, @Nonnull PassiveElectricRemoval per) {
        final ItemMeta meta = is.getItemMeta();

        if (per.isEnabled(meta)) {
//            final float charge = ChargeUtils.getCharge(meta);
            final float charge = getCharge(meta);
            if (charge == 0) return;

            final float newCharge = Math.max(charge - per.getRemovedChargePerTick(), 0);

//            ChargeUtils.setCharge(meta, newCharge, CAPACITY);
            setCharge(meta, newCharge, per.getCapacity());

            if (meta instanceof Damageable) {
                final double chargePercent = (newCharge / per.getCapacity()) * 100;
                final int percentOfMax = (int) ((chargePercent / 100) * is.getType().getMaxDurability());
                final int damage = Math.max(1, is.getType().getMaxDurability() - percentOfMax);
                ((Damageable) meta).setDamage(damage);
            }

            is.setItemMeta(meta);
        }
    }

    // This is here until ChargeUtils is in the next RC
    @Deprecated
    static float getCharge(ItemMeta meta) {
        NamespacedKey key = SlimefunPlugin.getRegistry().getItemChargeDataKey();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Float value = container.get(key, PersistentDataType.FLOAT);

        // If persistent data is available, we just return this value
        if (value != null) {
            return value;
        }

        // If no persistent data exists, we will just fall back to the lore
        if (meta.hasLore()) {
            for (String line : meta.getLore()) {
                if (REGEX.matcher(line).matches()) {
                    String data =
                        ChatColor.stripColor(PatternUtils.SLASH_SEPARATOR.split(line)[0].replace(LORE_PREFIX, ""));

                    float loreValue = Float.parseFloat(data);
                    container.set(key, PersistentDataType.FLOAT, loreValue);
                    return loreValue;
                }
            }
        }

        return 0;
    }

    @Deprecated
    static void setCharge(@Nonnull ItemMeta meta, float charge, float capacity) {
        BigDecimal decimal = BigDecimal.valueOf(charge).setScale(2, RoundingMode.HALF_UP);
        float value = decimal.floatValue();

        NamespacedKey key = SlimefunPlugin.getRegistry().getItemChargeDataKey();
        meta.getPersistentDataContainer().set(key, PersistentDataType.FLOAT, value);

        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        for (int i = 0; i < lore.size(); i++) {
            String line = lore.get(i);

            if (REGEX.matcher(line).matches()) {
                lore.set(i, LORE_PREFIX + value + " / " + capacity + " J");
                meta.setLore(lore);
                return;
            }
        }

        lore.add(LORE_PREFIX + value + " / " + capacity + " J");
        meta.setLore(lore);
    }
}
