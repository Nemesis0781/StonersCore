package fr.nemesis07.stoners.bukkit.permission;

import fr.nemesis07.stoners.bukkit.rank.Rank;
import org.bukkit.ChatColor;

public class Permission {

    private final String permission;
    private final Rank rank;
    private boolean isActive;

    public Permission(String permission, Rank rank, boolean isActive) {
        this.permission = permission;
        this.rank = rank;
        this.isActive = isActive;
    }

    public String getPermission() {
        return permission;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String isActiveToString() {
        if (isActive) return "&atrue";
        else return "&cfalse";
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&',
                "&7(&a'&b" + getPermission() +"&a', &a'&d" + getRank() + "&d'&7, " + isActiveToString() + "&7)");
    }
}
