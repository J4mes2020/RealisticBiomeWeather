package dev.odionwolf.realisticweather.wind;

import dev.odionwolf.realisticweather.RealisticWeather;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WindGenerator {

    private final RealisticWeather realisticWeather;
    public boolean windDisabled = false;

    public WindGenerator(RealisticWeather realisticWeather) {
        this.realisticWeather = realisticWeather;
    }

    String[] windDirections = {"N", "E", "S", "W"};
    List<String> windDirectionsList = Arrays.asList(windDirections);

    // North = Negative Z
    // South = Positive Z

    //East = Positive X
    //West = Negative X

    public void windManager(Player player) {
        windCreator(player);
    }

    public void windCreator(Player player) {

        if (windDisabled) {

            Collections.shuffle(windDirectionsList);
            windDirectionsList.toArray(windDirections);

            realisticWeather.getServer().getScheduler().scheduleSyncRepeatingTask(realisticWeather, () -> {

                Vector playerVelocity = player.getVelocity();
                if (!player.isFlying()) {
                    switch (windDirections[0]) {
                        case "N":
                            player.setVelocity(playerVelocity.add(new Vector(0, 0, -0.003)));
                            break;

                        case "E":
                            player.setVelocity(playerVelocity.add(new Vector(0.003, 0, 0)));
                            break;

                        case "S":
                            player.setVelocity(playerVelocity.add(new Vector(0, 0, 0.003)));
                            break;

                        case "W":
                            player.setVelocity(playerVelocity.add(new Vector(-0.003, 0, 0)));
                            break;
                    }
                }
            }, 0, 20);
        }
    }
}