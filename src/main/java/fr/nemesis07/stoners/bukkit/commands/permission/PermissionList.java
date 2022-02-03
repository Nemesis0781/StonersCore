package fr.nemesis07.stoners.bukkit.commands.permission;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.permission.Permission;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class PermissionList extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermission() {
        return "permission.list";
    }

    @Override
    public String getDescription() {
        return lang.PermissionListDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.PermissionListSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        // TODO Player has permission ?
        //if(p.hasPermission(""))
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //rank list <rank>
        if(args.length < 2) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }
        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRankByName(args[1]);

        String header = lang.PermissionListSuccess().getValue().get(0);
        String footer = lang.PermissionListSuccess().getValue().get(lang.RankListSuccess().getValue().size()-1);
        stonedPlayer.sendMessage(header);

        for (int i = 0; i < rank.getPermissions().size(); i++) {
            Permission permission = rank.getPermissions().get(i);

            for (int j = 1; j < lang.PermissionListSuccess().getValue().size()-1; j++) {
                String line = lang.PermissionListSuccess().getValue().get(j);
                stonedPlayer.sendMessage(line
                        .replace("%index%", String.valueOf(i+1))
                        .replace("%permission%", permission.getPermission())
                        .replace("%value%", permission.isActiveToString()));
            }
        }

        stonedPlayer.sendMessage(footer);
    }
}
