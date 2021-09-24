package dev.j3fftw.litexpansion.uumatter;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.autocrafters.AbstractAutoCrafter;
import io.github.thebusybiscuit.slimefun4.implementation.items.autocrafters.VanillaAutoCrafter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.gadgets.Multimeter;
import org.bukkit.GameRule;
import org.bukkit.Keyed;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class UUCrafterListener implements Listener {


    @ParametersAreNonnullByDefault
    public UUCrafterListener(Slimefun plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerRightClickEvent e) {
        Optional<Block> clickedBlock = e.getClickedBlock();

        // We want to make sure we used the main hand, the interaction was not cancelled and a Block was clicked.
        if (e.getHand() == EquipmentSlot.HAND
            && e.useBlock() != Event.Result.DENY
            && clickedBlock.isPresent()
        ) {
            Optional<SlimefunItem> slimefunBlock = e.getSlimefunBlock();

            // Check if the clicked Block is a Slimefun block.
            if (!slimefunBlock.isPresent()) {
                return;
            }

            SlimefunItem block = slimefunBlock.get();

            if (block instanceof AbstractAutoCrafter) {
                Optional<SlimefunItem> slimefunItem = e.getSlimefunItem();

                if (!e.getPlayer().isSneaking() && slimefunItem.get() instanceof Multimeter) {
                    // Allow Multimeters to pass through and do their job
                    return;
                }

                // Prevent blocks from being placed, food from being eaten, etc...
                e.cancel();

                // Check for the "doLimitedCrafting" gamerule when using a Vanilla Auto-Crafter
                if (block instanceof VanillaAutoCrafter) {
                    boolean doLimitedCrafting = e.getPlayer().getWorld().getGameRuleValue(GameRule.DO_LIMITED_CRAFTING);

                    // Check if the recipe of the item is disabled.
                    if (doLimitedCrafting && !hasUnlockedRecipe(e.getPlayer(), e.getItem())) {
                        Slimefun.getLocalization().sendMessage(e.getPlayer(),
                            "messages.auto-crafting.recipe-unavailable");
                        return;
                    }
                }

                // Fixes 2896 - Forward the interaction before items get handled.
                AbstractAutoCrafter crafter = (AbstractAutoCrafter) block;

                try {
                    crafter.onRightClick(clickedBlock.get(), e.getPlayer());
                } catch (Exception | LinkageError x) {
                    crafter.error("Something went wrong while right-clicking an Auto-Crafter", x);
                }
            }
        }
    }

    @ParametersAreNonnullByDefault
    private boolean hasUnlockedRecipe(Player p, ItemStack item) {
        for (Recipe recipe : Slimefun.getMinecraftRecipeService().getRecipesFor(item)) {
            if (recipe instanceof Keyed && !p.hasDiscoveredRecipe(((Keyed) recipe).getKey())) {
                return false;
            }
        }

        return true;
    }
}