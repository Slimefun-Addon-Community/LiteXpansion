package dev.j3fftw.litexpansion.commands;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

public class StressTestCommand implements CommandExecutor {

    private static StressTestCommand instance;

    private final Set<Run> runs = new HashSet<>();

    private final int ITERATIONS = 1_000;

    public StressTestCommand() {
        instance = this;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Reee I'll add dis soon");
            return true;
        }

        final Block b = ((Player) sender).getTargetBlockExact(5);
        if (b == null) {
            sender.sendMessage("Not looking at a block dumbass");
            return true;
        }

        final String id = BlockStorage.checkID(b);

        Executors.newSingleThreadExecutor().submit(() -> {
            final SlimefunItem item = SlimefunPlugin.getRegistry().getSlimefunItemIds().get(id);
            if (item == null) {
                sender.sendMessage("Can't find item");
                return;
            }

            final Location loc = b.getLocation();
            final Config config = BlockStorage.getLocationInfo(loc);

            if (item.getEnergyTicker() == null) {
                sender.sendMessage("No energy ticker");
                return;
            }

            long start = System.nanoTime();
            for (int i = 0; i < ITERATIONS; i++) {
                item.getEnergyTicker().generateEnergy(loc, item, config);
            }
            long end = System.nanoTime();
            long total = end - start;

            double avg = ((double) total / ITERATIONS);

            sender.sendMessage(ChatColor.GRAY + "LX Stress Test result:"
                + "\n  Iterations: " + ITERATIONS
                + "\n  Total: " + total + "ns (" + (total / 1e6) + "ms)"
                + "\n  Avg/b: " + avg + "ns (" + (avg / 1e6) + "ms)"
                + "\n\nStep avg:"
                + averageSteps()
            );

            this.runs.clear();
        });
        return true;
    }

    public String averageSteps() {
        final StringBuilder sb = new StringBuilder();

        final Map<Integer, Set<Long>> stepTimes = new HashMap<>();
        for (Run run : runs) {
            System.out.println("Checking run - Steps: " + run.getSteps());
            for (int i = 0; i < run.getSteps(); i++) {
                final Set<Long> timings = stepTimes.getOrDefault(i, new HashSet<>());
                timings.add(run.getStep(i));
                stepTimes.put(i, timings);
            }
        }

        for (Map.Entry<Integer, Set<Long>> entry : stepTimes.entrySet()) {
            final DoubleSummaryStatistics stats = entry.getValue().stream().mapToDouble(l -> l).summaryStatistics();
            sb.append("\nStep ").append(entry.getKey()).append('(').append(stats.getCount()).append(')').append("\n  ")
                .append("Avg: ").append(stats.getAverage()).append(", ")
                .append("Min: ").append(stats.getMin()).append(", ")
                .append("Max: ").append(stats.getMax());
        }

        return sb.toString();
    }

    public static class Run {

        private final List<Long> steps = new ArrayList<>();

        public Run addStep(long timing) {
            if (this.steps.isEmpty()) return this;

            this.steps.add(timing - this.steps.get(this.steps.size() - 1));
            return this;
        }

        public long getStep(int i) {
            if (i < 0 || i >= steps.size()) return 0;
            return steps.get(i);
        }

        public int getSteps() {
            return this.steps.size();
        }
    }

    public static void newRun(@Nonnull Run run) {
        instance.runs.add(run);
    }
}
