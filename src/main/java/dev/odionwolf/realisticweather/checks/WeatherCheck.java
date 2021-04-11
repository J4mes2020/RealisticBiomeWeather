package dev.odionwolf.realisticweather.checks;

import dev.odionwolf.realisticweather.RealisticWeather;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class WeatherCheck implements Listener {

    public WeatherCheck(RealisticWeather realisticWeather) {
        realisticWeather.getServer().getPluginManager().registerEvents(this, realisticWeather);
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
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.UNLUCK);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 1000000, 0, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1000000, 0, true, false, false));

    }

    public void rainy(Player player) {
        player.removePotionEffect(PotionEffectType.LUCK);
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 0, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 1000000, 0, true, false, false));
    }

}
