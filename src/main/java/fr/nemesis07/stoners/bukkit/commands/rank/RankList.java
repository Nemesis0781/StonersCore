package fr.nemesis07.stoners.bukkit.commands.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.permission.Permission;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class RankList extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermission() {
        return "rank.list";
    }

    @Override
    public String getDescription() {
        return lang.RankListDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.RankListSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        // TODO Player has permission ?
        //if(p.hasPermission(""))
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();

        //his.lang = stonedPlayer.getLang().;
        //rank list
        RankManager rankManager = Core.getInstance().getRankManager();

        String header = lang.RankListSuccess().getValue().get(0);
        String footer = lang.RankListSuccess().getValue().get(lang.RankListSuccess().getValue().size()-1);
        stonedPlayer.sendMessage(header);

        for (int i = 0; i <rankManager.getRanks().size(); i++) {
            Rank rank = rankManager.getRanks().get(i);


            for (int j = 1; j < lang.RankListSuccess().getValue().size()-1; j++) {
                String line = lang.RankListSuccess().getValue().get(j);
                stonedPlayer.sendMessage(line
                        .replace("%index%", String.valueOf(i+1))
                        .replace("%rank%", rank.toString()));
            }
        }

        stonedPlayer.sendMessage(footer);
    }
}
