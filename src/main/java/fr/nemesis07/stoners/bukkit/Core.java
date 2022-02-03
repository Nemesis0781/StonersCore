package fr.nemesis07.stoners.bukkit;

import fr.nemesis07.stoners.bukkit.commands.PermCMDManager;
import fr.nemesis07.stoners.bukkit.commands.RankCMDManager;
import fr.nemesis07.stoners.bukkit.languages.FR;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import fr.nemesis07.stoners.bukkit.languages.LanguageManager;
import fr.nemesis07.stoners.bukkit.listeners.ChatListeners;
import fr.nemesis07.stoners.bukkit.listeners.PlayerListeners;
import fr.nemesis07.stoners.bukkit.permission.PermissionManager;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.utils.ServersCommon;
import fr.nemesis07.stoners.bukkit.utils.SettingsUtils;
import fr.nemesis07.stoners.common.storage.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;

public class Core extends JavaPlugin {

    private static Core instance;
    private MySQL SQL;
    private RankManager rankManager;
    private PermissionManager permissionManager;
    private LanguageManager languageManager;
    private Lang defaultLang;
    private ServersCommon serversCommon;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        initConnection();

        registerEvents();
        registerCommands();
        rankManager = new RankManager();
        permissionManager = new PermissionManager();
        languageManager = new LanguageManager();
        try {
            defaultLang = languageManager
                    .getLangs().stream()
                    .filter(p -> p.getLanguage().equalsIgnoreCase(getConfig().getString("defaultLang")))
                    .findFirst().orElse(null);
        } catch (NullPointerException e) {
            throw new NullPointerException("Unknown language in config.yml");
        }
    }

    private void registerCommands() {
        getCommand("rank").setExecutor(new RankCMDManager());
        getCommand("permission").setExecutor(new PermCMDManager());
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListeners(), this);
        pm.registerEvents(new ChatListeners(), this);
    }

    @Override
    public void onDisable() {
        this.SQL.disconnect();
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

    public static Core getInstance() {
        return instance;
    }

    public MySQL getSQL() {
        return SQL;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public void debug(Level lvl, String message) {
        if(SettingsUtils.isDebugEnable())
            getLogger().log(lvl, message);
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public Lang getDefaultLang() {
        return defaultLang;
    }

    /*TODO
    *   Manager: GameManager, PlayerManager, TeamManager, ArenaManager
    *   Events: FoodLevel, HealLevel, PlayerJoin, PlayerQuit, PlayerPlace, PlayerBreak;
    *   Faire systeme de pour créer achivement si on créer un jeu
    * */
}
