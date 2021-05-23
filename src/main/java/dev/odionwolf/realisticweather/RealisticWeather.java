package dev.odionwolf.realisticweather;

import dev.odionwolf.realisticweather.checks.BiomeCheck;
import dev.odionwolf.realisticweather.checks.WeatherCheck;
import dev.odionwolf.realisticweather.disasters.Tornado;
import org.bukkit.plugin.java.JavaPlugin;

public final class RealisticWeather extends JavaPlugin {

    WeatherCheck weatherCheck;
    Tornado tornado;
    public String weather = "sun";

    @Override
    public void onEnable() {
        tornado = new Tornado(this);
        weatherCheck = new WeatherCheck(this, tornado);
        new BiomeCheck(this, weatherCheck);
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
