package dev.odionwolf.realisticweather.commands;

import dev.odionwolf.realisticweather.RealisticWeather;
import dev.odionwolf.realisticweather.wind.WindGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeatherCommand implements CommandExecutor {

    private final WindGenerator windGenerator;

    public WeatherCommand(RealisticWeather realisticWeather, WindGenerator windGenerator) {
        this.windGenerator = windGenerator;
        realisticWeather.getCommand("realisticweather");
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("testdirection")) {
                player.sendMessage(String.valueOf(windGenerator.getWindDirection()));
            }
        }

        return false;
    }
}
