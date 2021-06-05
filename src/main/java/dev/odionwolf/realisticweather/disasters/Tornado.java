package dev.odionwolf.realisticweather.disasters;

import dev.odionwolf.realisticweather.RealisticWeather;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class Tornado {

    RealisticWeather realisticWeather;

    public Tornado(RealisticWeather realisticWeather) {
        this.realisticWeather = realisticWeather;
    }

    public static Boolean percentChance(double chance) {
        return (Math.random() <= chance);
    }

    public void tornadoManager(Player player) {
        tornadoLayout(player);
    }


    public void tornadoLayout(Player player) {
        World world = player.getWorld();
        int disasterTimer = ThreadLocalRandom.current().nextInt(620, 1140);
        int distanceFromPlayer = 30;

        int max_height = 30;
        double max_radius = 10;
        float lines = 20;
        double height_increasement = 0.5;
        double radius_increasement = max_radius / max_height;
        final int[] angle = {0};
        final int[] counter = {0};

        int randomParticleX = ThreadLocalRandom.current().nextInt(
                player.getLocation().getBlockX() - distanceFromPlayer,
                player.getLocation().getBlockX() + distanceFromPlayer);
        int randomParticleZ = ThreadLocalRandom.current().nextInt(
                player.getLocation().getBlockZ() - distanceFromPlayer,
                player.getLocation().getBlockZ() + distanceFromPlayer);
        int randomParticleY = world.getHighestBlockYAt(randomParticleX, randomParticleZ);
        Location particleLocation = new Location(world, randomParticleX, randomParticleY, randomParticleZ);
        Block particleBlock = particleLocation.subtract(0, 1, 0).getBlock();
        Particle particle = Particle.CLOUD;

        if (percentChance(1.0D) && (!particleBlock.getType().isAir())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (counter[0] >= disasterTimer || realisticWeather.weather.equalsIgnoreCase("sun")) {
                        counter[0] = 0;
                        cancel();
                    }

                    for (int l = 0; l < lines; l++) {
                        for (double y = 0; y < max_height; y += height_increasement) {
                                double radius = y * radius_increasement;
                                double mathLines = Math.toRadians(360 / lines * l + y * 25 - angle[0]);
                                double x = Math.cos(mathLines) * radius;
                                double z = Math.sin(mathLines) * radius;
                                world.spawnParticle(particle,
                                        randomParticleX + x,
                                        randomParticleY + y,
                                        randomParticleZ + z,
                                        0);

                                world.spawnParticle(particle,
                                        randomParticleX + x*5,
                                        randomParticleY + max_height,
                                        randomParticleZ + z*5,
                                        0);

                            }
                        }

                    angle[0] += 4;
                    counter[0]++;
                }

            }.runTaskTimer(realisticWeather, 0, 6);

        }
    }
}