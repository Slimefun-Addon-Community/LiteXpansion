package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.GlassCutter;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.weapons.NanoBlade;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Events implements Listener {

    private final NanoBlade nanoBlade = (NanoBlade) Items.NANO_BLADE.getItem();
    private final GlassCutter glassCutter = (GlassCutter) Items.GLASS_CUTTER.getItem();
    private final ElectricChestplate electricChestplate = (ElectricChestplate) Items.ELECTRIC_CHESTPLATE.getItem();
    private final FoodSynthesizer foodSynth = (FoodSynthesizer) Items.FOOD_SYNTHESIZER.getItem();

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getFoodLevel() < p.getFoodLevel()) {
            checkAndConsume(p, e);
        }
    }

    @EventHandler
    public void onPlayerDamageDeal(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if (nanoBlade.isItem(itemInHand)
                && itemInHand.containsEnchantment(Enchantment.getByKey(Constants.GLOW_ENCHANT))
                && nanoBlade.removeItemCharge(itemInHand, 10)
            ) {
                e.setDamage(e.getDamage() * 1.75);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && ((Player) e.getEntity()).getEquipment() != null) {
            Player p = (Player) e.getEntity();
            ItemStack chestplate = p.getEquipment().getChestplate();
            if (chestplate != null
                && electricChestplate.isItem(chestplate)
                && electricChestplate.removeItemCharge(chestplate, (float) (e.getDamage() / 1.75))
            ) {
                e.setCancelled(true);
            }
        }
    }

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

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onGlassCut(PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) return;

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();
        final ItemStack item = e.getItem();

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

            if (slimefunItem == null && ((Rechargeable) SlimefunItem.getByItem(item))
                .removeItemCharge(item, 0.5F)) {
                blockLocation.getWorld().dropItemNaturally(blockLocation,
                    new ItemStack(blockType));
                block.setType(Material.AIR);
                e.getPlayer().playSound(block.getLocation(), Sound.BLOCK_GLASS_HIT,
                    SoundCategory.BLOCKS, 1.5F, 1F);
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

    public void checkAndConsume(@Nonnull Player p, @Nullable FoodLevelChangeEvent e) {
        for (ItemStack item : p.getInventory().getContents()) {
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
