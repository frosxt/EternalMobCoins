package me.frost.mobcoins.events;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.inventories.CoinsShop;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.List;

public class PurchaseEvent implements Listener {
    private final MobCoins plugin;

    public PurchaseEvent(final MobCoins plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) {
            return;
        }
        if (!(event.getClickedInventory().getHolder() instanceof CoinsShop)) {
            return;
        }
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
        if (event.getCurrentItem() != null) {
            final FileConfiguration config = plugin.configFile.getConfig();
            for (final String section : config.getConfigurationSection("inventory.menu").getKeys(false)) {
                if (event.getSlot() == config.getInt("inventory.menu." + section + ".slot")) {
                    final String uuid = player.getUniqueId().toString();
                    final FileConfiguration data = plugin.dataFile.getConfig();
                    if (data.getInt("balance." + uuid) >= config.getInt("inventory.menu." + section + ".price")) {
                        final List<String> commands = config.getStringList("inventory.menu." + section + ".commands");
                        for (final String command : commands) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
                        }
                        data.set("balance." + uuid, data.getInt("balance." + uuid) - config.getInt("inventory.menu." + section + ".price"));
                        plugin.dataFile.saveConfig();
                        player.updateInventory();
                    } else {
                        player.closeInventory();
                        player.sendMessage(GeneralUtils.colorize(config.getString("messages.not-enough-mobcoins")));
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(final InventoryDragEvent event) {
        if (event.getInventory() == null) {
            return;
        }
        if (!(event.getInventory().getHolder() instanceof CoinsShop)) {
            return;
        }
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
    }
}