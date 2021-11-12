package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;
import dev.j3fftw.extrautils.utils.Utils;
import dev.j3fftw.litexpansion.machine.api.PoweredMachine;
import dev.j3fftw.litexpansion.uumatter.UUMatter;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public class AutoCrafter extends SlimefunItem implements InventoryBlock, EnergyNetComponent, PoweredMachine {

    public static final int ENERGY_CONSUMPTION = 200;
    public static final int CAPACITY = ENERGY_CONSUMPTION * 3;
    private static final int[] INPUT_SLOTS = {10, 19, 28, 37};
    private static final int[] OUTPUT_SLOTS = {16, 25, 34, 43};
    private static final int[] CRAFTING_SLOTS = {12, 13, 14, 21, 22, 23, 30, 31, 32};
    private static final int PLAY_SLOT = 40;
    public static final CustomItemStack STOP = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "STOP");
    public static final CustomItemStack START = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "START");

    public AutoCrafter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        setup();
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                AutoCrafter.this.tick(b);
            }

            public boolean isSynchronized() {
                return false;
            }
        });
    }

    private void tick(Block block) {
        @Nullable final BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu == null) {
            return;
        }

        Material material = blockMenu.getItemInSlot(PLAY_SLOT).getType();
        if (material == Material.RED_STAINED_GLASS_PANE) {
            return;
        }
        ItemStack[] itemStacks = new ItemStack[9];
        int i = 0;

        for (int slot : CRAFTING_SLOTS) {
            itemStacks[i] = blockMenu.getItemInSlot(slot);
            i++;
        }

        //todo not done ignore plox
        UUMatter.INSTANCE.getRecipes().entrySet().stream();
    }

    public void setup() {
        createPreset(this, "&8Recycler", blockMenuPreset -> {
            for (int i = 0; i < 54; i++) {
                if (Arrays.asList(INPUT_SLOTS).contains(i)
                    || Arrays.asList(OUTPUT_SLOTS).contains(i)
                    || Arrays.asList(CRAFTING_SLOTS).contains(i)
                    || i == PLAY_SLOT
                ) continue;

                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            for (int outputSlot : OUTPUT_SLOTS) {
                Utils.putOutputSlot(blockMenuPreset, outputSlot);
            }

            blockMenuPreset.addItem(PLAY_SLOT,
                new CustomItemStack(
                    Material.ORANGE_STAINED_GLASS_PANE,
                    "&7Click to start"
                ), (player, i, itemStack, clickAction) -> {
                    Material material = itemStack.getType();
                    if (material == Material.RED_STAINED_GLASS_PANE) {
                        blockMenuPreset.replaceExistingItem(i, START);
                    } else {
                        blockMenuPreset.replaceExistingItem(i, STOP);
                    }
                    return false;
                }
            );
        });
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public int getDefaultEnergyConsumption() {
        return ENERGY_CONSUMPTION;
    }
}
