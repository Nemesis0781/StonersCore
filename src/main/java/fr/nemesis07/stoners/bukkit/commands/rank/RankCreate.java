package fr.nemesis07.stoners.bukkit.commands.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.SubCommand;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import fr.nemesis07.stoners.bukkit.rank.RankManager;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

public class RankCreate extends SubCommand {

    private Lang lang = Core.getInstance().getDefaultLang();

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "rank.create";
    }

    @Override
    public String getDescription() {
        return lang.RankCreateDescription().getValue();
    }

    @Override
    public String getSyntax() {
        return lang.RankCreateSyntax().getValue();
    }

    @Override
    public void perform(Player p, String[] args) {
        // TODO Player has permission ?
        //if(p.hasPermission(""))
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();
        //this.lang = stonedPlayer.getLang();
        // rank create name power(int) prefix suffix
        if(args.length < 5) {
            stonedPlayer.sendMessage(getSyntax());
            return;
        }
        int power;
        try {
            power = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            stonedPlayer.sendMessage(lang.RankErrorPowerNotInteger().getValue().replace("%args%", args[2]));
            return;
        }
        if(power < 0) {
            stonedPlayer.sendMessage(lang.RankErrorPowerNotPositive().getValue().replace("%power%", args[2]));
            return;
        }
        int lengths = args[3].length() + args[4].length();
        if(lengths >= 32) {
            stonedPlayer.sendMessage(lang.RankErrorPrefixAndSuffixTooLong().getValue()
                    .replace("%length%", lengths+""));
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank createdRank = new Rank(args[1], power, args[3], args[4]);
        rankManager.createRank(createdRank);
        stonedPlayer.sendMessage(lang.RankCreateSuccess().getValue()
                .replace("%rank%", createdRank.toString()));
    }
}
