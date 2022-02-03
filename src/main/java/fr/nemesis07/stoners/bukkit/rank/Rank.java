package fr.nemesis07.stoners.bukkit.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.permission.Permission;
import io.netty.handler.codec.DecoderException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class Rank {

    private final String name;
    private final int power;
    private final String prefix;
    private final String suffix;
    private Team team;
    private final ArrayList<Permission> permissions;

    public Rank(String name, int power, String prefix, String suffix) {
        this.name = name;
        this.power = power;
        this.prefix = prefix;
        this.suffix = suffix;
        this.team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(name);
        if(team == null) {
            this.team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(name);
        }
        this.team.setCanSeeFriendlyInvisibles(false);
        this.team.setAllowFriendlyFire(false);

        /* VERIFICATION */
        int prefixLength = 0;
        int suffixLength = 0;
        if (prefix != null) {
            prefixLength = prefix.length();
        }
        if (suffix != null) {
            suffixLength = suffix.length();
        }
        int lengths = prefixLength+suffixLength;
        if (lengths >= 16) {
            throw new DecoderException(Core.getInstance().getDefaultLang().RankErrorPrefixAndSuffixTooLong().getValue()
                    .replace("%length%", lengths+""));
        }
        if (suffix != null) {
            this.team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
        }
        if (prefix != null) {
            this.team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
        }

        this.permissions = new ArrayList<>();
    }

    public String getRankName() {
        return name;
    }

    public int getRankPower() {
        return power;
    }

    public String getRankPrefix() {
        return prefix.replace('_', ' ');
    }

    public String getRankSuffix() {
        return suffix.replace('_', ' ');
    }

    public Team getTeam() {
        return team;
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&',
                "&7(&e" + getRankName() +"&f,&b" + getRankPower() + "&7," + getRankPrefix() + "&7," + getRankSuffix() + "&7)");
    }
}
