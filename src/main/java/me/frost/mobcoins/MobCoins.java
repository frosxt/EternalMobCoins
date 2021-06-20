package me.frost.mobcoins;

import me.frost.mobcoins.commands.CommandManager;
import me.frost.mobcoins.events.PlayerKillEntity;
import me.frost.mobcoins.events.PurchaseEvent;
import me.frost.mobcoins.utils.DataFile;
import me.frost.mobcoins.utils.GeneralUtils;
import me.frost.mobcoins.utils.Placeholders;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class MobCoins extends JavaPlugin {
    public DataFile configFile;
    public DataFile dataFile;
    private static MobCoins instance;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(GeneralUtils.colorize("&e[EternalMobCoins] Enabling plugin..."));
        new Metrics(this, 11370);
        instance = this;
        configFile = new DataFile(this, "config", true);
        dataFile = new DataFile(this, "data", true);
        for (final String data : dataFile.getConfig().getStringList("balance")) {
            final String[] split = data.split(";");
            PlayerKillEntity.playerData.put(UUID.fromString(split[0]), Integer.parseInt(split[1]));
        }
        loadChances();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerKillEntity(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PurchaseEvent(this), this);
        getCommand("mobcoins").setExecutor(new CommandManager());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }
        Bukkit.getLogger().info(GeneralUtils.colorize("&e[EternalMobCoins] Enabled successfully!"));
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(GeneralUtils.colorize("&e[EternalMobCoins] Disabling plugin..."));
        GeneralUtils.reloadData();
        Bukkit.getLogger().info(GeneralUtils.colorize("&e[EternalMobCoins] Disabled successfully!"));
    }

    private void loadChances() {
        for (final String mob : configFile.getConfig().getConfigurationSection("mobs").getKeys(false)) {
            try {
                final EntityType mobType = EntityType.valueOf(mob.toUpperCase());
                final int chance = configFile.getConfig().getInt("mobs." + mob);
                PlayerKillEntity.mobChances.put(mobType, chance);
            } catch (final Exception ex) {
                Bukkit.getLogger().warning(mob + " is not a valid Entity!");
            }
        }
    }

    public static MobCoins getInstance() {
        return instance;
    }
}