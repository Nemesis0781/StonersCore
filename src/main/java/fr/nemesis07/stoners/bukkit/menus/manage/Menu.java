package fr.nemesis07.stoners.bukkit.menus.manage;

import fr.nemesis07.stoners.bukkit.Core;
import fr.nemesis07.stoners.bukkit.StonedPlayer;
import fr.nemesis07.stoners.bukkit.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class Menu implements InventoryHolder, Listener {

    private final UUID uuid;
    private StonedPlayer stonedPlayer;
    protected Inventory inventory;
    protected final ItemStack FILLER_GLASS = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 7).setName(" ").toIT();
    protected final ItemBuilder VALIDATE = new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 13);
    protected final ItemBuilder CLOSE = new ItemBuilder(Material.BARRIER, 1);

    int[][] borders = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 25, 23, 22, 21, 20, 19, 18, 9}
            , {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 34, 33, 32, 31, 30, 29, 28, 27, 18, 9}
            , {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 44, 43, 42, 41, 40, 39, 38, 37, 36, 27, 18, 9}
            , {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 44, 53, 52, 51, 50, 49, 48, 47, 46, 45, 36, 27, 18, 9}};

    public Menu(UUID uuid) {
        this.uuid = uuid;
        stonedPlayer = StonedPlayer.getStonedPlayer(uuid);
        Bukkit.getPluginManager().registerEvents(this, Core.getInstance());
        VALIDATE.setName(stonedPlayer.getLang().ItemValidateName().getValue());
        Bukkit.getPluginManager().registerEvents(this, Core.getInstance());
    }

    public abstract String getName();

    public abstract int getSize();

    @EventHandler
    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSize(), ChatColor.translateAlternateColorCodes('&', getName()));

        this.setMenuItems();
        stonedPlayer.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void fillNullSlot(){
        for (int i = 0; i < getSize(); i++) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, FILLER_GLASS);
        }
    }

    public void fillBorder(){
        if ((getSize() / 9) < 3) return;
        int[] border = borders[getSize() / 9 - 3];
        for (int i = 0; i < border.length; i++) {
            if(inventory.getItem(i) == null)
                inventory.setItem(border[i], FILLER_GLASS);
        }
    }

    public void fillDelimitation(){
        if(getSize() < 54) return;
        for (int i = 45; i < getSize(); i++) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, FILLER_GLASS);
        }
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;

            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }

    }

}
