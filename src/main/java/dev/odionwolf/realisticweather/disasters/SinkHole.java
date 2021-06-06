package dev.odionwolf.realisticweather.disasters;

import dev.odionwolf.realisticweather.RealisticWeather;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SinkHole {

    RealisticWeather realisticWeather;

    public SinkHole(RealisticWeather realisticWeather) {
        this.realisticWeather = realisticWeather;
    }

    public static Boolean percentChance(double chance) {
        return (Math.random() <= chance);
    }

    public void sinkHoleManager(Player player) {
        sinkHoleMain(player);
    }

    public void sinkHoleMain(Player player) {
        if (percentChance(1.0D)) {//CHANGE TO 0.01
            World world = player.getWorld();
            int distanceFromPlayer = 30;

            int min_height = 50;
            double radius = 30;
            double radius_increasement = radius / min_height;
            final int[] angle = {0};

            int randomSinkHoleX = ThreadLocalRandom.current().nextInt(
                    player.getLocation().getBlockX() - distanceFromPlayer,
                    player.getLocation().getBlockX() + distanceFromPlayer);
            int randomSinkHoleZ = ThreadLocalRandom.current().nextInt(
                    player.getLocation().getBlockZ() - distanceFromPlayer,
                    player.getLocation().getBlockZ() + distanceFromPlayer);
            int randomSinkHoleY = world.getHighestBlockYAt(randomSinkHoleX, randomSinkHoleZ)-70;

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (double y = 0; y < min_height; y++) {
                        double radius = y * radius_increasement;
                        double x = (radius * Math.sin(angle[0]));
                        double z = (radius * Math.cos(angle[0]));//FIX HOW IT SPAWNS
                        Location blockLocation = new Location(world, randomSinkHoleX + x, randomSinkHoleY + y, randomSinkHoleZ + z);
                        Block block = world.getBlockAt(blockLocation);
                        if (block.getType() == Material.BEDROCK) {
                            return;
                        }
                        block.setType(Material.AIR);
                        System.out.println(blockLocation);

                    }
                    angle[0]++;
                }
            }.runTaskTimer(realisticWeather, 0, 20);
        }
    }
}
