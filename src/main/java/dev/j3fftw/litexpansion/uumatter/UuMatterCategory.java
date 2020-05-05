package dev.j3fftw.litexpansion.uumatter;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class UuMatterCategory extends FlexCategory {

    public static final UuMatterCategory INSTANCE = new UuMatterCategory();

    private final int[] recipeSlots = new int[] {12, 13, 14, 21, 22, 23, 30, 31, 32};
    private ChestMenu menu;

    private UuMatterCategory() {
        super(new NamespacedKey(LiteXpansion.getInstance(), "uumatter_category"),
            new CustomItem(Items.UU_MATTER, "&5UU-Matter Recipes")
        );

        setupInv();
    }

    private void setupInv() {
        menu = new ChestMenu("&5UU-Matter Recipes");

        // Header
        for (int i = 0; i < 9; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
    }

    private ChestMenu create(Player p) {
        final ChestMenu playerMenu = new ChestMenu(SlimefunPlugin.getLocal().getMessage(p, "guide.title.main"));
        playerMenu.setEmptySlotsClickable(false);
        return playerMenu;
    }

    private void displayItem(PlayerProfile profile, ItemStack result) {
        final Player p = profile.getPlayer();
        if (p != null) {
            final ChestMenu menu = this.create(p);

            // Header and back button
            for (int i = 0; i < 9; i++) {
                if (i == 1) {
                    menu.addItem(i, new CustomItem(ChestMenuUtils.getBackButton(p, "",
                        ChatColor.GRAY + SlimefunPlugin.getLocal().getMessage(p, "guide.back.guide")))
                    );
                    menu.addMenuClickHandler(i, (pl, s, is, action) -> {
                        open(p, profile, SlimefunGuideLayout.CHEST);
                        return false;
                    });
                } else
                    menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }

            ItemStack[] recipe = UUMatter.INSTANCE.getRecipes().get(result);
            if (recipe == null) return;

            this.displayItem(menu, p, result, recipe);

            // Panda wants a footer
            for (int i = 36; i < 45; i++) {
                menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }

            menu.open(p);
        }
    }

    private void displayItem(ChestMenu menu, Player p, ItemStack output, ItemStack[] recipe) {
        ChestMenu.MenuClickHandler clickHandler = (pl, slot, itemstack, action) -> false;

        for (int i = 0; i < 9; ++i) {
            menu.addItem(recipeSlots[i], recipe[i], clickHandler);
        }

        menu.addItem(19, RecipeType.ENHANCED_CRAFTING_TABLE.getItem(p), ChestMenuUtils.getEmptyClickHandler());
        menu.addItem(25, output, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public boolean isVisible(Player player, PlayerProfile playerProfile, SlimefunGuideLayout slimefunGuideLayout) {
        return true;
    }

    @Override
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideLayout slimefunGuideLayout) {
        menu.addItem(1, ChestMenuUtils.getBackButton(player));
        menu.addMenuClickHandler(1, (pl, slot, item, action) -> {
            SlimefunPlugin.getRegistry().getGuideLayout(slimefunGuideLayout).openMainMenu(playerProfile, 1);
            return false;
        });

        // Other items
        int i = 9;
        for (ItemStack item : UUMatter.INSTANCE.getRecipes().keySet()) {
            menu.addItem(i++, item, (v0, v1, v2, v3) -> {
                displayItem(playerProfile, item);
                return false;
            });
        }

        menu.open(player);
    }
}
