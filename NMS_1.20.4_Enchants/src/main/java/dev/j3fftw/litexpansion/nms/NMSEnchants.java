package dev.j3fftw.litexpansion.nms;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R3.enchantments.CraftEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;

import java.util.IdentityHashMap;

public class NMSEnchants {

    public static Enchantment GLOW = null;

    public NMSEnchants(Plugin plugin){
        Ref.init(Ref.getClass("net.md_5.bungee.api.ChatColor") != null ? Ref.getClass("net.kyori.adventure.Adventure") != null ? Ref.ServerType.PAPER : Ref.ServerType.SPIGOT : Ref.ServerType.BUKKIT,
                Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
        unfreezeRegistry();
        NamespacedKey GLOW_ENCHANT = new NamespacedKey(plugin,"glow_enchant");
        XEnchantment xEnchantment = new XEnchantment(new String[]{
                "ADVANCED_CIRCUIT", "NANO_BLADE", "GLASS_CUTTER", "LAPOTRON_CRYSTAL",
                "ADVANCEDLX_SOLAR_HELMET", "HYBRID_SOLAR_HELMET", "ULTIMATE_SOLAR_HELMET",
                "DIAMOND_DRILL"
        });
        Registry.register(BuiltInRegistries.ENCHANTMENT,GLOW_ENCHANT.getKey(), xEnchantment);

        freezeRegistry();
        GLOW = CraftEnchantment.minecraftToBukkit(xEnchantment);
    }

    private void unfreezeRegistry(){
        Ref.set(BuiltInRegistries.ENCHANTMENT,"l",false);
        Ref.set(BuiltInRegistries.ENCHANTMENT,"m",new IdentityHashMap<>());
    }

    private void freezeRegistry(){
        BuiltInRegistries.ENCHANTMENT.freeze();
    }


}
