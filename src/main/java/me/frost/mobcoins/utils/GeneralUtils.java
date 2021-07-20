package me.frost.mobcoins.utils;

import org.bukkit.ChatColor;

public class GeneralUtils {
    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
