package me.frost.mobcoins.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.frost.mobcoins.MobCoins;
import org.bukkit.entity.Player;

import java.text.NumberFormat;

public class Placeholders extends PlaceholderExpansion {
    private MobCoins plugin;

    public Placeholders(MobCoins plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Frost";
    }

    @Override
    public String getIdentifier() {
        return "eternalmobcoins";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        // %eternalmobcoins_balance%
        if (player != null && identifier.toLowerCase().equalsIgnoreCase("balance")) {
            if (plugin.dataFile.getConfig().getInt("balance." + player.getUniqueId().toString()) > 0) {
                return NumberFormat.getInstance().format(plugin.dataFile.getConfig().getInt("balance." + player.getUniqueId().toString()));
            } else {
                return "0";
            }
        }

        return null;
    }
}
