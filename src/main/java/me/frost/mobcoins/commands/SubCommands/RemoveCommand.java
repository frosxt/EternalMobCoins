package me.frost.mobcoins.commands.SubCommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RemoveCommand implements SubCommandManager {
    private final MobCoins plugin;

    public RemoveCommand(final MobCoins plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getAlias() {
        return "take";
    }

    @Override
    public void perform(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (args.length == 3) {
                if (player.hasPermission("emobcoins.admin")) {
                    final Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (isInt(args[2])) {
                            player.sendMessage(GeneralUtils.colorize("&a&l(!) &aSuccessfully removed " + args[2] + " MobCoin(s)" + " from " + target.getName() + "!"));
                            MobCoinsAPI.removeMobCoins(target, Integer.parseInt(args[2]));
                            GeneralUtils.reloadData();
                        } else {
                            player.sendMessage(GeneralUtils.colorize("&c&l(!) &cPlease specify an amount to remove!"));
                        }
                    } else {
                        player.sendMessage(GeneralUtils.colorize("&c&l(!) &cInvalid player!"));
                    }
                } else {
                    player.sendMessage(GeneralUtils.colorize("&c&l(!) &cYou do not have permission to execute that command!"));
                }
            } else {
                player.sendMessage(GeneralUtils.colorize("&c&l(!) &cInvalid arguments! /mobcoins remove <player> <amount>"));
            }
        } else if (sender instanceof ConsoleCommandSender) {
            final Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                if (isInt(args[2])) {
                    Bukkit.getServer().getConsoleSender().sendMessage(GeneralUtils.colorize("&a&l(!) &aSuccessfully removed " + args[2] + " MobCoin(s)" + " from " + target.getName() + "!"));
                    MobCoinsAPI.removeMobCoins(target, Integer.parseInt(args[2]));
                    GeneralUtils.reloadData();
                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage(GeneralUtils.colorize("&c&l(!) &cPlease specify an amount to remove!"));
                }
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(GeneralUtils.colorize("&c&l(!) &cInvalid player!"));
            }
        }
    }

    public static boolean isInt(final String integer) {
        try {
            Integer.parseInt(integer);
        } catch (final NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}