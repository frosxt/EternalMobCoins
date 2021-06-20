package me.frost.mobcoins.utils;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.events.PlayerKillEntity;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeneralUtils {
    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void reloadConfig() {
        MobCoins.getInstance().configFile.reload();
        MobCoins.getInstance().configFile.saveConfig();
    }

    public static void reloadData() {
        final List<String> playerMapData = new ArrayList<>();
        for (final UUID uuid : PlayerKillEntity.playerData.keySet()) {
            final String data = uuid.toString() + ";" + PlayerKillEntity.playerData.get(uuid);
            playerMapData.add(data);
        }
        MobCoins.getInstance().dataFile.getConfig().set("balance", playerMapData);
        MobCoins.getInstance().dataFile.saveConfig();
        MobCoins.getInstance().dataFile.reload();
    }
}
