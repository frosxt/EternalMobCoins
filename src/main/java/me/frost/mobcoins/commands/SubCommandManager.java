package me.frost.mobcoins.commands;

import org.bukkit.command.CommandSender;

public interface SubCommandManager {
    String getName();

    String getAlias();

    void perform(CommandSender player, String[] args);
}
