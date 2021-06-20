package me.frost.mobcoins.commands.SubCommands;

import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements SubCommandManager {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getAlias() {
        return "rl";
    }

    @Override
    public void perform(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (player.hasPermission("emobcoins.admin")) {
                GeneralUtils.reloadData();
                GeneralUtils.reloadConfig();
                player.sendMessage(GeneralUtils.colorize("&a&l(!) &aSuccessfully reloaded the config!"));
            } else {
                player.sendMessage(GeneralUtils.colorize("&c&l(!) &cYou do not have permission to execute that command!"));
            }
        } else if (sender instanceof ConsoleCommandSender) {
            GeneralUtils.reloadData();
            GeneralUtils.reloadConfig();
            Bukkit.getConsoleSender().sendMessage("Successfully reloaded the config!");
        }
    }
}