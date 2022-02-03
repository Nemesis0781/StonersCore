package fr.nemesis07.stoners.bukkit.commands.permission;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.permission.PermissionManager;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class PermissionEdit extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getPermission() {
        return "permission.edit";
    }

    @Override
    public String getDescription() {
        return lang.PermissionEditDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.PermissionEditSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //permission edit <rank> <permission>
        if(args.length < 3) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        PermissionManager permissionManager = Core.getInstance().getPermissionManager();
        Rank rank = rankManager.getRankByName(args[1]);

        Boolean old = permissionManager.hasPermission(rank, args[2]);
        permissionManager.replaceValue(rank, args[2]);

        Boolean newBo = permissionManager.hasPermission(rank, args[2]);

        stonedPlayer.sendMessage(lang.PermissionEditSuccess().getValue()
                .replace("%old%", old.toString())
                .replace("%new%", newBo.toString()));
    }
}
