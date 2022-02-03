package fr.nemesis07.stoners.bukkit.utils;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bungee.BungeeCore;

import java.sql.SQLException;

public class ServersCommon {

    public String table = "servers";
    private String displayName;

    public ServersCommon(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return displayName;
    }

    public void loadServer(String address, int port) {
        Core.getInstance().getSQL().query("SELECT status FROM " + table + " WHERE server_name = '"+getName()+"'", rs -> {
            try {
                if(!rs.next()) {
                    Core.getInstance().getSQL().update(
                            "INSERT INTO " + table + " ('"+displayName+"', 1, '"+address+"', "+port+")");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void setStatus(int status) {
        Core.getInstance().getSQL().update("UPDATE " + table + " SET status = " + status + " WHERE server_name = '"+getName()+"'");
    }
}
