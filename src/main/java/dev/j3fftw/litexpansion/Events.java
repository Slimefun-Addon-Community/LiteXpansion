package dev.j3fftw.litexpansion;

import com.google.common.base.Preconditions;
import dev.j3fftw.extrautils.objects.DyeItem;
import dev.j3fftw.litexpansion.armor.ElectricChestplate;
import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.GlassCutter;
import dev.j3fftw.litexpansion.items.MiningDrill;
import dev.j3fftw.litexpansion.items.PassiveElectricRemoval;
import dev.j3fftw.litexpansion.utils.Utils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChargeUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
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
import java.util.concurrent.ThreadLocalRandom;

public class Events implements Listener {

    private final GlassCutter glassCutter = (GlassCutter) Items.GLASS_CUTTER.getItem();
    private final ElectricChestplate electricChestplate = (ElectricChestplate) Items.ELECTRIC_CHESTPLATE.getItem();
    private final FoodSynthesizer foodSynth = (FoodSynthesizer) Items.FOOD_SYNTHESIZER.getItem();

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        if (e.getItem().hasItemMeta()) {
            final SlimefunItem item = SlimefunItem.getByItem(e.getItem());

            if (item instanceof PassiveElectricRemoval || item instanceof ElectricChestplate) {
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
        if (e.getEntity() instanceof Player player && player.getEquipment() != null) {
            final ItemStack chestplate = player.getEquipment().getChestplate();
            if (e.getFinalDamage() > 0
                && chestplate != null
                && electricChestplate.isItem(chestplate)
                && electricChestplate.removeItemCharge(chestplate, Math.max(1, (float) e.getFinalDamage()) * 20)
            ) {
                final ItemMeta meta = chestplate.getItemMeta();
                final float newCharge = ChargeUtils.getCharge(meta);

                e.setCancelled(true);

                final ComponentBuilder builder = new ComponentBuilder();
                builder
                    .append("Electric Chestplate").color(ChatColor.BLUE)
                    .append(" absorbed damage - Charge left: ").color(ChatColor.GRAY)
                    .append(String.valueOf(electricChestplate.getItemCharge(chestplate))).color(ChatColor.YELLOW)
                    .append(" J");

                if (meta instanceof Damageable damageable) {
                    final double chargePercent = (newCharge / electricChestplate.getMaxItemCharge(chestplate)) * 100;
                    final int percentOfMax = (int) ((chargePercent / 100) * chestplate.getType().getMaxDurability());
                    final int damage = Math.max(1, chestplate.getType().getMaxDurability() - percentOfMax);
                    damageable.setDamage(damage);
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
        if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION
            && e.getEntity() instanceof Player player
            && Items.FOOD_SYNTHESIZER != null
            && !Items.FOOD_SYNTHESIZER.getItem().isDisabled()
        ) {
            checkAndConsume(player, null);
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
    public void onDrillUse(PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        final Material blockType = block.getType();
        final Location blockLocation = block.getLocation();
        final ItemStack hand = event.getItem();

        final MiningDrill miningDrill = (MiningDrill) SlimefunItem.getById(Items.MINING_DRILL.getItemId());
        if (miningDrill.isItem(hand)) {

            if (!check(miningDrill, event, blockLocation)) {
                return;
            }

            if (!SlimefunTag.STONE_VARIANTS.isTagged(blockType)) {
                return;
            }

            drillUse(0.5f, block, blockType, blockLocation, event);
        }

        final MiningDrill diamondDrill = (MiningDrill) SlimefunItem.getById(Items.DIAMOND_DRILL.getItemId());
        if (diamondDrill != null && diamondDrill.isItem(hand)) {

            if (!check(diamondDrill, event, blockLocation)) {
                return;
            }

            if (!SlimefunTag.MINEABLE_PICKAXE.isTagged(blockType)) {
                return;
            }

            drillUse(1.5f, block, blockType, blockLocation, event);
        }
    }


    public boolean check(MiningDrill miningDrill, PlayerInteractEvent event, Location blockLocation) {
        return miningDrill.isItem(event.getItem())
            && !miningDrill.isDisabled()
            && Slimefun.getProtectionManager().hasPermission(event.getPlayer(),
            blockLocation, Interaction.BREAK_BLOCK);
    }

    public void drillUse(float charge, Block block, Material blockType,
                         Location blockLocation, PlayerInteractEvent event
    ) {
        event.setCancelled(true);

        final SlimefunItem slimefunItem = BlockStorage.check(block);

        if (slimefunItem != null) {
            return;
        }

        final Rechargeable item = (Rechargeable) SlimefunItem.getByItem(event.getItem());

        if (item == null) {
            return;
        }

        if (!item.removeItemCharge(event.getItem(), charge)) {
            return;
        }

        BlockBreakEvent newEvent = new BlockBreakEvent(block, event.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        block.breakNaturally();
    }


    @EventHandler
    public void onDiamondDrillUpgrade(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        // 1.16 inventory, compare strings
        final MiningDrill diamondDrill = (MiningDrill) SlimefunItem.getById(Items.DIAMOND_DRILL.getItemId());
        Preconditions.checkNotNull(diamondDrill, "Can not be null");
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

        Preconditions.checkNotNull(glassCutter, "Can not be null");
        if (SlimefunTag.GLASS.isTagged(blockType)
            && glassCutter.isItem(item)
            && !glassCutter.isDisabled()
            && Slimefun.getProtectionManager().hasPermission(e.getPlayer(),
            blockLocation, Interaction.BREAK_BLOCK)
        ) {
            e.setCancelled(true);

            final SlimefunItem slimefunItem = BlockStorage.check(block);

            if (slimefunItem == null && ((Rechargeable) SlimefunItem.getByItem(item)).removeItemCharge(item, 0.5F)
            ) {
                blockLocation.getWorld().dropItemNaturally(blockLocation,
                    new ItemStack(blockType)
                );
                block.setType(Material.AIR);
                e.getPlayer().playSound(block.getLocation(), Sound.BLOCK_GLASS_HIT,
                    SoundCategory.BLOCKS, 1.5F, 1F
                );
            }

        }
    }

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
            Preconditions.checkNotNull(foodSynth, "Can not be null");
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

    /**
     * Rest In Peace Kleintje aka Chunker 9/16/2022
     * You will be missed
     * <p>
     * This event is dedicated to my cat Kleintje also
     * known as Chunker
     */
    @EventHandler
    public void onCatSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Cat cat) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, 100_000);
            if (cat.getCatType() == Cat.Type.RED && randomNumber == 91622) {
                OfflinePlayer player = Bukkit.getOfflinePlayer("22815ad5-2a54-44c0-8f83-f65cfe5310f8"); // _lagpc_
                entity.setCustomName("Kleintje");
                ((Cat) entity).setOwner(player);
            }
        }
    }
}

