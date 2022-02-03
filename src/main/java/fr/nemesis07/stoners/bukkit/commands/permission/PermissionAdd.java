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

public class PermissionAdd extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getPermission() {
        return "permission.add";
    }

    @Override
    public String getDescription() {
        return lang.PermissionAddDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.PermissionAddSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //this.lang = stonedPlayer.getLang();
        //permission add <rank> <permission> {boolean = default true}
        if(args.length < 3) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        PermissionManager permissionManager = Core.getInstance().getPermissionManager();
        Rank rank = rankManager.getRankByName(args[1]);
        if(permissionManager.hasPermission(rank, args[2])) {
            Permission perm = permissionManager.getPermission(rank, args[2]);
            stonedPlayer.sendMessage(lang.PermissionErrorAlreadyHasPermission().getValue()
                    .replace("%rank%", rank.toString()).replace("%active%", perm.isActiveToString()));
            return;
        }

        if(args.length == 3) {
            Permission permission = new Permission(args[2], rank, true);
            permissionManager.addPermission(permission);

            stonedPlayer.sendMessage(lang.PermissionAddSuccess().getValue()
                .replace("%rank%", rank.toString())
                    .replace("%permission%", permission.toString())
                    .replace("%val%", permission.isActiveToString()));
        } else if(args.length == 4) {
            Permission permission = new Permission(args[2], rank, Boolean.valueOf(args[3]));
            permissionManager.addPermission(permission);

            stonedPlayer.sendMessage(lang.PermissionAddSuccess().getValue()
                    .replace("%rank%", rank.toString())
                    .replace("%permission%", permission.toString())
                    .replace("%val%", permission.isActiveToString()));
        }
    }
}
