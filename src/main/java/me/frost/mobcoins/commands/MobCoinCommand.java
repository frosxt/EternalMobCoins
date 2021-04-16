package me.frost.mobcoins.commands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
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
            Player player = (Player) sender;
            FileConfiguration data = MobCoins.dataFile.getConfig();
            switch (args.length) {
                case 0:
                    CoinsShop inventory = new CoinsShop(player);
                    player.openInventory(inventory.getInventory());
                    return false;
                case 1:
                    if (args[0].equalsIgnoreCase("bal") || args[0].equalsIgnoreCase("balance")) {
                        player.sendMessage(Formatting.colorize("&a&l(!) &aYou currently have " + data.getInt("balance." + player.getUniqueId().toString())) + " MobCoin(s)!");
                    }
                    if (args[0].equalsIgnoreCase("reload")) {
                        MobCoins.configFile.reload();
                        player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully reloaded the config!"));
                    }
                    else {
                        sendHelpMessage(player);
                    }
                    return false;
                case 2:
                    if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            player.sendMessage(Formatting.colorize("&a&l(!) &a" + target.getName() + " currently has " + MobCoinsAPI.getMobCoins(target) + " MobCoin(s)!"));
                        }
                    }
                    return false;
                case 3:
                    if (args[0].equalsIgnoreCase("give")) {
                        if (player.hasPermission("emobcoins.give")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target != null) {
                                if (isInt(args[2])) {
                                    player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully gave " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                                    data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) + Integer.parseInt(args[2]));
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
                    if (args[0].equalsIgnoreCase("remove")) {
                        if (player.hasPermission("emobcoins.give")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target != null) {
                                if (isInt(args[2])) {
                                    player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully removed " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                                    data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) - Integer.parseInt(args[2]));
                                    MobCoins.dataFile.saveConfig();
                                    MobCoins.dataFile.reload();
                                    return false;
                                } else {
                                    player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to remove!"));
                                }
                            } else {
                                player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify a player to remove!"));
                            }
                        } else {
                            player.sendMessage(Formatting.colorize("&c&l(!) &cYou do not have permission to execute that command!"));
                        }
                    }
                    if (args[0].equalsIgnoreCase("pay")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if (isInt(args[2])) {
                                player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully paid " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                                data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) + Integer.parseInt(args[2]));
                                data.set("balance." + player.getUniqueId().toString(), data.getInt("balance." + player.getUniqueId().toString()) - Integer.parseInt(args[2]));
                                MobCoins.dataFile.saveConfig();
                                MobCoins.dataFile.reload();
                                return false;
                            } else {
                                player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to give!"));
                            }
                        } else {
                            player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify a player to give!"));
                        }
                    }
            }
        } else {
            FileConfiguration data = MobCoins.dataFile.getConfig();
            switch (args.length) {
                case 2:
                    if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&a&l(!) &a" + target.getName() + " currently has " + MobCoinsAPI.getMobCoins(target) + " MobCoin(s)!"));
                        }
                    }
                    return false;
                case 3:
                    if (args[0].equalsIgnoreCase("give")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if (isInt(args[2])) {
                                Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully gave " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                                data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) + Integer.parseInt(args[2]));
                                MobCoins.dataFile.saveConfig();
                                MobCoins.dataFile.reload();
                                return false;
                            } else {
                                Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to give!"));
                            }
                        } else {
                            Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&c&l(!) &cPlease specify a player to give!"));
                        }
                    }
                    if (args[0].equalsIgnoreCase("remove")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if (isInt(args[2])) {
                                Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully removed " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                                data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) - Integer.parseInt(args[2]));
                                MobCoins.dataFile.saveConfig();
                                MobCoins.dataFile.reload();
                                return false;
                            } else {
                                Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to remove!"));
                            }
                        } else {
                            Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&c&l(!) &cPlease specify a player to remove!"));
                        }
                    }
            }
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

    public void sendHelpMessage(Player player) {
        player.sendMessage(Formatting.colorize("&e&lEternalMobCoins &7(Made by Frost#0723)"));
        player.sendMessage(" ");
        player.sendMessage(Formatting.colorize("&e/mobcoins &f- &7Opens the MobCoin Shop"));
        player.sendMessage(Formatting.colorize("&e/mobcoins give <player> <amount> &f- &7Gives a player MobCoins"));
        player.sendMessage(Formatting.colorize("&e/mobcoins remove <player> <amount> &f- &7Removes a players MobCoins"));
        player.sendMessage(Formatting.colorize("&e/mobcoins pay <player> <amount> &f- &7Pays a player MobCoins"));
    }
}