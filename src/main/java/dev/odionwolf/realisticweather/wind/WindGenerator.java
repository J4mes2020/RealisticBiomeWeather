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

        Collections.shuffle(windDirectionsList);
        windDirectionsList.toArray(windDirections);

        realisticWeather.getServer().getScheduler().scheduleSyncRepeatingTask(realisticWeather, () -> {

            Vector playerVelocity = player.getVelocity();
            switch (windDirections[0]) {
                case "N":
                    if (!player.isFlying()) {
                        player.setVelocity(playerVelocity.add(new Vector(0, 0, -0.003)));
                    }
                    break;

                case "E":
                    if (!player.isFlying()) {
                        player.setVelocity(playerVelocity.add(new Vector(0.003, 0, 0)));
                    }
                    break;

                case "S":
                    if (!player.isFlying()) {
                        player.setVelocity(playerVelocity.add(new Vector(0, 0, 0.003)));
                    }
                    break;

                case "W":
                    if (!player.isFlying()) {
                        player.setVelocity(playerVelocity.add(new Vector(-0.003, 0, 0)));
                    }
                    break;

            }
        }, 0, 20);
    }
}