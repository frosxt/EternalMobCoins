package me.frost.mobcoins;

import me.frost.mobcoins.commands.MobCoinCommand;
import me.frost.mobcoins.events.PlayerKillEntity;
import me.frost.mobcoins.events.PurchaseEvent;
import me.frost.mobcoins.utils.DataFile;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class MobCoins extends JavaPlugin {
    public static DataFile configFile;
    public static DataFile dataFile;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e[EternalMobCoins] Enabling plugin..."));
        configFile = new DataFile(this, "config", true);
        dataFile = new DataFile(this, "data", true);
        loadChances();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerKillEntity(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PurchaseEvent(), this);
        getCommand("mobcoins").setExecutor(new MobCoinCommand());
        Bukkit.getConsoleSender().sendMessage(Formatting.colorize("&e[EternalMobCoins] Enabled successfully!"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadChances() {
        PlayerKillEntity.mobChances = new HashMap<>();
        for (String mob : dataFile.getConfig().getConfigurationSection("mobs").getKeys(false)) {
            try {
                EntityType mobType = EntityType.valueOf(mob.toUpperCase());
                int chance = dataFile.getConfig().getInt("mobs." + mob);
                PlayerKillEntity.mobChances.put(mobType, chance);
            } catch (Exception ex) {
                Bukkit.getLogger().warning(mob + " is not a valid Entity!");
            }
        }
    }
}
