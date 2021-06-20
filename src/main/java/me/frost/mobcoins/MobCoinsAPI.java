package me.frost.mobcoins;

import me.frost.mobcoins.events.PlayerKillEntity;
import org.bukkit.entity.Player;

public class MobCoinsAPI {

    /**
     * Gets the players MobCoin balance
     *
     */
    public static int getMobCoins(final Player player) {
        if (PlayerKillEntity.playerData.get(player.getUniqueId()) == null) {
            return 0;
        }
        return PlayerKillEntity.playerData.get(player.getUniqueId());
    }

    /**
     * Adds MobCoins to a player
     *
     */
    public static void addMobCoins(final Player player, final Integer amount) {
        if (PlayerKillEntity.playerData.get(player.getUniqueId()) == null) {
            PlayerKillEntity.playerData.putIfAbsent(player.getUniqueId(), amount);
        }
        PlayerKillEntity.playerData.put(player.getUniqueId(), PlayerKillEntity.playerData.get(player.getUniqueId()) + amount);
    }

    /**
     * Removes MobCoins from a player
     *
     */
    public static void removeMobCoins(final Player player, final Integer amount) {
        if (PlayerKillEntity.playerData.get(player.getUniqueId()) == null) {
            return;
        }
        PlayerKillEntity.playerData.put(player.getUniqueId(), PlayerKillEntity.playerData.get(player.getUniqueId()) - amount);
    }

    /**
     * Sets a player's MobCoins
     *
     */
    public static void setMobCoins(final Player player, final Integer amount) {
        PlayerKillEntity.playerData.put(player.getUniqueId(), amount);
    }
}