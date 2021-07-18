package me.frost.mobcoins.commands.subcommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements SubCommandManager {
    private final MobCoins plugin;

    public PayCommand(final MobCoins plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "pay";
    }

    @Override
    public String getAlias() {
        return "payplayer";
    }

    @Override
    public void perform(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (args.length == 3) {
                final Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (isInt(args[2])) {
                        player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.paid-player").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2])));
                        MobCoinsAPI.addMobCoins(target, Integer.parseInt(args[2]));
                        MobCoinsAPI.removeMobCoins(player, Integer.parseInt(args[2]));
                        GeneralUtils.reloadData();
                    } else {
                        player.sendMessage(GeneralUtils.colorize("&c&l(!) &cPlease specify an amount to give!"));
                    }
                } else {
                    player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.invalid-player").replaceAll("%player%", args[1])));
                }
            } else {
                player.sendMessage(GeneralUtils.colorize("&c&l(!) &cInvalid arguments! /mobcoins pay <player> <amount>"));
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
