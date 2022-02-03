package fr.nemesis07.stoners.bukkit.commands;

import fr.nemesis07.stoners.bukkit.StonedPlayer;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getPermission();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract void perform(Player p, String[] args);

}