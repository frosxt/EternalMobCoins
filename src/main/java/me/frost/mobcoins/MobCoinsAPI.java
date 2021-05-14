package me.frost.mobcoins;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MobCoinsAPI {
    private static final FileConfiguration data = MobCoins.getInstance().dataFile.getConfig();

    /**
     * Gets the players MobCoin balance
     * Sometimes this method doesn't update and will always show the same value, working on it!
     *
     * @param player
     * @return
     */
    public static int getMobCoins(Player player) {
        int balance = data.getInt("balance." + player.getUniqueId().toString());
        return balance;
    }

    /**
     * Adds MobCoins to a player
     *
     * @param player
     * @return
     */
    public static void addMobCoins(Player player, Integer amount) {
        data.set("balance." + player.getUniqueId().toString(), data.getInt("balance." + player.getUniqueId().toString()) + amount);
        MobCoins.getInstance().dataFile.saveConfig();
    }

    /**
     * Sets a player's MobCoins
     * Sometimes this method doesn't update and will always show the same value, working on it!
     *
     * @param player
     * @return
     */
    public static void setMobCoins(Player player, Integer amount) {
        data.set("balance." + player.getUniqueId().toString(), amount);
        MobCoins.getInstance().dataFile.saveConfig();
    }

    /**
     * Resets everyone's MobCoins to 0
     *
     * @return
     */
    public static void resetMobCoins() {
        MobCoins.getInstance().dataFile.resetConfig();
    }
}
