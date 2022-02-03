package fr.nemesis07.stoners.bukkit.listeners;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.utils.SimpleScoreboard;
import fr.nemesis07.stoners.bukkit.utils.TablistUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class PlayerListeners implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Core.getInstance().getSQL().createAccount(p.getUniqueId());
        new StonedPlayer(p.getUniqueId());
        TablistUtils.setPlayerList(p,
                StonedPlayer.getStonedPlayer(p).getLang().TablistHeader().getValue(),
                StonedPlayer.getStonedPlayer(p).getLang().TablistFooter().getValue());

        if(Bukkit.getServer().getServerName().startsWith("lobby")) {
            SimpleScoreboard scoreboard = new SimpleScoreboard("&e&lSTONERS");
            scoreboard.add("&7"+Date.from(Instant.now()), 10);
            scoreboard.blankLine();
            scoreboard.add("&eNice", 8);
            scoreboard.blankLine();
            scoreboard.blankLine();
            scoreboard.add("&eStoners.net", 8);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        StonedPlayer.getStonedPlayer(p).setLastSeen(new Date());
    }

}
