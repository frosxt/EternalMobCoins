package me.frost.mobcoins.commands.SubCommands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.commands.SubCommandManager;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BalanceCommand implements SubCommandManager {

    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("bal", "amount");
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        FileConfiguration data = MobCoins.dataFile.getConfig();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    player.sendMessage(Formatting.colorize("&a&l(!) &a" + target.getName() + " currently has " + MobCoinsAPI.getMobCoins(target) + " MobCoin(s)!"));
                } else {
                    player.sendMessage(Formatting.colorize("&c&l(!) &cInvalid player!"));
                }
            } else {
                player.sendMessage(Formatting.colorize("&a&l(!) &aYou currently have " + data.getInt("balance." + player.getUniqueId().toString())) + " MobCoin(s)!");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    Bukkit.getServer().getConsoleSender().sendMessage(Formatting.colorize("&a&l(!) &a" + target.getName() + " currently has " + MobCoinsAPI.getMobCoins(target) + " MobCoin(s)!"));
                }
            }
        }
    }
}
