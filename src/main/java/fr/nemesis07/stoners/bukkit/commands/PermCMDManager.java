package fr.nemesis07.stoners.bukkit.commands;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.permission.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.nemesis07.stoners.bukkit.languages.Lang;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PermCMDManager implements CommandExecutor {
    private ArrayList<SubCommand> subCommands = new ArrayList<>();
    private Lang lang;
    public final static String CMD = "permission";

    public PermCMDManager() {
        subCommands.add(new PermissionHelp(this));
        subCommands.add(new PermissionAdd());
        subCommands.add(new PermissionEdit());
        subCommands.add(new PermissionRemove());
        subCommands.add(new PermissionList());
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
            if (!(stonedPlayer.hasPermission("permission.*"))) {
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
            if(!(stonedPlayer.hasPermission("permission.*"))) {
                if(!(stonedPlayer.hasPermission("permission.help"))) {
                    return true;
                }
            }

            for (int i = 0; i < getSubCommands().size(); i++) {

                SubCommand sub = getSubCommands().get(i);
                for(String line: stonedPlayer.getLang().help().getValue()) {

                    stonedPlayer.sendMessage(line
                            .replace("%command%", label)
                            .replace("%arg%", sub.getName())
                            .replace("%desc%", sub.getDescription()));
                }
            }

        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
