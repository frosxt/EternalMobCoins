package me.frost.mobcoins.utils;

import com.google.common.io.Files;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DataFile {
    private final JavaPlugin plugin;
    private FileConfiguration configuration;
    private final boolean hasDefault;
    private final File file;
    private final String fileName;

    public DataFile(final JavaPlugin plugin, final String fileName, final boolean hasDefault) {
        this.plugin = plugin;
        this.hasDefault = hasDefault;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder() + File.separator + fileName + ".yml");
        reload();
    }

    public void reload() {
        if (!this.file.exists()) {
            try {
                if (this.hasDefault) {
                    this.plugin.saveResource(this.fileName + ".yml", false);
                } else {
                    this.file.createNewFile();
                }
            } catch (final IOException | SecurityException ex) {
                ex.printStackTrace();
            }
        }
        loadConfig();
    }

    public void loadConfig() {
        this.configuration = new YamlConfiguration();
        try {
            this.configuration.loadFromString(Files.toString(this.file, StandardCharsets.UTF_8));
        } catch (final IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.configuration;
    }

    public void saveConfig() {
        try {
            this.configuration.save(this.file);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

}
