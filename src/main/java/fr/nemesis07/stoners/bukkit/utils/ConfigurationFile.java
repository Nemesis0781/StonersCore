package fr.nemesis07.stoners.bukkit.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationFile {

    private final File file;
    private final FileConfiguration configuration;

    public ConfigurationFile(Plugin plugin, String fileName) {
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
