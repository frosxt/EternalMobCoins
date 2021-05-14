package me.frost.mobcoins.commands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.commands.SubCommands.*;
import me.frost.mobcoins.inventories.CoinsShop;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    private ArrayList<SubCommandManager> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new GiveCommand(MobCoins.getInstance()));
        subCommands.add(new BalanceCommand(MobCoins.getInstance()));
        subCommands.add(new ReloadCommand(MobCoins.getInstance()));
        subCommands.add(new PayCommand(MobCoins.getInstance()));
        subCommands.add(new RemoveCommand(MobCoins.getInstance()));
    }

    public ArrayList<SubCommandManager> getSubCommands() {
        return subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;

            CoinsShop inventory = new CoinsShop(player);
            player.openInventory(inventory.getInventory());
            return false;
        }
        if (args.length >= 1) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName()) || args[0].equalsIgnoreCase(getSubCommands().get(i).getAlias())) {
                    getSubCommands().get(i).perform(sender, args);
                    return false;
                }
            }
        }
        sendHelpMessage(sender);
        return false;
    }

    public void sendHelpMessage(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(Formatting.colorize("&e&lEternalMobCoins &7(Made by Frost#0723)"));
            player.sendMessage(" ");
            player.sendMessage(Formatting.colorize("&e/mobcoins &f- &7Opens the MobCoin Shop"));
            player.sendMessage(Formatting.colorize("&e/mobcoins balance <player> &f- &7Shows a players MobCoin balance"));
            player.sendMessage(Formatting.colorize("&e/mobcoins give <player> <amount> &f- &7Gives a player MobCoins"));
            player.sendMessage(Formatting.colorize("&e/mobcoins remove <player> <amount> &f- &7Removes a players MobCoins"));
            player.sendMessage(Formatting.colorize("&e/mobcoins reload &f- &7Reloads the configuration"));
            player.sendMessage(Formatting.colorize("&e/mobcoins pay <player> <amount> &f- &7Pays a player MobCoins"));
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e&lEternalMobCoins &7(Made by Frost#0723)"));
            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e/mobcoins &f- &7Opens the MobCoin Shop"));
            Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e/mobcoins balance <player> &f- &7Shows a players MobCoin balance"));
            Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e/mobcoins give <player> <amount> &f- &7Gives a player MobCoins"));
            Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e/mobcoins remove <player> <amount> &f- &7Removes a players MobCoins"));
            Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e/mobcoins reload &f- &7Reloads the configuration"));
            Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e/mobcoins pay <player> <amount> &f- &7Pays a player MobCoins"));
        }
    }
}