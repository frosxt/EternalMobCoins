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
        return "frost#0723";
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
    public String onPlaceholderRequest(final Player player, final String identifier) {

        // %eternalmobcoins_balance%
        if (player != null && identifier.equalsIgnoreCase("balance")) {
            return NumberFormat.getInstance().format(MobCoinsAPI.getMobCoins(player));
        }

        return null;
    }
}
