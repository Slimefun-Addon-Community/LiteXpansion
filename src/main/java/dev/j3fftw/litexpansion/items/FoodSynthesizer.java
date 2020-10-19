package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FoodSynthesizer extends SlimefunItem implements Rechargeable, NotPlaceable {

    public FoodSynthesizer() {
        super(Items.LITEXPANSION, Items.FOOD_SYNTHESIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.PLASTIC_SHEET, new ItemStack(Material.COOKED_BEEF), SlimefunItems.PLASTIC_SHEET,
            new ItemStack(Material.APPLE), SlimefunItems.COOLER, new ItemStack(Material.APPLE),
            SlimefunItems.PLASTIC_SHEET, new ItemStack(Material.COOKED_BEEF), SlimefunItems.PLASTIC_SHEET
        });
    }

    @Override
    public void preRegister() {
        addItemHandler((ItemUseHandler) PlayerRightClickEvent::cancel);
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 100;
    }
}
