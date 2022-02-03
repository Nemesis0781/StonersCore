package fr.nemesis07.stoners.bungee;

import fr.nemesis07.stoners.bungee.listeners.PunishmentListeners;
import fr.nemesis07.stoners.bungee.utils.ConfigurationFileBungee;
import fr.nemesis07.stoners.common.punishment.PunishmentManager;
import fr.nemesis07.stoners.common.storage.MySQL;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.SQLException;
import java.util.logging.Level;

public class BungeeCore extends Plugin {

    private static BungeeCore instance;
    private MySQL SQL;
    private ConfigurationFileBungee configFile;
    private PunishmentManager punishmentManager;

    @Override
    public void onEnable() {
        instance = this;

        configFile = new ConfigurationFileBungee(this, "config");
        if(!getConfig().contains("db.host")) {
            getConfig().set("db.host", "localhost");
            getConfig().set("db.port", "3306");
            getConfig().set("db.dbName", "gameapi");
            getConfig().set("db.username", "root");
            getConfig().set("db.password", "root");
        }
        initConnection();

        this.punishmentManager = new PunishmentManager(getSQL());
        getProxy().getPluginManager().registerListener(this, new PunishmentListeners());
    }

    private void initConnection(){
        this.SQL = new MySQL(getConfig().getString("db.host"),
                getConfig().getString("db.port"),
                getConfig().getString("db.dbName"),
                getConfig().getString("db.username"),
                getConfig().getString("db.password"));
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            debug(Level.WARNING, "\n \n Base de donnée non connecté ! \n");
        }

        if(SQL.isConnected()) {
            SQL.createTables();
        }
    }

    public void debug(Level lvl, String message) {
        getLogger().log(lvl, message);
    }

    public FileConfiguration getConfig() {
        return configFile.getConfiguration();
    }

    public static BungeeCore getInstance() {
        return instance;
    }

    public MySQL getSQL() {
        return SQL;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }
}
