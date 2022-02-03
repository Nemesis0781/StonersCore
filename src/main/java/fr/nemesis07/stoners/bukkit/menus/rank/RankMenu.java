package fr.nemesis07.stoners.bukkit.menus.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.commands.RankCMDManager;
import fr.nemesis07.stoners.bukkit.menus.manage.Menu;
import fr.nemesis07.stoners.bukkit.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class RankMenu extends Menu {

    private final StonedPlayer stonedPlayer;
    private final RankCMDManager rankCMDManager;
    private final FileConfiguration config;

    private final ItemStack create;

    //TODO MODIFIER TOUTE VARIABLE ET LES RECUPERER DANS LE FR ou US

    public RankMenu(UUID uuid, RankCMDManager rankCMDManager) {
        super(uuid);
        this.rankCMDManager = rankCMDManager;
        this.stonedPlayer = StonedPlayer.getStonedPlayer(uuid);
        this.config = Core.getInstance().getConfig();

        create = new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 13)
                //.setName(stonedPlayer.getLang().MenuRankMainCreate)
                .toIT();
    }

    @Override
    public String getName() {
        return stonedPlayer.getLang().MenuRankMainName().getValue();
    }

    @Override
    public int getSize() {
        return config.getInt("menu.rank.main.size");
    }

    @Override
    public void setMenuItems() {
        this.inventory.setItem(10, new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 13).toIT());
        this.inventory.setItem(11, new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 4).toIT());
        this.inventory.setItem(12, new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14).toIT());
        this.inventory.setItem(13, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setSkullOwner(stonedPlayer.getPlayer().getName()).toIT());
        this.inventory.setItem(14, new ItemBuilder(Material.BOOK).toIT());

        this.inventory.setItem(16, CLOSE.toIT());

        this.fillNullSlot();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getWhoClicked() != stonedPlayer.getPlayer()) return;
        switch(e.getSlot()) {
            case 10:
                new RankCreateMenu(stonedPlayer.getUUID()).open();
                break;
            case 11:
                break;
            case 12:
            case 13:
            case 14:
            case 16:
                break;
            default: break;
        }
    }
}
