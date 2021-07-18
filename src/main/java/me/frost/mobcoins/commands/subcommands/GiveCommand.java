package me.frost.mobcoins.commands.subcommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveCommand implements SubCommandManager {
    private final MobCoins plugin;

    public GiveCommand(final MobCoins plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getAlias() {
        return "add";
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
                            player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.gave-mobcoins")));
                            MobCoinsAPI.addMobCoins(target, Integer.parseInt(args[2]));
                            GeneralUtils.reloadData();
                        } else {
                            player.sendMessage(GeneralUtils.colorize("&c&l(!) &cPlease specify an amount to give!"));
                        }
                    } else {
                        player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.invalid-player").replaceAll("%player%", args[1])));
                    }
                } else {
                    player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.no-permission")));
                }
            } else {
                player.sendMessage(GeneralUtils.colorize("&c&l(!) &cInvalid arguments! /mobcoins give <player> <amount>"));
            }
        } else if (sender instanceof ConsoleCommandSender) {
            final Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                if (isInt(args[2])) {
                    Bukkit.getServer().getConsoleSender().sendMessage(GeneralUtils.colorize("&a&l(!) &aSuccessfully gave " + target.getName() + " " + args[2] + " MobCoin(s)!"));
                    MobCoinsAPI.addMobCoins(target, Integer.parseInt(args[2]));
                    GeneralUtils.reloadData();
                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage(GeneralUtils.colorize("&c&l(!) &cPlease specify an amount to give!"));
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