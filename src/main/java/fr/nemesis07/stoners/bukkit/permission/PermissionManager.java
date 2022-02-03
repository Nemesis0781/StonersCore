package fr.nemesis07.stoners.bukkit.permission;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.common.storage.MySQL;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class PermissionManager {

    private final MySQL SQL;
    private final Map<Rank, Permission> rankPermission;

    public PermissionManager() {
        this.SQL = Core.getInstance().getSQL();
        rankPermission = new HashMap<>();

        int permissions = 0;
        for(Rank rank: Core.getInstance().getRankManager().getRanks()) {
            this.SQL.query("SELECT * FROM Permissions WHERE rank_name='"+rank.getRankName()+"'", rs -> {
                try {
                    while (rs.next()) {
                        Permission perm = new Permission(rs.getString("permission"), rank, rs.getBoolean("active"));
                        addPermission(perm);
                        rankPermission.put(rank, perm);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            permissions = permissions+rank.getPermissions().size();
        }

        Core.getInstance().debug(Level.INFO, Core.getInstance().getName()+ " "  + permissions + " Permissions added !");
    }

    public boolean hasPermission(Player player, String permission) {
        return hasPermission(Core.getInstance().getRankManager().getRankOfPlayer(player), permission);
    }

    public boolean hasPermission(Player player, Permission permission) {
        return hasPermission(Core.getInstance().getRankManager().getRankOfPlayer(player), permission.getPermission());
    }

    public void addPermission(Permission permission) {
        permission.getRank().getPermissions().add(permission);
    }

    public void removePermission(Permission permission) {
        permission.getRank().getPermissions().add(permission);
    }

    public boolean hasPermission(Rank rank, String permission) {
        Permission perm;
        try {
            perm = rank.getPermissions().stream().filter(permissions -> permissions.getPermission().equalsIgnoreCase(permission)).findFirst().orElse(null);
            return perm.isActive();
        }catch (NullPointerException e) {
            return false;
        }
    }

    public void replaceValue(Rank rank, String permission) {
        Permission perm = rank.getPermissions().stream().filter(permissions -> permissions.getPermission().equalsIgnoreCase(permission)).findFirst().orElse(null);
        perm.setActive(perm.isActive());
    }

    public Permission getPermission(Rank rank, String permission) {
        if(hasPermission(rank, permission))
            return rank.getPermissions().stream().filter(permissions -> permissions.getPermission().equalsIgnoreCase(permission)).findFirst().orElse(null);
        else throw new NullPointerException("Permission doesn't exist in " + rank.getRankName());
    }

    public Map<Rank, Permission> getRankPermission() {
        return rankPermission;
    }
}
