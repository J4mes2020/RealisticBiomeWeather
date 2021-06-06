package dev.odionwolf.realisticweather.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UtilMessage {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
