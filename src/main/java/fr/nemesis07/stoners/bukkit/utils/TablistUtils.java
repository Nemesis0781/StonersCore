package fr.nemesis07.stoners.bukkit.utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class TablistUtils {

    static TablistUtils instance;
    public static TablistUtils getInstance() {
        return instance;
    }

    final static HashMap<String, Integer> count = new HashMap<>();

    public static void setPlayerList(Player p, String header, String footer) {
        header = ChatColor.translateAlternateColorCodes('&', header);
        footer = ChatColor.translateAlternateColorCodes('&', footer);
        IChatBaseComponent hj = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        IChatBaseComponent fj = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = (PacketPlayOutPlayerListHeaderFooter) constructHeaderAndFooterPacket(hj, fj);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static void setPlayerList(Player p, List<String> headerList, List<String> footerList) {
        String header = "", footer = "";

        for (int i = 0; i < headerList.size()-1; i++) {
            header += headerList.get(i)+"\n";
        }
        header += headerList.get(headerList.size()-1);

        footer += footerList.get(0);
        for (int i = 1; i < footerList.size(); i++) {
            footer += "\n"+footerList.get(i);
        }

        header = ChatColor.translateAlternateColorCodes('&', header);
        footer = ChatColor.translateAlternateColorCodes('&', footer);
        IChatBaseComponent hj = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        IChatBaseComponent fj = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = (PacketPlayOutPlayerListHeaderFooter) constructHeaderAndFooterPacket(hj, fj);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    private static Object constructHeaderAndFooterPacket(Object header, Object footer) {
        try {
            Object packet = PacketPlayOutPlayerListHeaderFooter.class.newInstance();
            if(header != null) {
                Field field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("a");
                field.setAccessible(true);
                field.set(packet, header);
                field.setAccessible(false);
            }
            if(footer != null) {
                Field field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
                field.setAccessible(true);
                field.set(packet, footer);
                field.setAccessible(false);
            }
            return packet;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title) {
        sendTitle(player, fadeIn, stay, fadeOut, title, null);
    }

    @Deprecated
    public void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String subtitle) {
        sendTitle(player, fadeIn, stay, fadeOut, null, subtitle);
    }

    @Deprecated
    public void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
                              String subtitle) {
        sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
                          String subtitle) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null,
                fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket(packetPlayOutTimes);
        if (subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(
                    PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            connection.sendPacket(packetPlayOutSubTitle);
        }
        if (title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                    titleMain);
            connection.sendPacket(packetPlayOutTitle);
        }
    }

    public void sendTabTitle(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes('&', header);
        if (footer == null) {
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }

    public void sendActionBar(Player player, String message){
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,(byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }

}
