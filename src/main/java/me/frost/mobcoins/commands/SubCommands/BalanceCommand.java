package me.frost.mobcoins.commands.SubCommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements SubCommandManager {
    private final MobCoins plugin;

    public BalanceCommand(final MobCoins plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public String getAlias() {
        return "bal";
    }

    @Override
    public void perform(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (args.length == 2) {
                final Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    player.sendMessage(GeneralUtils.colorize("&a&l(!) &a" + target.getName() + " currently has " + MobCoinsAPI.getMobCoins(target) + " MobCoin(s)!"));
                } else {
                    player.sendMessage(GeneralUtils.colorize("&c&l(!) &cInvalid player!"));
                }
            } else {
                player.sendMessage(GeneralUtils.colorize("&a&l(!) &aYou currently have " + MobCoinsAPI.getMobCoins(player) + " MobCoin(s)!"));
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
                final Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    Bukkit.getServer().getConsoleSender().sendMessage(GeneralUtils.colorize("&a&l(!) &a" + target.getName() + " currently has " + MobCoinsAPI.getMobCoins(target) + " MobCoin(s)!"));
                }
            }
        }
    }
}
