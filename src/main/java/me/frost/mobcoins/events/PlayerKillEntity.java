package me.frost.mobcoins.events;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerKillEntity implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            FileConfiguration data = MobCoins.dataFile.getConfig();
            int chance = ThreadLocalRandom.current().nextInt(100);
            if (chance <= MobCoins.configFile.getConfig().getInt("mobcoin-chance")) {
                player.sendMessage(Formatting.colorize("&a&l+1 MobCoin"));
                data.set("balance." + player.getUniqueId().toString(), data.getInt("balance." + player.getUniqueId().toString()) + 1);
                MobCoins.dataFile.saveConfig();
                MobCoins.dataFile.reload();
            }
        }
    }
}