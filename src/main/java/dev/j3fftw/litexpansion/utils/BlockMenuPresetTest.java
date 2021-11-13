package dev.j3fftw.litexpansion.utils;

import dev.j3fftw.extrautils.utils.Utils;
import dev.j3fftw.litexpansion.machine.UUCrafter;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//todo make it generic then push to extrautils
public class BlockMenuPresetTest extends BlockMenuPreset {

    private UUCrafter autoCrafter;

    public BlockMenuPresetTest(@Nonnull String id, @Nonnull String title, @Nonnull UUCrafter autoCrafter) {
        super(id, title);
        this.autoCrafter = autoCrafter;
    }


    @Override
    public void init() {
        List<Integer> crafting = Arrays.stream(UUCrafter.CRAFTING_SLOTS).boxed().collect(Collectors.toList());

        for (int i = 0; i < 54; i++) {
            if (i == UUCrafter.INPUT_SLOT
                || i == UUCrafter.OUTPUT_SLOT
                || i == UUCrafter.START_STOP
                || crafting.contains(i)
            ) continue;

            addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        Utils.putOutputSlot(this, UUCrafter.OUTPUT_SLOT);

        addItem(UUCrafter.START_STOP,
            new CustomItemStack(
                Material.RED_STAINED_GLASS_PANE,
                "&7Click to start"
            )
        );
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) {
            return autoCrafter.getInputSlots();
        } else {
            return autoCrafter.getOutputSlots();
        }
    }

    @Override
    public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
        return player.hasPermission("slimefun.inventory.bypass")
            || (Slimefun.getProtectionManager().hasPermission(player, block.getLocation(),
            Interaction.INTERACT_BLOCK) && SlimefunUtils.canPlayerUseItem(player, autoCrafter.getItem(), false)
        );
    }

    @Override
    public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block block) {
        autoCrafter.onNewInstance(menu, block);
    }
}
