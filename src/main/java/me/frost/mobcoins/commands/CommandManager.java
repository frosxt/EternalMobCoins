package me.frost.mobcoins.commands;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.commands.SubCommands.*;
import me.frost.mobcoins.inventories.CoinsShop;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    private final ArrayList<SubCommandManager> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new GiveCommand(MobCoins.getInstance()));
        subCommands.add(new BalanceCommand(MobCoins.getInstance()));
        subCommands.add(new ReloadCommand());
        subCommands.add(new PayCommand(MobCoins.getInstance()));
        subCommands.add(new RemoveCommand(MobCoins.getInstance()));
    }

    public ArrayList<SubCommandManager> getSubCommands() {
        return subCommands;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 0) {
            final Player player = (Player) sender;

            final CoinsShop inventory = new CoinsShop(player);
            player.openInventory(inventory.getInventory());
            return false;
        }
        for (int i = 0; i < getSubCommands().size(); i++) {
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName()) || args[0].equalsIgnoreCase(getSubCommands().get(i).getAlias())) {
                getSubCommands().get(i).perform(sender, args);
                return false;
            }
        }
        sendHelpMessage(sender);
        return false;
    }

    public void sendHelpMessage(final CommandSender sender) {
        sender.sendMessage(GeneralUtils.colorize("&e&lEternalMobCoins &7(Made by Frost#0723)"));
        sender.sendMessage(" ");
        sender.sendMessage(GeneralUtils.colorize("&e/mobcoins &f- &7Opens the MobCoin Shop"));
        sender.sendMessage(GeneralUtils.colorize("&e/mobcoins balance <player> &f- &7Shows a players MobCoin balance"));
        sender.sendMessage(GeneralUtils.colorize("&e/mobcoins give <player> <amount> &f- &7Gives a player MobCoins"));
        sender.sendMessage(GeneralUtils.colorize("&e/mobcoins remove <player> <amount> &f- &7Removes a players MobCoins"));
        sender.sendMessage(GeneralUtils.colorize("&e/mobcoins reload &f- &7Reloads the configuration"));
        sender.sendMessage(GeneralUtils.colorize("&e/mobcoins pay <player> <amount> &f- &7Pays a player MobCoins"));
    }
}