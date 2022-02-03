package fr.nemesis07.stoners.bukkit.menus.rank;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.menus.manage.Menu;
import fr.nemesis07.stoners.bukkit.utils.AnvilGUI;
import fr.nemesis07.stoners.bukkit.utils.Color;
import fr.nemesis07.stoners.bukkit.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RankCreateMenu extends Menu {

    //TODO MODIFIER TOUTE VARIABLE ET LES RECUPERER DANS LE FR ou US

    private final StonedPlayer stonedPlayer;
    private final FileConfiguration config;

    private final ItemStack detail;
    private final Map<String, String> placeHolder;

    public RankCreateMenu(UUID uuid) {
        super(uuid);
        this.stonedPlayer = StonedPlayer.getStonedPlayer(uuid);
        this.config = Core.getInstance().getConfig();
        this.placeHolder = new HashMap<>();
        placeHolder();

        if(config.getInt("menu.rank.create.detail.color") == -1)
            this.detail = new ItemBuilder(Material.valueOf(config.getString("menu.rank.create.detail.material").toUpperCase()))
                    .setName(stonedPlayer.getLang().MenuRankCreateItem_RankDetail_Name().getValue())
                    .setLore(placeHolder(stonedPlayer.getLang().MenuRankCreateItem_RankDetail_Lore().getValue())).toIT();
        else
            this.detail = new ItemBuilder(Material.valueOf(config.getString("menu.rank.create.detail.material").toUpperCase()),
                    1, (byte) config.getInt("menu.rank.create.detail.color"))
                .setName(stonedPlayer.getLang().MenuRankCreateItem_RankDetail_Name().getValue())
                .setLore(placeHolder(stonedPlayer.getLang().MenuRankCreateItem_RankDetail_Lore().getValue()))
                .toIT();

    }

    private void placeHolder() {
        placeHolder.put("%rankName%", "N/A");
        placeHolder.put("%power%", "N/A");
        placeHolder.put("%prefix%", "N/A");
        placeHolder.put("%suffix%", "N/A");
    }

    @Override
    public String getName() {
        return stonedPlayer.getLang().MenuRankCreateName().getValue();
    }

    @Override
    public int getSize() {
        return config.getInt("menu.rank.create.size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch(e.getSlot()) {
            case 20:
                rankName();
                break;
            case 11:
                rankPower();
                break;
            case 12:
            case 13:
            case 14:
            case 16:
                break;
            default: break;
        }
    }

    @Override
    public void setMenuItems() {
        this.inventory.setItem(config.getInt("menu.rank.create.detail.slot"), detail);
        this.inventory.setItem(config.getInt("menu.rank.create.setName.slot"), detail);
        this.inventory.setItem(24, new ItemBuilder(Material.REDSTONE).toIT());
        this.inventory.setItem(30, new ItemBuilder(Material.PUMPKIN).toIT());
        this.inventory.setItem(32, new ItemBuilder(Material.PUMPKIN_STEM).toIT());

        this.inventory.setItem(inventory.getSize()-1, new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 13).toIT());
        this.inventory.setItem(36, CLOSE.toIT());

        this.fillNullSlot();
    }

    private void rankName() {
        stonedPlayer.getPlayer().setLevel(stonedPlayer.getPlayer().getLevel()+1);
        boolean[] bool = new boolean[1];
        String[] str = new String[1];
        AnvilGUI gui = new AnvilGUI(Core.getInstance(), stonedPlayer.getPlayer(), new AnvilGUI.AnvilClickEventHandler() {
            @Override
            public void onAnvilClick(AnvilGUI.AnvilClickEvent e) {
                bool[0] = e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT;
                if(bool[0]) {
                    placeHolder.replace("%rankName%", e.getRenameText());
                }
            }
        });
        try {
            gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, new ItemStack(Material.PAPER));
            gui.open();
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
            ex.printStackTrace();
        }

        if(bool[0]) {
            this.open();
        } else stonedPlayer.getPlayer().setLevel(stonedPlayer.getPlayer().getLevel()-1);
    }

    private void rankPower() {
    }

    public String placeHolder(String s) {
        for(String key: placeHolder.keySet()) {
            if(s.contains(key)) {
                s.replace(key, placeHolder.get(key));
            }
        }
        return s;
    }

    public List<String> placeHolder(List<String> s) {
        for(String key: placeHolder.keySet()) {
            s.replaceAll(r -> r.replace(key, placeHolder.get(key)));
        }
        return s;
    }
}
