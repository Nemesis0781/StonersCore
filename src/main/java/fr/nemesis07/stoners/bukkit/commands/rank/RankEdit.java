package fr.nemesis07.stoners.bukkit.commands.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class RankEdit extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getPermission() {
        return "rank.edit";
    }

    @Override
    public String getDescription() {
        return lang.RankEditDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.RankEditSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        // TODO Player has permission ?
        //if(p.hasPermission(""))
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //this.lang = stonedPlayer.getLang();
        // rank edit <name/power/prefix/suffix> rankName newValue
        if(args.length < 4) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank oldRank = rankManager.getRankByName(args[2]);
        if(oldRank == null) {
            stonedPlayer.sendMessage(lang.RankErrorRankNotExist().getValue().replace("%name%", args[2]));
            return;
        }
        if(args[1].equalsIgnoreCase("name"))
            args[1] = "rank_name";

        if(args[1].equalsIgnoreCase("rank_name")
                || args[1].equalsIgnoreCase("prefix") || args[1].equalsIgnoreCase("suffix")) {

            rankManager.editRank(oldRank, args[1].toLowerCase(), args[3]);
        } else if(args[1].equalsIgnoreCase("power")) {
            int power;
            try {
                power = Integer.parseInt(args[3]);
                if(power < 0) {
                    stonedPlayer.sendMessage(lang.RankErrorPowerNotInteger().getValue().replace("%power%", args[1]));
                    return;
                }

                rankManager.editRank(oldRank, args[1].toLowerCase(), args[3]);
            } catch (NumberFormatException e) {
                stonedPlayer.sendMessage(lang.RankErrorPowerNotInteger().getValue().replace("%args%", args[1]));
            }
        } else {
            stonedPlayer.sendMessage(lang.RankErrorUnknownArgs().getValue().replace("%args%", args[1]));
            return;
        }

        Rank newRank;
        if(args[1].equalsIgnoreCase("rank_name"))
            newRank = rankManager.getRankByName(args[3]);
        else newRank = rankManager.getRankByName(args[2]);
        stonedPlayer.sendMessage(lang.RankEditSuccess().getValue()
                .replace("%oldrank%", oldRank.toString())
                .replace("%newrank%", newRank.toString()));
    }
}
