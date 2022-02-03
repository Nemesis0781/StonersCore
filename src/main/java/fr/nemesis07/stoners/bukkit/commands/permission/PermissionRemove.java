package fr.nemesis07.stoners.bukkit.commands.permission;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.permission.Permission;
import fr.nemesis07.stoners.bukkit.permission.PermissionManager;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class PermissionRemove extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getPermission() {
        return "permission.remove";
    }

    @Override
    public String getDescription() {
        return lang.PermissionRemoveDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.PermissionRemoveSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //permission remove <rank> <permission>
        if(args.length < 3) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        PermissionManager permissionManager = Core.getInstance().getPermissionManager();

        Rank rank = rankManager.getRankByName(args[1]);
        Permission permission = permissionManager.getPermission(rank, args[2]);

        permissionManager.removePermission(permission);
        stonedPlayer.sendMessage(lang.PermissionRemoveSuccess().getValue()
                .replace("%permission%", permission.toString()));
    }
}
