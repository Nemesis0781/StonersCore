package fr.nemesis07.stoners.bukkit.menus.manage;

import fr.nemesis07.stoners.bukkit.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class StoreMenu extends Menu {

    protected final ItemStack MORE_ONE = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 5).toIT();
    protected final ItemStack LESS_ONE = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 14).toIT();
    protected final ItemStack MORE_TEN = new ItemBuilder(Material.STAINED_GLASS_PANE, 10, (byte) 5).toIT();
    protected final ItemStack LESS_TEN = new ItemBuilder(Material.STAINED_GLASS_PANE, 10, (byte) 14).toIT();
    protected final ItemStack MAX = new ItemBuilder(Material.STAINED_GLASS_PANE, 100, (byte) 4).toIT();

    public StoreMenu(UUID uuid) {
        super(uuid);
    }


}
