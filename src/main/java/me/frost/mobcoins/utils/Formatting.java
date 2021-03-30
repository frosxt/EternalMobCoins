package me.frost.mobcoins.utils;

import org.bukkit.ChatColor;

public class Formatting {
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
