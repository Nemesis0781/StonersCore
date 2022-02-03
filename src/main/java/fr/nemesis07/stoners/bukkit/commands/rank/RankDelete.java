package fr.nemesis07.stoners.bukkit.commands.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class RankDelete extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getPermission() {
        return "rank.delete";
    }

    @Override
    public String getDescription() {
        return lang.RankDeleteDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.RankDeleteSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        // TODO Player has permission ?
        //if(p.hasPermission(""))
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //this.lang = stonedPlayer.getLang();
        // rank delete name
        if(args.length < 2) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank delete = rankManager.getRankByName(args[1]);
        if(delete == null) {
            stonedPlayer.sendMessage(lang.RankErrorRankNotExist().getValue());
            return;
        }
        rankManager.deleteRank(delete);
        stonedPlayer.sendMessage(lang.RankDeleteSuccess().getValue()
                .replace("%rank%", delete.toString()));
    }
}
