package dev.odionwolf.realisticweather;

import dev.odionwolf.realisticweather.checks.BiomeCheck;
import dev.odionwolf.realisticweather.checks.WeatherCheck;
import org.bukkit.plugin.java.JavaPlugin;

public final class RealisticWeather extends JavaPlugin {


    @Override
    public void onEnable() {
        new WeatherCheck(this);
        new BiomeCheck(new WeatherCheck(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
