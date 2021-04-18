package me.frost.mobcoins.commands.SubCommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RemoveCommand implements SubCommandManager {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getAlias() {
        return "take";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        FileConfiguration data = MobCoins.dataFile.getConfig();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 3) {
                if (player.hasPermission("emobcoins.admin")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (isInt(args[2])) {
                            player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully removed " + args[2] + " MobCoin(s)" + " from " + target.getName() + "!"));
                            data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) - Integer.parseInt(args[2]));
                            MobCoins.dataFile.saveConfig();
                            MobCoins.dataFile.reload();
                        } else {
                            player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to remove!"));
                        }
                    } else {
                        player.sendMessage(Formatting.colorize("&c&l(!) &cInvalid player!"));
                    }
                } else {
                    player.sendMessage(Formatting.colorize("&c&l(!) &cYou do not have permission to execute that command!"));
                }
            } else {
                player.sendMessage(Formatting.colorize("&c&l(!) &cInvalid arguments! /mobcoins remove <player> <amount>"));
            }
        } else if (sender instanceof ConsoleCommandSender) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                if (isInt(args[2])) {
                    Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully removed " + args[2] + " MobCoin(s)" + " from " + target.getName() + "!"));
                    data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) + Integer.parseInt(args[2]));
                    MobCoins.dataFile.saveConfig();
                    MobCoins.dataFile.reload();
                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to remove!"));
                }
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&c&l(!) &cInvalid player!"));
            }
        }
    }

    public static boolean isInt(String integer) {
        try {
            Integer.parseInt(integer);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}