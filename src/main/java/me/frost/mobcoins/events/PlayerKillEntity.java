package me.frost.mobcoins.events;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerKillEntity implements Listener {
    public static Map<EntityType, Integer> mobChances = new HashMap<>();
    public static Map<UUID, Integer> playerData = new ConcurrentHashMap<>();
    private final MobCoins plugin;

    public PlayerKillEntity(final MobCoins plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(final EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            final Player player = event.getEntity().getKiller();
            final EntityType entity = event.getEntity().getType();
            for (final EntityType mob : mobChances.keySet()) {
                if (entity.equals(mob)) {
                    final int chance = ThreadLocalRandom.current().nextInt(100);
                    if (chance <= plugin.configFile.getConfig().getInt("mobs." + mob)) {
                        player.sendMessage(GeneralUtils.colorize("&a&l+1 MobCoin &7(Grinding Mobs)"));
                        MobCoinsAPI.addMobCoins(player, 1);
                    }
                }
            }
        }
    }
}