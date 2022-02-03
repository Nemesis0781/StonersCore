package fr.nemesis07.stoners.bukkit;

import fr.nemesis07.stoners.common.storage.MySQL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class PlayerData {

    private final MySQL SQL;
    private final UUID uuid;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.SQL = Core.getInstance().getSQL();
    }

    Timestamp getFirstJoin() {
        final Timestamp[] dates = new Timestamp[1];
        SQL.query("SELECT * FROM Players WHERE player_uuid = '"+uuid.toString()+"'", rs -> {
            try {
                if(rs.next()) {
                    dates[0] = rs.getTimestamp("first_join");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return dates[0];
    }

    Timestamp getLastSeen() {
        final Timestamp[] dates = new Timestamp[1];
        SQL.query("SELECT * FROM Players WHERE player_uuid = '"+uuid.toString()+"'", rs -> {
            try {
                if(rs.next()) {
                    dates[0] = rs.getTimestamp("last_seen");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return dates[0];
    }

    void setLastSeen(Timestamp date) {
        SQL.update("UPDATE Players SET last_seen = '" + date + "' WHERE player_uuid = '"+uuid.toString()+"'");
    }

    String getLanguage() {
        final String[] language = new String[1];
        SQL.query("SELECT * FROM Players WHERE player_uuid = '"+uuid.toString()+"'", rs -> {
            try {
                if(rs.next()) {
                    language[0] = rs.getString("language");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return language[0];
    }

    void setLanguage(String lang) {
        SQL.update("UPDATE Players SET language = '" + lang + "' WHERE player_uuid = '"+uuid.toString()+"'");
    }

    void addCoins(int amount) {
        SQL.update("UPDATE Players SET coins = coins + " + amount + " WHERE player_uuid = '"+uuid.toString()+"'");
    }

    void removeCoins(int amount) {
        SQL.update("UPDATE Players SET coins = coins - " + amount + " WHERE player_uuid = '"+uuid.toString()+"'");
    }

    int getCoins() {
        int[] coins = new int[1];
        coins[0] = 0;
        SQL.query("SELECT * FROM Players WHERE player_uuid = '"+uuid.toString()+"'", rs -> {
            try {
                if(rs.next()) {
                    coins[0] = rs.getInt("coins");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return coins[0];
    }

    void addKarma(int amount) {
        SQL.update("UPDATE Players SET karma = karma + " + amount + " WHERE player_uuid = '"+uuid.toString()+"'");
    }

    void removeKarma(int amount) {
        SQL.update("UPDATE Players SET karma = karma - " + amount + " WHERE player_uuid = '"+uuid.toString()+"'");
    }

    int getKarma() {
        int[] karma = new int[1];
        karma[0] = 0;
        SQL.query("SELECT * FROM Players WHERE player_uuid = '"+uuid.toString()+"'", rs -> {
            try {
                if(rs.next()) {
                    karma[0] = rs.getInt("karma");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return karma[0];
    }



}
