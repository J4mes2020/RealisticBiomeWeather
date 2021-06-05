package dev.odionwolf.realisticweather.checks;

import dev.odionwolf.realisticweather.RealisticWeather;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BiomeCheck implements Listener {

    private final WeatherCheck weatherCheck;

    public BiomeCheck(RealisticWeather realisticWeather, WeatherCheck weatherCheck) {
        realisticWeather.getServer().getPluginManager().registerEvents(this, realisticWeather);
        this.weatherCheck = weatherCheck;

    }

    @EventHandler
    public void onJoinWeather(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        weatherCheck.playerInWorld.add(player);
        Biome playerBiome = event.getPlayer().getLocation().getBlock().getBiome();

        if (player.getWorld().isClearWeather()) {
            weatherCheck.sunny(player);
        }
        else {
            weatherCheck.rainy(player);
        }

        switch (playerBiome) {
            case DESERT:
            case DESERT_HILLS:
            case DESERT_LAKES:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 1, false, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000000, 0, false, false, false));
                break;
                //add more biomes
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        weatherCheck.playerInWorld.remove(player);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

    }
}