package dev.j3fftw.litexpansion.resources;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.biomes.BiomeMap;
import java.util.logging.Level;
import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

public class ThoriumResource implements GEOResource {

    private final NamespacedKey key = new NamespacedKey(LiteXpansion.getInstance(), "thorium");

    private BiomeMap<Integer> map;

    public ThoriumResource () {
        final LiteXpansion instance = LiteXpansion.getInstance();

        try {
            if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_18)) {
                map = BiomeMap.getIntMapFromResource(key, instance, "/biome-maps/thorium_v1.18.json");
            } else {
                map = BiomeMap.getIntMapFromResource(key, instance, "/biome-maps/thorium_v1.14.json");
            }
        } catch (Exception e) {
            instance.getLogger().log(Level.SEVERE, "Failed to load biome map!", e);
        }
    }

    @Override
    public int getDefaultSupply(@Nonnull World.Environment environment, Biome biome) {
        return map.getOrDefault(biome, 1);
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public int getMaxDeviation() {
        return 1;
    }

    @Nonnull
    @Override
    public String getName() {
        return "Thorium";
    }

    @Nonnull
    @Override
    public ItemStack getItem() {
        return Items.THORIUM.clone();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }
}
