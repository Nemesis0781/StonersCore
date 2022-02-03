package fr.nemesis07.stoners.common.storage;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class MySQL {
    private Connection connection;

    private final String host;
    private final String port;
    private final String dbName;
    private final String username;
    private final String password;

    public MySQL(String host, String port, String dbName, String username, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()) {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://"+ host + ":" + port + "/" + dbName + "?characterEncoding=utf8",
                    username, password);
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTables() {
        update("CREATE TABLE IF NOT EXISTS Friends (" +
                "player_uuid VARCHAR(255), " +
                "player_name VARCHAR(255), " +
                "friend_uuid VARCHAR(255), " +
                "friend_name VARCHAR(255))");

        update("CREATE TABLE IF NOT EXISTS Punishments ("+
                "id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(16) NULL DEFAULT NULL," +
                "uuid VARCHAR(35) NULL DEFAULT NULL," +
                "reason VARCHAR(255) NULL DEFAULT NULL," +
                "operator VARCHAR(16) NULL DEFAULT NULL," +
                "punishmentType VARCHAR(16) NULL DEFAULT NULL," +
                "start DATETIME DEFAULT NULL," +
                "end DATETIME DEFAULT NULL," +
                "PRIMARY KEY (id))");

        update("CREATE TABLE IF NOT EXISTS PunishmentHistory (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(16) NULL DEFAULT NULL," +
                "uuid VARCHAR(35) NULL DEFAULT NULL," +
                "reason VARCHAR(255) NULL DEFAULT NULL," +
                "operator VARCHAR(16) NULL DEFAULT NULL," +
                "punishmentType VARCHAR(16) NULL DEFAULT NULL," +
                "start DATETIME DEFAULT NULL," +
                "end DATETIME DEFAULT NULL," +
                "PRIMARY KEY (id))");

        /*update("CREATE TABLE IF NOT EXISTS Servers (" +
                "server_name VARCHAR(255) PRIMARY KEY, " +
                "status INT DEFAULT 0, " +
                "server_address VARCHAR(255), " +
                "server_port INT)");*/

        update("CREATE TABLE IF NOT EXISTS Rank (" +
                "rank_name VARCHAR(255), " +
                "power INT NOT NULL CHECK(power >= 0), " +
                "prefix VARCHAR(255), " +
                "suffix VARCHAR(255), " +
                "primary key(rank_name))");

        update("CREATE TABLE IF NOT EXISTS Permissions (" +
                "permission VARCHAR(255), " +
                "rank_name VARCHAR(255), " +
                "active BIT, " +
                "primary key(permission)," +
                "foreign key(rank_name) references Rank(rank_name))");

        update("CREATE TABLE IF NOT EXISTS Players (" +
                "player_uuid VARCHAR(255) PRIMARY KEY, " +
                "player_name VARCHAR(255) NOT NULL, " +
                "language VARCHAR(255) DEFAULT 'fr', " +
                "rank_name VARCHAR(255), " +
                "coins INT, " +
                "karma INT, " +
                "first_join DATETIME, " +
                "last_seen DATETIME, " +
                "foreign key(rank_name) references Rank(rank_name))");
    }

    public void createAccount(UUID uuid) {
        if(hasAccount(uuid)) return;
        Rank r = Core.getInstance().getRankManager().getDEFAULT();
        Timestamp date = new Timestamp(new java.util.Date().getTime());
        update("INSERT INTO Players VALUES ('"+uuid.toString()+"', " +
                "'"+ Bukkit.getPlayer(uuid).getName()+"'," +
                "'fr', " +
                "'"+ r.getRankName()+"', 0, 0," +
                "'"+date+"'," +
                "'"+date+"'" +
                ")");

        Player p = Bukkit.getPlayer(uuid);
        p.setPlayerListName(
                ChatColor.translateAlternateColorCodes('&', r.getRankPrefix() + p.getName()));

        Core.getInstance().getRankManager().getPlayersRank().put(uuid, r);
    }

    private boolean hasAccount(UUID uuid) {
        boolean[] account = new boolean[1];
        query("SELECT * FROM Players WHERE player_uuid = '"+uuid.toString()+"'", rs -> {
            try {
                if (rs.next()){
                    account[0] = true;
                    String name = rs.getString("rank_name");
                    Rank r = Core.getInstance().getRankManager()
                            .getRanks().stream().filter(rank -> rank.getRankName().equals(name))
                            .findFirst().orElse(Core.getInstance().getRankManager().getDEFAULT());
                    Core.getInstance().getRankManager().getPlayersRank().put(uuid, r);
                    Player p = Bukkit.getPlayer(uuid);
                    p.setPlayerListName(
                            ChatColor.translateAlternateColorCodes('&', r.getRankPrefix() + p.getName()));
                    return;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return account[0];
    }

    public void update(String qry){
        try (PreparedStatement s = getConnection().prepareStatement(qry)) {
            s.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Object query(String qry, Function<ResultSet, Object> consumer){
        try (PreparedStatement s = getConnection().prepareStatement(qry);
             ResultSet rs = s.executeQuery()) {
            return consumer.apply(rs);
        } catch(SQLException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void query(String qry, Consumer<ResultSet> consumer){
        try (PreparedStatement s = getConnection().prepareStatement(qry);
             ResultSet rs = s.executeQuery()) {
            consumer.accept(rs);
        } catch(SQLException e){
            throw new IllegalStateException(e.getMessage());
        }
    }
}