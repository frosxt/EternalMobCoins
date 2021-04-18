package me.frost.mobcoins.commands.SubCommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PayCommand implements SubCommandManager {

    @Override
    public String getName() {
        return "pay";
    }

    @Override
    public String getAlias() {
        return "payplayer";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        FileConfiguration data = MobCoins.dataFile.getConfig();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (isInt(args[2])) {
                        player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully paid " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                        data.set("balance." + target.getUniqueId().toString(), data.getInt("balance." + target.getUniqueId().toString()) + Integer.parseInt(args[2]));
                        data.set("balance." + player.getUniqueId().toString(), data.getInt("balance." + player.getUniqueId().toString()) - Integer.parseInt(args[2]));
                        MobCoins.dataFile.saveConfig();
                        MobCoins.dataFile.reload();
                    } else {
                        player.sendMessage(Formatting.colorize("&c&l(!) &cPlease specify an amount to give!"));
                    }
                } else {
                    player.sendMessage(Formatting.colorize("&c&l(!) &cInvalid player!"));
                }
            } else {
                player.sendMessage(Formatting.colorize("&c&l(!) &cInvalid arguments! /mobcoins pay <player> <amount>"));
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
