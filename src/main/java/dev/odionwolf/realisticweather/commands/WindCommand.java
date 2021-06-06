package dev.odionwolf.realisticweather.commands;

import dev.odionwolf.realisticweather.RealisticWeather;
import dev.odionwolf.realisticweather.util.UtilMessage;
import dev.odionwolf.realisticweather.wind.WindGenerator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WindCommand implements CommandExecutor {

    private final WindGenerator windGenerator;

    public WindCommand(RealisticWeather realisticWeather, WindGenerator windGenerator) {
        this.windGenerator = windGenerator;
        realisticWeather.getCommand("wind").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off")) {
            windGenerator.windDisabled = true;// needs fixing
            UtilMessage.sendMessage(player, "&cWind disabled!");
            return true;
        }

        if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on")) {
            windGenerator.windDisabled = false;
            UtilMessage.sendMessage(player, "&aWind enabled!");
            return true;
        }

        return false;
    }
}
