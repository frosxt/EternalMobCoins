package me.frost.mobcoins.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommandManager {
    String getName();

    List<String> getAliases();

    void perform(CommandSender player, String args[]);
}
