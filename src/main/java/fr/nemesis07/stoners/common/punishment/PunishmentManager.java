package fr.nemesis07.stoners.common.punishment;

import fr.nemesis07.stoners.bungee.BungeeCore;
import fr.nemesis07.stoners.common.storage.MySQL;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PunishmentManager {

    private final MySQL SQL;
    private final ArrayList<Punishment> punishments;
    private final ArrayList<Punishment> history;

    public PunishmentManager(MySQL sql) {
        this.SQL = sql;
        punishments = new ArrayList<>();
        history = new ArrayList<>();

        this.SQL.query("SELECT * FROM Punishments", rs -> {
            try {
                while (rs.next()) {
                    createPunishment(new Punishment(
                            rs.getString("name"),
                            rs.getString("uuid"),
                            rs.getString("reason"),
                            rs.getString("operator"),
                            PunishmentType.fromCommandName(rs.getString("punishmentType")),
                            rs.getDate("start"),
                            rs.getDate("end"),
                            rs.getInt("id")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        BungeeCore.getInstance().getProxy().getScheduler().schedule(BungeeCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                punishments.remove(
                        punishments.stream().filter(rs -> rs.remainingTime()<0 && rs.remainingTime()!=-3)
                                .findAny().orElse(null));
            }
        }, 0, 2, TimeUnit.MINUTES);
    }

    //String name,
    // String uuid,
    // String reason,
    // String operator,
    // PunishmentType type,
    // long start,
    // long end,
    // String calculation,
    // int id) {

    public void createPunishment(Punishment punishment) {
        punishments.add(punishment);
        SQL.query("SELECT * FROM Punishments WHERE id = '"+punishment.getId()+"'", rs -> {
            try {
                if(!rs.next()) {
                    SQL.update("INSERT INTO Punishments(name, uuid, reason, operator, punishmentType, start, end) " +
                            "VALUES('"+punishment.getName()+"', " +
                            "'"+punishment.getUuid()+"', " +
                            "'"+punishment.getReason()+"', " +
                            "'"+punishment.getOperator()+"', " +
                            "'"+punishment.getType().getName()+"', " +
                            "'"+punishment.getStart()+"', " +
                            "'"+punishment.getEnd()+"')");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void deletePunishment(Punishment p) {
        SQL.query("SELECT * FROM Punishments WHERE id = '"+p.getId()+"'", rs -> {
            try {
                if(rs.next()) {
                    SQL.update("DELETE FROM Punishments WHERE id = '"+p.getId()+ "'");
                    punishments.remove(p);
                    SQL.update("INSERT INTO PunishmentHistory VALUES('"+p.getName()+"', " +
                            "'"+p.getUuid()+"', " +
                            "'"+p.getReason()+"', " +
                            "'"+p.getOperator()+"', " +
                            "'"+p.getType().getName()+"', " +
                            "'"+p.getStart()+"', " +
                            "'"+p.getEnd()+"')");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void updateReason(Punishment p, String reason) {
        SQL.query("SELECT * FROM Punishments WHERE id = '"+p.getId()+"'", rs -> {
            try {
                if(rs.next()) {
                    SQL.update("UPDATE Punishments SET reason = '"+reason+"' WHERE id = '"+p.getId()+ "'");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        punishments.remove(p);
        p.setReason(reason);
        punishments.add(p);
    }

    public List<Punishment> getPlayerPunishments(UUID uuid) {
        List<Punishment> punishmentList = new ArrayList<>();
        SQL.query("SELECT * FROM Punishments, PunishmentHistory WHERE uuid = '"+uuid+"'", rs -> {
            try {
                while (rs.next()) {
                    punishmentList.add(new Punishment(
                            rs.getString("name"),
                            rs.getString("uuid"),
                            rs.getString("reason"),
                            rs.getString("operator"),
                            PunishmentType.fromCommandName(rs.getString("punishmentType")),
                            rs.getDate("start"),
                            rs.getDate("end"),
                            rs.getInt("id")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        return punishmentList;
    }

    public boolean isBanned(ProxiedPlayer p) {
        return isBanned(p.getUniqueId());
    }

    public boolean isBanned(UUID uuid) {
        return isBanned(uuid.toString().replace("-", ""));
    }

    public boolean isBanned(String nameOrip) {
        nameOrip = nameOrip.replace("-", "");
        boolean[] returned = new boolean[1];

        SQL.query("SELECT * FROM Punishments WHERE name = '"+ nameOrip + "' OR uuid = '" + nameOrip +"'", rs -> {
            try {
                if(rs.next()) {
                    returned[0] = true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return returned[0];
    }



    /*"CREATE TABLE IF NOT EXISTS Punishments ("+
                "id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(16) NULL DEFAULT NULL," +
                "uuid VARCHAR(35) NULL DEFAULT NULL," +
                "reason VARCHAR(255) NULL DEFAULT NULL," +
                "operator VARCHAR(16) NULL DEFAULT NULL," +
                "punishmentType VARCHAR(16) NULL DEFAULT NULL," +
                "start DATETIME DEFAULT NULL," +
                "end DATETIME DEFAULT NULL," +
                "PRIMARY KEY (id))");*/

}
