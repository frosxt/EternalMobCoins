package me.frost.mobcoins;

import me.frost.mobcoins.managers.BalanceManager;
import org.bukkit.entity.Player;

public class MobCoinsAPI {

    /**
     * Gets the players MobCoin balance
     *
     */
    public static int getMobCoins(final Player player) {
        if (BalanceManager.getBalances().get(player.getUniqueId()) == null) {
            return 0;
        }
        return BalanceManager.getBalances().get(player.getUniqueId());
    }

    /**
     * Adds MobCoins to a player
     *
     */
    public static void addMobCoins(final Player player, final Integer amount) {
        if (BalanceManager.getBalances().get(player.getUniqueId()) == null) {
            BalanceManager.getBalances().putIfAbsent(player.getUniqueId(), amount);
            return;
        }
        BalanceManager.getBalances().put(player.getUniqueId(), BalanceManager.getBalances().get(player.getUniqueId()) + amount);
    }

    /**
     * Removes MobCoins from a player
     *
     */
    public static void removeMobCoins(final Player player, final Integer amount) {
        if (BalanceManager.getBalances().get(player.getUniqueId()) == null) {
            return;
        }
        BalanceManager.getBalances().put(player.getUniqueId(), BalanceManager.getBalances().get(player.getUniqueId()) - amount);
    }

    /**
     * Sets a player's MobCoins
     *
     */
    public static void setMobCoins(final Player player, final Integer amount) {
        BalanceManager.getBalances().put(player.getUniqueId(), amount);
    }
}