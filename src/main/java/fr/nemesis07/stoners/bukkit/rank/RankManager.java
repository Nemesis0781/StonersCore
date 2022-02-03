package fr.nemesis07.stoners.bukkit.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.common.storage.MySQL;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class RankManager {

    private final MySQL SQL;
    private final Rank DEFAULT;
    private final ArrayList<Rank> ranks;
    private final Map<UUID, Rank> playersRank;

    public RankManager() {
        this.SQL = Core.getInstance().getSQL();
        ranks = new ArrayList<>();
        playersRank = new HashMap<>();

        this.DEFAULT = new Rank("Default", 0, "&7[Default] ", "");
        createRank(DEFAULT);
        this.SQL.query("SELECT * FROM Rank WHERE rank_name != 'Default'", rs -> {
            try {
                while (rs.next()) {
                    createRank(new Rank(rs.getString("rank_name"),
                            rs.getInt("power"),
                            rs.getString("prefix"),
                            rs.getString("suffix")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        Core.getInstance().debug(Level.INFO, Core.getInstance().getName() + " " + ranks.size() + " Rank listed !");
    }

    public void createRank(Rank rank) {
        ranks.add(rank);
        SQL.query("SELECT * FROM Rank WHERE rank_name = '"+rank.getRankName()+"'", rs -> {
            try {
                if(!rs.next()) {
                    SQL.update("INSERT INTO Rank VALUES('"+rank.getRankName()+"', " +
                            ""+rank.getRankPower()+", " +
                            "'"+rank.getRankPrefix()+"', " +
                            "'"+rank.getRankSuffix()+"')");
                    Core.getInstance().debug(Level.INFO, "[Core] Rank create successfully");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void deleteRank(Rank rank) {
        SQL.query("SELECT * FROM Rank WHERE rank_name = '"+rank.getRankName()+"'", rs -> {
            try {
                if(rs.next()) {
                    SQL.update("DELETE FROM Rank WHERE rank_name = '"+rank.getRankName()+ "'");
                    rank.getTeam().unregister();
                    ranks.remove(rank);
                    Core.getInstance().debug(Level.INFO, "[Core] Rank delete successfully");
                } else {
                    Core.getInstance().debug(Level.WARNING, "[Core] Rank not exist in database !");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void editRank(Rank rank, String databaseColumn, String newValue) {
        SQL.query("SELECT * FROM Rank WHERE rank_name = '"+rank.getRankName()+"'", rs -> {
            try {
                if(rs.next()) {
                    rank.getTeam().unregister();
                    ranks.remove(rank);
                    if(databaseColumn.equalsIgnoreCase("power")) {
                        SQL.update("UPDATE Rank SET "+databaseColumn+" = "+newValue+" WHERE rank_name = '"+rank.getRankName()+ "'");
                    } else {
                        SQL.update("UPDATE Rank SET "+databaseColumn+" = '"+newValue+"' WHERE rank_name = '"+rank.getRankName()+ "'");
                    }
                    ranks.add(new Rank(rs.getString("rank_name"),
                            rs.getInt("power"),
                            rs.getString("prefix"),
                            rs.getString("suffix")));
                    Core.getInstance().debug(Level.INFO, "[Core] Rank edit successfully");
                } else {
                    Core.getInstance().debug(Level.WARNING, "[Core] Rank not exist in database !");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public ArrayList<Rank> getRanks() {
        return ranks;
    }

    public Map<UUID, Rank> getPlayersRank() {
        return playersRank;
    }


    public Rank getRankOfPlayer(Player player) {
        return playersRank.get(player.getUniqueId());
    }

    public Rank getRankOfUUID(UUID uuid) {
        return playersRank.get(uuid);
    }

    public Rank getRankByName(String rank_name) {
        return getRanks().stream().filter(r -> r.getRankName().equalsIgnoreCase(rank_name)).findFirst().orElse(null);
    }

    public void setRank(Player player, Rank rank) {
        playersRank.remove(player.getUniqueId());
        playersRank.put(player.getUniqueId(), rank);
    }

    public Rank getDEFAULT() {
        return DEFAULT;
    }
}
