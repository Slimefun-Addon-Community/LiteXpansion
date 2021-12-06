package dev.j3fftw.litexpansion.items;

import io.github.thebusybiscuit.slimefun4.utils.ChargeUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public interface PassiveElectricRemoval {

    float getRemovedChargePerTick();

    float getCapacity();

    boolean isEnabled(@Nonnull ItemMeta meta);

    static void tick(@Nonnull ItemStack is, @Nonnull PassiveElectricRemoval per) {
        final ItemMeta meta = is.getItemMeta();

        if (meta != null && per.isEnabled(meta)) {
            final float charge = ChargeUtils.getCharge(meta);
            if (charge == 0) return;

            final float newCharge = Math.max(charge - per.getRemovedChargePerTick(), 0);

            ChargeUtils.setCharge(meta, newCharge, per.getCapacity());

            if (meta instanceof Damageable) {
                final double chargePercent = (newCharge / per.getCapacity()) * 100;
                final int percentOfMax = (int) ((chargePercent / 100) * is.getType().getMaxDurability());
                final int damage = Math.max(1, is.getType().getMaxDurability() - percentOfMax);
                ((Damageable) meta).setDamage(damage);
            }

            is.setItemMeta(meta);
        }
    }
}
