package fr.nemesis07.stoners.bukkit.commands.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.RankCMDManager;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class RankHelp extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();
    private final RankCMDManager rankCMDManager;

    public RankHelp(RankCMDManager rankCMDManager) {
        this.rankCMDManager = rankCMDManager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "rank.help";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getSyntax() { return ""; }

    @Override
    public void perform(Player p, String[] args) {
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();

        String header = lang.help().getValue().get(0);
        String footer = lang.help().getValue().get(lang.help().getValue().size()-1);
        stonedPlayer.sendMessage(header);

        for (int i = 0; i < rankCMDManager.getSubCommands().size(); i++) {

            SubCommand sub = rankCMDManager.getSubCommands().get(i);

            for (int j = 1; j < lang.help().getValue().size()-1; j++) {
                String line = lang.PermissionListSuccess().getValue().get(j);
                stonedPlayer.sendMessage(line
                        .replace("%command%", rankCMDManager.CMD)
                        .replace("%arg%", sub.getName())
                        .replace("%desc%", sub.getDescription()));
            }
        }

        stonedPlayer.sendMessage(footer);

    }
}
