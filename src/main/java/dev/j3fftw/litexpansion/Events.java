package dev.j3fftw.litexpansion;

import dev.j3fftw.extrautils.objects.DyeItem;
import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.GlassCutter;
import dev.j3fftw.litexpansion.items.MiningDrill;
import dev.j3fftw.litexpansion.items.PassiveElectricRemoval;
import dev.j3fftw.litexpansion.utils.Utils;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Events implements Listener {

    private final GlassCutter glassCutter = (GlassCutter) Items.GLASS_CUTTER.getItem();
    private final ElectricChestplate electricChestplate = (ElectricChestplate) Items.ELECTRIC_CHESTPLATE.getItem();
    private final FoodSynthesizer foodSynth = (FoodSynthesizer) Items.FOOD_SYNTHESIZER.getItem();

    //TODO Come up with a better way for this.
    private final Set<Material> drillableBlocks = new HashSet<>(Arrays.asList(Material.STONE,
        Material.COBBLESTONE, Material.ANDESITE, Material.DIORITE, Material.GRANITE,
        Material.NETHERRACK, Material.END_STONE)
    );

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        if (e.getItem().hasItemMeta()) {
            final SlimefunItem item = SlimefunItem.getByItem(e.getItem());

            if (item instanceof PassiveElectricRemoval) {
                e.setCancelled(true);
            }
        }
    }

    /**
     * Checks if the player takes damage
     * and has an {@link ElectricChestplate} to be immune to it
     * Power is consumed if both conditions are met.
     *
     * @param e is a provided parameter of the event
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && ((Player) e.getEntity()).getEquipment() != null) {
            final Player p = (Player) e.getEntity();
            final ItemStack chestplate = p.getEquipment().getChestplate();
            if (e.getFinalDamage() > 0
                && chestplate != null
                && electricChestplate.isItem(chestplate)
                && electricChestplate.removeItemCharge(chestplate, Math.max(1, (float) e.getFinalDamage()) * 20)
            ) {
                final ItemMeta meta = chestplate.getItemMeta();
                final float newCharge = PassiveElectricRemoval.getCharge(meta);

                e.setCancelled(true);

                final ComponentBuilder builder = new ComponentBuilder();
                builder
                    .append("Electric Chestplate").color(ChatColor.BLUE)
                    .append(" absorbed damage - Charge left: ").color(ChatColor.GRAY)
                    .append(String.valueOf(electricChestplate.getItemCharge(chestplate))).color(ChatColor.YELLOW)
                    .append(" J");

                if (meta instanceof Damageable) {
                    final double chargePercent = (newCharge / electricChestplate.getMaxItemCharge(chestplate)) * 100;
                    final int percentOfMax = (int) ((chargePercent / 100) * chestplate.getType().getMaxDurability());
                    final int damage = Math.max(1, chestplate.getType().getMaxDurability() - percentOfMax);
                    ((Damageable) meta).setDamage(damage);

                    chestplate.setItemMeta(meta);
                }

                ((Player) e.getEntity()).spigot().sendMessage(ChatMessageType.ACTION_BAR, builder.create());
            }
        }
    }

    /**
     * Checks if the player's hunger level changes
     * and has a {@link FoodSynthesizer} to be immune to it
     * Power is consumed if conditions are met.
     *
     * @param e is a provided parameter of the event
     */
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getFoodLevel() < p.getFoodLevel()) {
            checkAndConsume(p, e);
        }
    }

    /**
     * Checks if the player takes starvation damage
     * and has a {@link FoodSynthesizer} to be immune to it
     * Power is consumed if both conditions are met.
     *
     * @param e is a provided parameter of the event
     */
    @EventHandler
    public void onHungerDamage(EntityDamageEvent e) {
        if (Items.FOOD_SYNTHESIZER == null || Items.FOOD_SYNTHESIZER.getItem().isDisabled()
            || !(e.getEntity() instanceof Player)) {
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            checkAndConsume((Player) e.getEntity(), null);
        }
    }


    /**
     * Prevents animals from being dyed if the item used
     * extends {@link DyeItem}.
     *
     * @param e is a provided parameter of the event
     */
    @EventHandler
    public void onDye(PlayerInteractEntityEvent e) {
        ItemStack item;
        if (e.getHand() == EquipmentSlot.HAND) {
            item = e.getPlayer().getInventory().getItemInMainHand();
        } else {
            item = e.getPlayer().getInventory().getItemInOffHand();
        }

        if (SlimefunItem.getByItem(item) instanceof DyeItem) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMiningDrillUse(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();

        final MiningDrill miningDrill = (MiningDrill) SlimefunItem.getByID(Items.MINING_DRILL.getItemId());

        Validate.notNull(miningDrill, "Can no be null");
        if (miningDrill.isItem(e.getItem()) && drillableBlocks.contains(blockType)
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(),
            blockLocation, ProtectableAction.BREAK_BLOCK)
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);

            if (slimefunItem == null && ((Rechargeable) Objects.requireNonNull(SlimefunItem.getByItem(e.getItem())))
                .removeItemCharge(e.getItem(), 0.5F)) {
                // This allows other plugins to register broken block as player broken
                BlockBreakEvent newEvent = new BlockBreakEvent(block, e.getPlayer());
                Bukkit.getServer().getPluginManager().callEvent(newEvent);
                block.setType(Material.AIR);
                e.getPlayer().playSound(blockLocation, Sound.BLOCK_STONE_BREAK, 1.5F, 1F);

                // This has to be done because the item in the main hand is not a pickaxe
                if (blockType == Material.STONE) {
                    Objects.requireNonNull(blockLocation.getWorld()).dropItem(blockLocation,
                        new ItemStack(Material.COBBLESTONE)
                    );

                } else {
                    Objects.requireNonNull(blockLocation.getWorld()).dropItem(blockLocation,
                        new ItemStack(blockType)
                    );
                }
            }
        }
    }

    //TODO combine onDiamondDrillUse with onMiningDrillUse
    @EventHandler
    public void onDiamondDrillUse(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();

        final MiningDrill diamondDrill = (MiningDrill) SlimefunItem.getByID(Items.DIAMOND_DRILL.getItemId());


        Validate.notNull(diamondDrill, "Can not be null");
        if ((diamondDrill.isItem(e.getItem())
            && (drillableBlocks.contains(blockType)
            || blockType == Material.OBSIDIAN
            || blockType.toString().endsWith("_ORE")))
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(),
            blockLocation, ProtectableAction.BREAK_BLOCK)
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);

            if (slimefunItem == null && ((Rechargeable) Objects.requireNonNull(SlimefunItem.getByItem(e.getItem())))
                .removeItemCharge(e.getItem(), 1.5F)
            ) {
                // This allows other plugins to register broken block as player broken
                BlockBreakEvent newEvent = new BlockBreakEvent(block, e.getPlayer());
                Bukkit.getServer().getPluginManager().callEvent(newEvent);
                block.setType(Material.AIR);
                e.getPlayer().playSound(blockLocation, Sound.BLOCK_STONE_BREAK, 1.5F, 1F);

                // This has to be done because the item in the main hand is not a pickaxe
                if (blockType == Material.STONE) {
                    Objects.requireNonNull(blockLocation.getWorld()).dropItem(blockLocation,
                        new ItemStack(Material.COBBLESTONE)
                    );

                } else {
                    Objects.requireNonNull(blockLocation.getWorld()).dropItem(blockLocation,
                        new ItemStack(blockType)
                    );
                }
            }
        }
    }

    @EventHandler
    public void onDiamondDrillUpgrade(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        // 1.16 inventory, compare strings
        final MiningDrill diamondDrill = (MiningDrill) SlimefunItem.getByID(Items.DIAMOND_DRILL.getItemId());
        Validate.notNull(diamondDrill, "Can not be null");
        if (diamondDrill.isItem(e.getCurrentItem())
            && p.getOpenInventory().getType().toString().equals("SMITHING")
        ) {

            e.setCancelled(true);
            Utils.send(p, "&cYou can not upgrade your Diamond Drill!");
        }
    }

    @EventHandler
    public void onGlassCut(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();
        final ItemStack item = e.getItem();

        Validate.notNull(glassCutter, "Can not be null");
        if ((blockType == Material.GLASS
            || blockType == Material.GLASS_PANE
            || blockType.name().endsWith("_GLASS")
            || blockType.name().endsWith("_GLASS_PANE")
        ) && glassCutter.isItem(item)
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(),
            blockLocation, ProtectableAction.BREAK_BLOCK)
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);

            if (slimefunItem == null && ((Rechargeable) Objects.requireNonNull(SlimefunItem.getByItem(item)))
                .removeItemCharge(item, 0.5F)
            ) {
                Objects.requireNonNull(blockLocation.getWorld()).dropItemNaturally(blockLocation,
                    new ItemStack(blockType)
                );
                block.setType(Material.AIR);
                e.getPlayer().playSound(block.getLocation(), Sound.BLOCK_GLASS_HIT,
                    SoundCategory.BLOCKS, 1.5F, 1F
                );
            }

        }
    }

    /*
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();
        Block block = e.getBlock();
        Wrench wrench = (Wrench) Items.WRENCH.getItem();

        if (Wrench.machineBreakRequiresWrench.getValue()
            && !wrench.isItem(p.getInventory().getItemInMainHand())
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(),
            block.getLocation(), ProtectableAction.BREAK_BLOCK)
        ) {

            SlimefunItem slimefunBlock = BlockStorage.check(block);

            if (slimefunBlock instanceof EnergyNetComponent) {
                e.setCancelled(true);
                wrenchBlock(p, block, true, false);
                Utils.send(p, "&cYou need a Wrench to break Slimefun machines!");
                Utils.send(p, "&c(Slimefun Guide > LiteXpansion > Wrench)");
            }
        }
    }

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onWrenchUse(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Wrench wrench = (Wrench) Items.WRENCH.getItem();
        ItemStack playerWrench = p.getInventory().getItemInMainHand();
        final Block block = e.getClickedBlock();

        if (block == null) return;

        if (e.getHand() == EquipmentSlot.HAND && wrench.isItem(playerWrench)
            && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(),
            block.getLocation(), ProtectableAction.BREAK_BLOCK)) {

            SlimefunItem sfBlock = BlockStorage.check(block);

            if (sfBlock instanceof EnergyNetComponent) {

                if (Wrench.machineBreakRequiresWrench.getValue()) {

                    double random = Math.random();
                    wrenchBlock(p, block, random <= Wrench.wrenchFailChance.getValue(), true);

                } else {
                    wrenchBlock(p, block, false, true);
                }

                wrench.damageItem(p, playerWrench);

            } else if (sfBlock != null
                && (sfBlock.getID().startsWith("CARGO_NODE")
                || sfBlock instanceof TrashCan)) {

                wrenchBlock(p, block, false, true);
                wrench.damageItem(p, playerWrench);

            } else {
                Utils.send(p, "&cYou can not use the wrench on this block!");
            }
        }
    }

    public static void wrenchBlock(Player p, Block b, boolean fail, boolean byInteract) {

        if (fail) {

            World blockWorld = b.getWorld();
            Location blockLocation = b.getLocation();
            SlimefunItem slimefunBlock = BlockStorage.check(b);
            BlockMenu blockInventory = BlockStorage.getInventory(b);

            if (BlockStorage.hasInventory(b)) {
                int[] inputSlots = ((InventoryBlock) slimefunBlock).getInputSlots();
                for (int slot : inputSlots) {

                    if (blockInventory.getItemInSlot(slot) != null) {
                        blockWorld.dropItemNaturally(blockLocation, blockInventory.getItemInSlot(slot));
                    }
                }
                int[] outputSlots = ((InventoryBlock) slimefunBlock).getOutputSlots();
                for (int slot : outputSlots) {
                    if (blockInventory.getItemInSlot(slot) != null) {
                        blockWorld.dropItemNaturally(blockLocation, blockInventory.getItemInSlot(slot));
                    }
                }
            }

            BlockStorage.clearBlockInfo(b);
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), Items.MACHINE_BLOCK.clone());

            if (byInteract) {
                Utils.send(p, "&cOh no! Your wrench failed!");
            }

        } else {
            BlockBreakEvent breakEvent = new BlockBreakEvent(b, p);
            Bukkit.getServer().getPluginManager().callEvent(breakEvent);
        }
    }

     */

    /**
     * Checks if the player has a {@link FoodSynthesizer}
     * in their inventory. If it does, power is consumed
     * and the player's hunger is reset to max
     *
     * @param p is the player who's hunger is being modified
     * @param e is the FoodLevelChangeEvent linked to the player
     */
    public void checkAndConsume(@Nonnull Player p, @Nullable FoodLevelChangeEvent e) {
        for (ItemStack item : p.getInventory().getContents()) {
            Validate.notNull(foodSynth, "Can not be null");
            if (foodSynth.isItem(item) && foodSynth.removeItemCharge(item, 5F)) {
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.5F, 1F);
                p.setFoodLevel(20);
                p.setSaturation(5);
                if (e != null) {
                    e.setFoodLevel(20);
                }
                break;
            }
        }
    }
}
