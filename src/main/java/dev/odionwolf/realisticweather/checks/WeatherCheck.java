package dev.odionwolf.realisticweather.checks;

import dev.odionwolf.realisticweather.RealisticWeather;
import dev.odionwolf.realisticweather.disasters.Tornado;
import dev.odionwolf.realisticweather.wind.WindGenerator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class WeatherCheck implements Listener {

    private final RealisticWeather realisticWeather;
    private final Tornado tornado;
    private final WindGenerator windGenerator;

    public WeatherCheck(RealisticWeather realisticWeather, Tornado tornado, WindGenerator windGenerator) {
        realisticWeather.getServer().getPluginManager().registerEvents(this, realisticWeather);
        this.realisticWeather = realisticWeather;
        this.tornado = tornado;
        this.windGenerator = windGenerator;
    }

    public ArrayList<Player> playerInWorld = new ArrayList<>();


    @EventHandler
    public void onChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            for (Player player : playerInWorld) {
                rainy(player);
            }
        }

        else {
            for (Player player : playerInWorld) {
                sunny(player);
            }
        }
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
        if (event.toThunderState()) {
        }
    }

    public void sunny(Player player) {
        if (realisticWeather.weather.equalsIgnoreCase("rain") || realisticWeather.weather.isEmpty()) {
            realisticWeather.weather = "";
        }
        realisticWeather.weather = "sun";
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.UNLUCK);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 1000000, 0, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1000000, 0, true, false, false));
        windGenerator.windManager(player);

    }

    public void rainy(Player player) {
        if (realisticWeather.weather.equalsIgnoreCase("sun") || realisticWeather.weather.isEmpty()) {
            realisticWeather.weather = "";
        }
        realisticWeather.weather = "rain";
        player.removePotionEffect(PotionEffectType.LUCK);
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 0, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 1000000, 0, true, false, false));
        tornado.tornadoManager(player);
        windGenerator.windManager(player);

    }

}
