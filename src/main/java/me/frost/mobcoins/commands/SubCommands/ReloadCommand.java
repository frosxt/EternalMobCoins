package me.frost.mobcoins.commands.SubCommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.Formatting;
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
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("emobcoins.admin")) {
                MobCoins.configFile.reload();
                player.sendMessage(Formatting.colorize("&a&l(!) &aSuccessfully reloaded the config!"));
            } else {
                player.sendMessage(Formatting.colorize("&c&l(!) &cYou do not have permission to execute that command!"));
            }
        } else if (sender instanceof ConsoleCommandSender) {
            MobCoins.configFile.reload();
            Bukkit.getConsoleSender().sendMessage("Successfully reloaded the config!");
        }
    }
}