package fr.nemesis07.stoners.bungee.listeners;

import fr.nemesis07.stoners.bungee.BungeeCore;
import fr.nemesis07.stoners.common.punishment.PunishmentManager;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PunishmentListeners implements Listener {

    @EventHandler
    public void preLoginEvent(PreLoginEvent e) {
        String name = e.getConnection().getName();
        UUID uuid = e.getConnection().getUniqueId();
        String ip = e.getConnection().getVirtualHost().getHostName();

        PunishmentManager manager = BungeeCore.getInstance().getPunishmentManager();
        if(manager.isBanned(name) || manager.isBanned(ip) || manager.isBanned(uuid)) {

        }

    }
}
