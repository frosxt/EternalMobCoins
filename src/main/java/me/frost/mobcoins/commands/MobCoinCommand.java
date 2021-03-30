package me.frost.mobcoins.commands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.inventories.CoinsShop;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MobCoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            FileConfiguration data = MobCoins.dataFile.getConfig();
            if (args.length == 0) {
                CoinsShop inventory = new CoinsShop(player);
                player.openInventory(inventory.getInventory());
                return false;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (player.hasPermission("emobcoins.give")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if (isInt(args[2])) {
                                player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully gave " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                                data.set("balance." + player.getUniqueId().toString(), data.getInt("balance." + player.getUniqueId().toString()) + Integer.parseInt(args[2]));
                                MobCoins.dataFile.saveConfig();
                                MobCoins.dataFile.reload();
                                return false;
                            } else {
                                player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to give!"));
                            }
                        } else {
                            player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify a player to give!"));
                        }
                    } else {
                        player.sendMessage(Formatting.colorize("&c&l(!) &cYou do not have permission to execute that command!"));
                    }
                }
            }
        } else {
            Bukkit.getLogger().severe("Only players can execute that command!");
        }
        return false;
    }

    public static boolean isInt(String i) {
        try {
            Integer.parseInt(i);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}