package fr.nemesis07.stoners.bukkit.commands;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.rank.*;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import fr.nemesis07.stoners.bukkit.menus.rank.RankMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RankCMDManager implements CommandExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();
    private Lang lang;
    public final static String CMD = "rank";


    public RankCMDManager() {
        subCommands.add(new RankHelp(this));
        subCommands.add(new RankCreate());
        subCommands.add(new RankEdit());
        subCommands.add(new RankDelete());
        subCommands.add(new RankList());
        subCommands.add(new RankSet());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getDefaultLang().mustBePlayer().getValue()));
            return false;
        }

        Player p = (Player) sender;
        StonedPlayer stonedPlayer = StonedPlayer.getStonedPlayer(p);
        lang = stonedPlayer.getLang();


        if(args.length > 0) {
            if (!(stonedPlayer.hasPermission("rank.*"))) {
                for(SubCommand subCommand: getSubCommands()) {
                    if(!stonedPlayer.hasPermission(subCommand.getPermission()))
                        return true;
                }
            }
            for(SubCommand subCommand: getSubCommands()) {
                if(args[0].equalsIgnoreCase(subCommand.getName())){
                    subCommand.perform(p, args);
                }
            }
        } else {
            if(!(stonedPlayer.hasPermission("rank.*"))) {
                if(!(stonedPlayer.hasPermission("rank.help"))) {
                    return true;
                }
            }

            new RankMenu(p.getUniqueId(), this).open();

        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
