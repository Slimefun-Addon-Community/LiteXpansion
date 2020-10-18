package dev.j3fftw.litexpansion.items;


import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * The {@link Wrench} is a {@link SimpleSlimefunItem} that breaks
 * machines, cargo nodes, and capacitors instantly. Server owners
 * can manually configure if a wrench is required to break machines
 * and the chance that it fails.
 *
 * @author FluffyBear
 */
public class Wrench extends SimpleSlimefunItem<ItemUseHandler> implements DamageableItem {

    public static final ItemSetting<Boolean> machineBreakRequiresWrench = new ItemSetting<>("machine-break-requires" +
        "-wrench", false);
    public static final ItemSetting<Double> wrenchFailChance = new ItemSetting<>("wrench-failure-chance", 0.0);

    public Wrench() {
        super(Items.LITEXPANSION, Items.WRENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.COPPER_INGOT, null, SlimefunItems.COPPER_INGOT,
            null, SlimefunItems.COPPER_INGOT, null,
            null, SlimefunItems.COPPER_INGOT, null
        });

        addItemSetting(machineBreakRequiresWrench, wrenchFailChance);
    }

    @Nonnull
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    public static String getFailureChance() {
        if (!Wrench.machineBreakRequiresWrench.getValue()) {
            return "0";
        } else {
            return String.valueOf((Wrench.wrenchFailChance.getValue() * 100));
        }
    }
}
