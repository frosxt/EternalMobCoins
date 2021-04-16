package me.frost.mobcoins.events;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.inventories.CoinsShop;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.List;

public class PurchaseEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getHolder() instanceof CoinsShop) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                if (event.getCurrentItem() != null) {
                    FileConfiguration config = MobCoins.configFile.getConfig();
                    for (String section : config.getConfigurationSection("inventory.menu").getKeys(false)) {
                        if (event.getSlot() == config.getInt("inventory.menu." + section + ".slot")) {
                            String uuid = player.getUniqueId().toString();
                            FileConfiguration data = MobCoins.dataFile.getConfig();
                            if (data.getInt("balance." + uuid) >= config.getInt("inventory.menu." + section + ".price")) {
                                List<String> commands = config.getStringList("inventory.menu." + section + ".commands");
                                for (String command : commands) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
                                }
                                data.set("balance." + uuid, data.getInt("balance." + uuid) - config.getInt("inventory.menu." + section + ".price"));
                            } else {
                                player.closeInventory();
                                player.sendMessage(Formatting.colorize("&c&l(!) &cYou do not have enough MobCoins to purchase that!"));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory() != null) {
            if (event.getInventory().getHolder() instanceof CoinsShop) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
