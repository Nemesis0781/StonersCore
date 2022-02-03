package fr.nemesis07.stoners.bukkit.commands.permission;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.PermCMDManager;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class PermissionHelp extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();
    private final PermCMDManager permCMDManager;

    public PermissionHelp(PermCMDManager permCMDManager) {
        this.permCMDManager = permCMDManager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "permission.help";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public void perform(Player p, String[] args) {
        // TODO Player has permission ?
        //if(p.hasPermission(""))
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();

        String header = lang.help().getValue().get(0);
        String footer = lang.help().getValue().get(lang.help().getValue().size()-1);
        stonedPlayer.sendMessage(header);

        for (int i = 0; i < permCMDManager.getSubCommands().size(); i++) {

            SubCommand sub = permCMDManager.getSubCommands().get(i);

            for (int j = 1; j < lang.help().getValue().size()-1; j++) {
                String line = lang.PermissionListSuccess().getValue().get(j);
                stonedPlayer.sendMessage(line
                        .replace("%command%", permCMDManager.CMD)
                        .replace("%arg%", sub.getName())
                        .replace("%desc%", sub.getDescription()));
            }

        }

        stonedPlayer.sendMessage(footer);
    }
}
