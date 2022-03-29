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

    public static boolean isInt(final String integer) {
        try {
            Integer.parseInt(integer);
        } catch (final NumberFormatException nfe) {
            return false;
        }
        return true;
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
                        int amount = Integer.parseInt(args[2]);

                        if (amount <= 0) {
                            player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.pay-negative")));
                            return;
                        }

                        if (!(MobCoinsAPI.getMobCoins(player) >= amount)) {
                            player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.not-enough-mobcoins")));
                            return;
                        }
                        player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.paid-player").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2])));
                        MobCoinsAPI.addMobCoins(target, amount);
                        MobCoinsAPI.removeMobCoins(player, amount);
                        plugin.reloadData();
                    } else {
                        player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.invalid-amount")));
                    }
                } else {
                    player.sendMessage(GeneralUtils.colorize(plugin.getConfig().getString("messages.invalid-player").replaceAll("%player%", args[1])));
                }
            } else {
                player.sendMessage(GeneralUtils.colorize("&c&l(!) &cInvalid arguments! /mobcoins pay <player> <amount>"));
            }
        }
    }
}
