package dev.j3fftw.litexpansion.weapons;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.machine.MetalForge;
import dev.j3fftw.litexpansion.utils.Constants;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class NanoBlade extends SimpleSlimefunItem<ItemUseHandler> implements Rechargeable {

    private static final String LORE_PREFIX = ChatColors.color("&8\u21E8 &e\u26A1 &7");
    private static final Pattern REGEX = Pattern.compile(ChatColors.color("(&c&o)?" + LORE_PREFIX) + "[0-9.]+ / [0-9.]+ J");

    public static final float CAPACITY = 4_000;
    public static final float PER_TICK_REMOVAL = 64;

    public NanoBlade() {
        super(Items.LITEXPANSION, Items.NANO_BLADE, MetalForge.RECIPE_TYPE, new ItemStack[] {
                new ItemStack(Material.GLOWSTONE_DUST), Items.ADVANCED_ALLOY, null,
                new ItemStack(Material.GLOWSTONE_DUST), Items.ADVANCED_ALLOY, null,
                Items.CARBON_PLATE, Items.ENERGY_CRYSTAL, Items.CARBON_PLATE
            }
        );

        setupTicker();
    }

    @Override
    public float getMaxItemCharge(ItemStack item) {
        return CAPACITY;
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return event -> {
            final ItemMeta nanoBladeMeta = event.getItem().getItemMeta();
            final Enchantment enchantment = Enchantment.getByKey(Constants.GLOW_ENCHANT);
            boolean enabled = !nanoBladeMeta.removeEnchant(enchantment);

            if (enabled) {
                nanoBladeMeta.addEnchant(enchantment, 1, false);
                nanoBladeMeta.setDisplayName(ChatColor.DARK_GREEN + "Nano Blade" + ChatColor.GREEN + " (On)");
            } else {
                nanoBladeMeta.setDisplayName(ChatColor.DARK_GREEN + "Nano Blade" + ChatColor.RED + " (Off)");
            }

            PersistentDataAPI.setBoolean(nanoBladeMeta, Constants.NANO_BLADE_ENABLED, enabled);

            event.getItem().setItemMeta(nanoBladeMeta);
        };
    }

    private void setupTicker() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(LiteXpansion.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (ItemStack is : player.getInventory().getContents()) {
                    if (is != null && is.getType() == Material.DIAMOND_SWORD && is.hasItemMeta()) {
                        final ItemMeta meta = is.getItemMeta();
                        final Optional<Boolean> opt = PersistentDataAPI.getOptionalBoolean(meta,
                            Constants.NANO_BLADE_ENABLED);

                        if ((opt.isPresent() && opt.get()) || meta.hasEnchant(Enchantment.getByKey(Constants.GLOW_ENCHANT))) {
//                            final float charge = ChargeUtils.getCharge(meta);
                            final float charge = getCharge(meta);
                            if (charge == 0) continue;

                            final float newCharge = Math.max(charge - PER_TICK_REMOVAL, 0);

//                            ChargeUtils.setCharge(meta, newCharge, CAPACITY);
                            setCharge(meta, newCharge, CAPACITY);

                            if (!opt.isPresent())
                                PersistentDataAPI.setBoolean(meta, Constants.NANO_BLADE_ENABLED, true);
                        }
                    }
                }
            }
        }, 10, 10);
    }

    // This is here until ChargeUtils is in the next RC
    @Deprecated
    private float getCharge(ItemMeta meta) {
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
                    String data = ChatColor.stripColor(PatternUtils.SLASH_SEPARATOR.split(line)[0].replace(LORE_PREFIX, ""));

                    float loreValue = Float.parseFloat(data);
                    container.set(key, PersistentDataType.FLOAT, loreValue);
                    return loreValue;
                }
            }
        }

        return 0;
    }

    @Deprecated
    public static void setCharge(@Nonnull ItemMeta meta, float charge, float capacity) {
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
