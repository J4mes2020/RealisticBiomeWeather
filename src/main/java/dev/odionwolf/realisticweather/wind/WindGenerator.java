package dev.odionwolf.realisticweather.wind;

import dev.odionwolf.realisticweather.RealisticWeather;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
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

    public String getWindDirection() {
        String[] windDirectionOptions = {"N", "E", "S", "W"};
        List<String> shuffle = Arrays.asList(windDirectionOptions);
        Collections.shuffle(shuffle);
        return shuffle.get(0);
    }
    public void onPush(Player player) {
        World world = player.getWorld();
        BukkitScheduler scheduler = realisticWeather.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(realisticWeather, () -> {
            switch (getWindDirection()) {
                case "N":
                    scheduler.scheduleSyncRepeatingTask(realisticWeather, () -> {
                        for (Entity entities : world.getEntities()) {
                            if (realisticWeather.weather.equalsIgnoreCase("sun")) {
                                entities.setVelocity(new Vector(0, 0, -realisticWeather.windPushPower / 2));
                            } else {
                                entities.setVelocity(new Vector(0, 0, -realisticWeather.windPushPower));
                            }
                        }
                    }, 40, realisticWeather.windSpeed);
                    break;
                case "E":
                    scheduler.scheduleSyncRepeatingTask(realisticWeather, () -> {
                        for (Entity entities : world.getEntities()) {
                            if (realisticWeather.weather.equalsIgnoreCase("sun")) {
                                entities.setVelocity(new Vector(-realisticWeather.windPushPower / 2, 0, 0));
                            } else {
                                entities.setVelocity(new Vector(-realisticWeather.windPushPower, 0, 0));
                            }
                        }
                    }, 40, realisticWeather.windSpeed);
                    break;
                case "S":
                    scheduler.scheduleSyncRepeatingTask(realisticWeather, () -> {
                        for (Entity entities : world.getEntities()) {
                            if (realisticWeather.weather.equalsIgnoreCase("sun")) {
                                entities.setVelocity(new Vector(0, 0, realisticWeather.windPushPower / 2));
                            } else {
                                entities.setVelocity(new Vector(0, 0, realisticWeather.windPushPower));
                            }
                        }
                    }, 40, realisticWeather.windSpeed);
                    break;
                case "W":
                    scheduler.scheduleSyncRepeatingTask(realisticWeather, () -> {
                        for (Entity entities : world.getEntities()) {
                            if (realisticWeather.weather.equalsIgnoreCase("sun")) {
                                entities.setVelocity(new Vector(realisticWeather.windPushPower / 2, 0, 0));
                            } else {
                                entities.setVelocity(new Vector(realisticWeather.windPushPower, 0, 0));
                            }
                        }
                    }, 40, realisticWeather.windSpeed);
                    break;
            }
        }, 0, ThreadLocalRandom.current().nextInt(1200, 12000));
    }
}
