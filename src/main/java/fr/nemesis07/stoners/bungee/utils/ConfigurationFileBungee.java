package fr.nemesis07.stoners.bungee.utils;

import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationFileBungee {

    private final File file;
    private final FileConfiguration configuration;

    public ConfigurationFileBungee(Plugin plugin, String fileName) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        this.file = new File(plugin.getDataFolder(), fileName + ".yml");
        this.configuration = new YamlConfiguration();
        if (!file.exists()) {
            try {
                file.createNewFile();
                this.configuration.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public void saveConfig() {
        try {
            this.configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        boolean[] empty = new boolean[1];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            empty[0] = reader.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empty[0];
    }
}
