package dev.j3fftw.litexpansion.service;

import dev.j3fftw.litexpansion.LiteXpansion;
import dev.j3fftw.litexpansion.utils.Reflections;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MetricsService {

    private final Map<UUID, Field> rawStorageMethods = new HashMap<>();

    public void setup(@Nonnull Metrics metrics) {
        // We need to make sure the worlds are loaded.
        LiteXpansion.getInstance().getServer().getScheduler().runTask(LiteXpansion.getInstance(), () -> {
            for (World world : Bukkit.getWorlds()) {
                final BlockStorage storage = BlockStorage.getStorage(world);
                if (storage != null) {
                    final Field field = Reflections.getRawField(BlockStorage.class, "storage");

                    rawStorageMethods.put(world.getUID(), field);
                }
            }
        });

        // Setup charts
        setupCharts(metrics);
    }

    private void setupCharts(@Nonnull Metrics metrics) {
        metrics.addCustomChart(new AdvancedPie("blocks_placed", () -> {
            final Map<String, Integer> data = new HashMap<>();
            for (World world : Bukkit.getWorlds()) {
                final Map<Location, Config> rawStorage = getStorageForWorld(world);
                if (rawStorage == null) {
                    continue;
                }

                for (Map.Entry<Location, Config> entry : rawStorage.entrySet()) {
                    final SlimefunItem item = SlimefunItem.getByID(entry.getValue().getString("id"));
                    if (item == null || !(item.getAddon() instanceof LiteXpansion)) {
                        continue;
                    }

                    data.merge(item.getId(), 1, Integer::sum);
                }
            }
            return data;
        }));

        metrics.addCustomChart(new SimplePie("nerf_addons", () ->
            LiteXpansion.getInstance().getConfig().getBoolean("options.nerf-other-addons", true) ? "true" : "false"));
    }

    @Nullable
    private Map<Location, Config> getStorageForWorld(@Nonnull World world) {
        final Field f = rawStorageMethods.get(world.getUID());
        final BlockStorage storage = BlockStorage.getStorage(world);

        if (f != null && storage != null) {
            return Reflections.getField(storage, f);
        } else {
            return null;
        }
    }
}
