package fr.nemesis07.stoners.bukkit.languages;

import fr.nemesis07.stoners.bukkit.Core;
import javafx.util.Pair;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class LanguageManager {

    private final File languageDirectory;
    private final List<Lang> langList;

    public LanguageManager() {
        languageDirectory = new File(Core.getInstance().getDataFolder(), "languages/");
        if(!languageDirectory.isDirectory()) {
            languageDirectory.mkdir();
        }
        langList = new ArrayList<>();
        langList.add(new FR());
        langList.add(new US());

        createLangue();
    }

    private void createLangue() {
        for(Lang lang: langList) {
            File file = new File(languageDirectory, lang.getLanguage()+".yml");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
            for(Method method: lang.getClass().getDeclaredMethods()) {
                if(method.getName().equalsIgnoreCase("getLanguage")) continue;
                try {
                    Pair<String, ?> pair = (Pair<String, ?>) method.invoke(lang);

                    if(pair.getKey() != null && pair.getValue() != null) {
                        conf.set(pair.getKey(), pair.getValue());
                    }
                } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
                    Core.getInstance().debug(Level.SEVERE, "ERROR Cannot create " + lang.getLanguage() +".yml cause: " + method.getName());
                    continue;
                }
            }

            try {
                conf.save(file);
                Core.getInstance().debug(Level.INFO, "Language " + lang.getLanguage() + " created");
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Lang> getLangs() {
        return langList;
    }
}
