package fr.nemesis07.stoners.bukkit.menus.manage;

import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.UUID;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int index = 0;
    private final StonedPlayer stonedPlayer;

    public PaginatedMenu(UUID uuid) {
        super(uuid);
        stonedPlayer = StonedPlayer.getStonedPlayer(uuid);
    }

    public void addMenuBorder(){
        if(page != 0) inventory.setItem(48, new ItemBuilder(Material.ARROW).setName(stonedPlayer.getLang().ItemPreviousName().getValue()).toIT());
        inventory.setItem(48, this.CLOSE.toIT());
        if(page > 0) inventory.setItem(48, new ItemBuilder(Material.ARROW).setName(stonedPlayer.getLang().ItemPreviousName().getValue()).toIT());

    }

}
