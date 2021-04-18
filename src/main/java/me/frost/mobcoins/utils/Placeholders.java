package me.frost.mobcoins.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.frost.mobcoins.MobCoinsAPI;
import org.bukkit.entity.Player;

import java.text.NumberFormat;

public class Placeholders extends PlaceholderExpansion {
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
            if (MobCoinsAPI.getMobCoins(player) > 0) {
                return NumberFormat.getInstance().format(MobCoinsAPI.getMobCoins(player));
            } else {
                return NumberFormat.getInstance().format(0);
            }
        }

        return null;
    }
}
