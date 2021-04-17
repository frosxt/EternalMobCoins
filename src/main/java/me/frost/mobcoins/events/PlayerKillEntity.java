package me.frost.mobcoins.events;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerKillEntity implements Listener {
    public static HashMap<EntityType, Integer> mobChances;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            EntityType entity = event.getEntity().getType();
            for (EntityType mob : mobChances.keySet()) {
                if (entity.equals(mob)) {
                    int chance = ThreadLocalRandom.current().nextInt(100);
                    if (chance <= MobCoins.configFile.getConfig().getInt("mobs." + mob)) {
                        player.sendMessage(Formatting.colorize("&a&l+1 MobCoin &7(Grinding Mobs)"));
                        MobCoinsAPI.addMobCoins(player, 1);
                        MobCoins.dataFile.saveConfig();
                    }
                }
            }
        }
    }
}