package fr.nemesis07.stoners.bungee.commands.ban;

import fr.nemesis07.stoners.bungee.BungeeCore;
import fr.nemesis07.stoners.bungee.commands.SubCommandB;
import fr.nemesis07.stoners.common.punishment.Punishment;
import fr.nemesis07.stoners.common.punishment.PunishmentType;
import kong.unirest.Callback;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Date;

public class BanCommand extends SubCommandB {

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getPermission() {
        return "staff.ban";
    }

    @Override
    public ComponentBuilder getDescription() {
        return new ComponentBuilder("Commande de bannissement permanent").color(ChatColor.YELLOW);
    }

    @Override
    public ComponentBuilder getSyntax() {
        return new ComponentBuilder("/ban <Name/UUID> <Reason>").color(ChatColor.YELLOW);
    }

    @Override
    public void perform(ProxiedPlayer p, String[] args) {
        if(args.length < 2) {
            p.sendMessage(getSyntax().create());
            return;
        }

        ProxiedPlayer target = BungeeCore.getInstance().getProxy().getInstance().getPlayer(args[0]);

        final String[] uuid = new String[1];
        Unirest.get("https://api.mojang.com/users/profiles/minecraft/"+ target.getName()).asJsonAsync(new Callback<JsonNode>() {
            @Override
            public void completed(HttpResponse<JsonNode> httpResponse) {
                uuid[0] = httpResponse.getBody().getObject().getString("id");

            }
        });

        if(args[2].isEmpty() || args[2] == null) args[2] = "none";

        if(!uuid[0].isEmpty() && uuid[0] != null) {
            BungeeCore.getInstance().getPunishmentManager().createPunishment(
                    new Punishment(target.getName(), uuid[0], args[2],
                            p.getName(), PunishmentType.BAN, new Date(new java.util.Date().getTime()),
                            null, 0));
        } else
            p.sendMessage(new ComponentBuilder("Erreur récupération uuid").color(ChatColor.RED).create());
        return;
    }
}
