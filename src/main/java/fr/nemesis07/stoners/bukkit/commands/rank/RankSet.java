package fr.nemesis07.stoners.bukkit.commands.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class RankSet extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getPermission() {
        return "rank.set";
    }

    @Override
    public String getDescription() {
        return lang.RankSetDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.RankSetSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        // TODO Player has permission ?
        //if(p.hasPermission(""))
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //this.lang = stonedPlayer.getLang();
        //rank set pseudo rankname
        if(args.length < 3) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        OfflinePlayer[] target = {Bukkit.getPlayer(args[1])};
        if(target[0] == null) {
            Core.getInstance().getSQL().query("SELECT * FROM Players WHERE player_name = '"+args[1]+"'", rs -> {
                try {
                    if(rs.next()) {
                        target[0] = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("player_uuid")));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            stonedPlayer.sendMessage(lang.RankErrorTargetNotFound().getValue());
            return;
        }

        Rank oldRank = rankManager.getRankOfUUID(target[0].getUniqueId());
        Rank newRank = rankManager.getRankByName(args[2]);

        rankManager.setRank(target[0].getPlayer(), newRank);
        stonedPlayer.sendMessage(lang.RankSetSuccess().getValue()
                    .replace("%player%", target[0].getName())
                    .replace("%oldrank%", oldRank.toString())
                    .replace("%newrank%", newRank.toString()));

    }
}
