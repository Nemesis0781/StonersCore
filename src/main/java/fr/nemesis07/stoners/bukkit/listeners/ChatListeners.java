package fr.nemesis07.stoners.bukkit.listeners;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListeners implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Rank r = Core.getInstance().getRankManager().getRankOfPlayer(p);
        e.setFormat(ChatColor.translateAlternateColorCodes('&', r.getRankPrefix() + p.getDisplayName() + r.getRankSuffix()) + " " + e.getMessage());
    }
}
