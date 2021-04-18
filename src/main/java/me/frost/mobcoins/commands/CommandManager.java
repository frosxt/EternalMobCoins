package me.frost.mobcoins.commands;

import me.frost.mobcoins.commands.SubCommands.*;
import me.frost.mobcoins.inventories.CoinsShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    private ArrayList<SubCommandManager> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new GiveCommand());
        subCommands.add(new BalanceCommand());
        subCommands.add(new ReloadCommand());
        subCommands.add(new PayCommand());
        subCommands.add(new RemoveCommand());
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
            return true;
        }
        if (args.length >= 1) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName()) || args[0].equalsIgnoreCase(getSubCommands().get(i).getAlias())) {
                    getSubCommands().get(i).perform(sender, args);
                    return true;
                }
            }
        }
        return false;
    }
}