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
        //Find a way to add wind which is calm, using velocity 0.02
        World world = player.getWorld();
    }
}
