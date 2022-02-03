package fr.nemesis07.stoners.bungee.commands;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class SubCommandB {

    public abstract String getName();
    public abstract String getPermission();
    public abstract ComponentBuilder getDescription();
    public abstract ComponentBuilder getSyntax();
    public abstract void perform(ProxiedPlayer p, String[] args);

}