package dev.odionwolf.realisticweather;

import dev.odionwolf.realisticweather.checks.BiomeCheck;
import dev.odionwolf.realisticweather.checks.WeatherCheck;
import dev.odionwolf.realisticweather.commands.WeatherCommand;
import dev.odionwolf.realisticweather.wind.WindGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public final class RealisticWeather extends JavaPlugin {

    WindGenerator windGenerator;
    WeatherCheck weatherCheck;
    public String weather = "sun";
    public double windPushPower = ThreadLocalRandom.current().nextDouble(0, 1);
    public long windSpeed = ThreadLocalRandom.current().nextLong(2, 7);

    @Override
    public void onEnable() {
        windGenerator = new WindGenerator(this);
        weatherCheck = new WeatherCheck(this, windGenerator);
        new BiomeCheck(this, weatherCheck);
        new WeatherCommand(this, windGenerator);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
