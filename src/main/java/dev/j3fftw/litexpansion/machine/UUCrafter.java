package dev.j3fftw.litexpansion.machine;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;
import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.machine.api.PoweredMachine;
import dev.j3fftw.litexpansion.utils.BlockMenuPresetTest;
import dev.j3fftw.litexpansion.uumatter.UUMatter;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UUCrafter extends SlimefunItem implements InventoryBlock, EnergyNetComponent, PoweredMachine {

    public static final int ENERGY_CONSUMPTION = 200;
    public static final int CAPACITY = ENERGY_CONSUMPTION * 3;
    public static final int INPUT_SLOT = 19;
    public static final int OUTPUT_SLOT = 25;
    public static final int[] CRAFTING_SLOTS = {12, 13, 14, 21, 22, 23, 30, 31, 32};
    public static final int START_STOP = 40;
    public static final CustomItemStack RUNNING = new CustomItemStack(
        Material.GREEN_STAINED_GLASS_PANE, ChatColor.GRAY + "Click to stop"
    );

    public static final CustomItemStack NOT_RUNNING = new CustomItemStack(
        Material.RED_STAINED_GLASS_PANE, ChatColor.GRAY + "Click to start"
    );

    protected static final Map<Location, Boolean> whatIsRunning = new HashMap<>();

    public UUCrafter() {
        super(Items.LITEXPANSION, Items.UU_CRAFTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.UU_MATTER, new ItemStack(Material.CRAFTING_TABLE), Items.UU_MATTER,
            Items.CARGO_CONFIGURATOR, Items.ADVANCED_MACHINE_BLOCK, Items.GLASS_CUTTER,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        });
        setup();
        this.addItemHandler(
            new BlockBreakHandler(false, false) {
                @Override
                public void onPlayerBreak(BlockBreakEvent event, ItemStack item, List<ItemStack> drops) {
                    BlockMenu blockMenu = BlockStorage.getInventory(event.getBlock());
                    if (blockMenu != null) {
                        blockMenu.dropItems(blockMenu.getLocation(), INPUT_SLOT, OUTPUT_SLOT);
                        blockMenu.dropItems(blockMenu.getLocation(), CRAFTING_SLOTS);
                    }
                }
            }
        );
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                UUCrafter.this.tick(b);
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

        if (!whatIsRunning.get(block.getLocation())) {
            return;
        }

        ItemStack[] itemStacks = new ItemStack[9];
        int i = 0;

        for (int slot : CRAFTING_SLOTS) {
            itemStacks[i] = blockMenu.getItemInSlot(slot);
            i++;
        }

        for (Map.Entry<ItemStack, ItemStack[]> entry : UUMatter.INSTANCE.getRecipes().entrySet()) {
            if (Arrays.equals(entry.getValue(), itemStacks)) {
                int amount = 9;
                for (ItemStack recipeStack : entry.getValue()) {
                    if (recipeStack == null) amount--;
                }

                ItemStack output = entry.getKey().clone();
                final ItemStack input = blockMenu.getItemInSlot(INPUT_SLOT);

                if (input != null
                    && input.getAmount() >= amount
                    && blockMenu.fits(output, OUTPUT_SLOT)
                ) {
                    blockMenu.pushItem(output, OUTPUT_SLOT);
                    blockMenu.consumeItem(INPUT_SLOT, amount);
                }
                break;
            }
        }
    }

    public void setup() {
        new BlockMenuPresetTest(this.getId(), "&8UU Crafter", this);
    }

    public void onNewInstance(BlockMenu menu, Block block) {
        String isRunningString = BlockStorage.getLocationInfo(block.getLocation(), "RUNNING");
        boolean isRunning = false;
        if (isRunningString != null) {
            isRunning = Boolean.parseBoolean(isRunningString);
        }

        whatIsRunning.put(block.getLocation(), isRunning);

        menu.replaceExistingItem(START_STOP, isRunning ? RUNNING : NOT_RUNNING);
        menu.addMenuClickHandler(START_STOP, (p, slot, item, action) -> {
            toggleRunning(menu, block);
            return false;
        });
    }

    public void toggleRunning(BlockMenu blockMenu, Block block) {
        boolean setTo = !whatIsRunning.get(block.getLocation());
        BlockStorage.addBlockInfo(block, "RUNNING", String.valueOf(setTo));
        whatIsRunning.put(block.getLocation(), setTo);
        ItemStack itemStack = setTo ? RUNNING : NOT_RUNNING;
        blockMenu.replaceExistingItem(START_STOP, itemStack);
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
    public int getDefaultEnergyConsumption() {
        return ENERGY_CONSUMPTION;
    }

    @Override
    public int[] getInputSlots() {
        return new int[] {
            INPUT_SLOT
        };
    }

    @Override
    public int[] getOutputSlots() {
        return new int[] {
            OUTPUT_SLOT
        };
    }

}
