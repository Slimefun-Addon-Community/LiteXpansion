package dev.j3fftw.litexpansion.catergory;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import javax.annotation.Nonnull;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainCategory extends FlexCategory {

    public static final MainCategory INSTANCE = new MainCategory();

    public MainCategory() {
        super(new NamespacedKey(LiteXpansion.getInstance(), "test_category"),
            new CustomItem(Items.GENERATOR, "&5test")
        );
    }

    private ChestMenu create(Player p) {
        final ChestMenu playerMenu = new ChestMenu(SlimefunPlugin.getLocalization().getMessage(p, "guide.title.main"));
        playerMenu.setEmptySlotsClickable(false);
        return playerMenu;
    }

    private void displayItem(PlayerProfile profile, ItemStack result) {
        final Player p = profile.getPlayer();
        if (p != null) {
            final ChestMenu menu = this.create(p);
            menu.setEmptySlotsClickable(false);

            // Header and back button
            for (int i = 0; i < 9; i++) {
                if (i == 1) {
                    menu.addItem(i, new CustomItem(ChestMenuUtils.getBackButton(p, "",
                        ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(p, "guide.back.guide")))
                    );
                    menu.addMenuClickHandler(i, (pl, s, is, action) -> {
                        open(p, profile, SlimefunGuideLayout.CHEST);
                        return false;
                    });
                } else {
                    menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
            }

            for (int i = 36; i < 45; i++) {
                menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }

            menu.open(p);
        }
    }

    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideLayout layout) {
        return true;
    }

    @Override
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideLayout slimefunGuideLayout) {

        ChestMenu menu = new ChestMenu("&5UU-Matter Recipes");

        // Header
        for (int i = 0; i < 9; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
      
        menu.setEmptySlotsClickable(false);

        menu.addItem(1, new CustomItem(ChestMenuUtils.getBackButton(player, "",
            ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide")))
        );

        menu.addMenuClickHandler(1, (pl, slot, item, action) -> {
            SlimefunPlugin.getRegistry().getGuideLayout(slimefunGuideLayout).openMainMenu(playerProfile, 1);
            return false;
        });

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        playerProfile.getGuideHistory().add(this, 1);

        menu.open(player);
    }
}
