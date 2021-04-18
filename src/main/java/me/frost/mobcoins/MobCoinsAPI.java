package me.frost.mobcoins;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MobCoinsAPI {
    private static final FileConfiguration data = MobCoins.dataFile.getConfig();

    public static int getMobCoins(Player player) {
        return data.getInt("balance." + player.getUniqueId().toString());
    }

    public static void addMobCoins(Player player, Integer amount) {
        data.set("balance." + player.getUniqueId().toString(), data.getInt("balance." + player.getUniqueId().toString()) + amount);
        MobCoins.dataFile.saveConfig();
    }

    public static void setMobCoins(Player player, Integer amount) {
        data.set("balance." + player.getUniqueId().toString(), amount);
        MobCoins.dataFile.saveConfig();
    }

    public static void resetMobCoins() {
        MobCoins.dataFile.resetConfig();
    }
}
