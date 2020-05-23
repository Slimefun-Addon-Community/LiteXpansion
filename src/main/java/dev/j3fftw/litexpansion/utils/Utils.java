package dev.j3fftw.litexpansion.utils;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class Utils {

    private Utils() {}

    public static int euToJ(int i) {
        return i * 10;
    }

    public static void putOutputSlot(BlockMenuPreset preset, int slot) {
        preset.addItem(slot, null, new ChestMenu.AdvancedMenuClickHandler() {

            @Override
            public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                return false;
            }

            @Override
            public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                return cursor == null || cursor.getType() == Material.AIR;
            }
        });
    }
}
