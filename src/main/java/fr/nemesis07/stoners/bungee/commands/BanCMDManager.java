package fr.nemesis07.stoners.bungee.commands;

import fr.nemesis07.stoners.bungee.commands.ban.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;

public class BanCMDManager extends Command {

    private ArrayList<SubCommandB> subCommands = new ArrayList<>();

    public BanCMDManager(String name) {
        super(name);
        subCommands.add(new BanCommand());
        subCommands.add(new UnbanCommand());
        subCommands.add(new TempbanCommand());
        subCommands.add(new BanIpCommand());
        subCommands.add(new UnbanIpCommand());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;

        if(args.length > 0) {
            if (!(p.hasPermission("punishment.*"))) {
                for(SubCommandB subCommand: getSubCommands()) {
                    if(!p.hasPermission(subCommand.getPermission()))
                        p.sendMessage(new ComponentBuilder("Vous n'avez pas la permission !").color(ChatColor.RED).create());
                    return;
                }
            }
            for(SubCommandB subCommand: getSubCommands()) {
                if(args[0].equalsIgnoreCase(subCommand.getName())){
                    subCommand.perform(p, args);
                }
            }
        }
    }

    public ArrayList<SubCommandB> getSubCommands() {
        return subCommands;
    }
}
