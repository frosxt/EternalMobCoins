package me.frost.mobcoins.events;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.managers.MobManager;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerKillEntity implements Listener {
    private final MobCoins plugin;

    public PlayerKillEntity(final MobCoins plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(final EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) {
            return;
        }
        final Player player = event.getEntity().getKiller();
        final EntityType entity = event.getEntity().getType();
        for (final EntityType mob : MobManager.getMobChances().keySet()) {
            if (entity.equals(mob)) {
                final int chance = ThreadLocalRandom.current().nextInt(100);
                if (chance <= plugin.getConfig().getInt("mobs." + mob)) {
                    String message = GeneralUtils.colorize(plugin.getConfig().getString("messages.received-mobcoins"));
                    if (!message.equals("")) {
                        player.sendMessage(message);
                    }
                    MobCoinsAPI.addMobCoins(player, plugin.getConfig().getInt("settings.amount-per-mob"));
                }
            }
        }
    }
}