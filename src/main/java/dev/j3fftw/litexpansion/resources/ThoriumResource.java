package dev.j3fftw.litexpansion.resources;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

public class ThoriumResource implements GEOResource {

    private final NamespacedKey key = new NamespacedKey(LiteXpansion.getInstance(), "thorium");
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public int getDefaultSupply(World.Environment environment, Biome biome) {
        switch (biome) {
            case MOUNTAINS:
            case GRAVELLY_MOUNTAINS:
            case WOODED_MOUNTAINS:
            case SNOWY_MOUNTAINS:
            case MODIFIED_GRAVELLY_MOUNTAINS:
                return random.nextInt(2) + 1;
            default:
                return 1;
        }
    }

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
