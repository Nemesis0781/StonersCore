package fr.nemesis07.stoners.bukkit.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by chasechocolate.
 */
public class AnvilGUI {
    private Player player;
    private AnvilClickEventHandler handler;
    private static Class<?> BlockPosition;
    private static Class<?> PacketPlayOutOpenWindow;
    private static Class<?> ContainerAnvil;
    private static Class<?> ContainerAccess;
    private static Class<?> ChatMessage;
    private static Class<?> EntityHuman;
    private HashMap<AnvilSlot, ItemStack> items = new HashMap<AnvilSlot, ItemStack>();
    private Inventory inv;
    private Listener listener;

    private void loadClasses() {
        BlockPosition = NMSManager.get().getNMSClass("BlockPosition");
        PacketPlayOutOpenWindow = NMSManager.get().getNMSClass("PacketPlayOutOpenWindow");
        ContainerAnvil = NMSManager.get().getNMSClass("ContainerAnvil");
        EntityHuman = NMSManager.get().getNMSClass("EntityHuman");
        ChatMessage = NMSManager.get().getNMSClass("ChatMessage");
        ContainerAccess = NMSManager.get().getNMSClass("ContainerAccess");
    }

    public AnvilGUI(JavaPlugin plugin, final Player player, final AnvilClickEventHandler handler) {
        loadClasses();
        this.player = player;
        this.handler = handler;

        this.listener = new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                if (event.getWhoClicked() instanceof Player) {

                    if (event.getInventory().equals(inv)) {

                        ItemStack item = event.getCurrentItem();
                        int slot = event.getRawSlot();
                        String name = "";

                        if (item != null) {
                            if (item.hasItemMeta()) {
                                ItemMeta meta = item.getItemMeta();

                                if (meta.hasDisplayName()) {
                                    name = meta.getDisplayName();
                                }
                            }
                        }

                        AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name,
                                getItemNameText());

                        handler.onAnvilClick(clickEvent);
                        if(clickEvent.isCanceled) {
                            event.setCancelled(true);
                        }

                        if (clickEvent.getWillClose()) {
                            event.getWhoClicked().closeInventory();
                        }

                        if (clickEvent.getWillDestroy()) {
                            destroy();
                        }
                    }
                }
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if (event.getPlayer() instanceof Player) {
                    Inventory inv = event.getInventory();
                    if (inv.equals(AnvilGUI.this.inv)) {
                        inv.clear();
                        destroy();
                    }
                }
            }
        };

        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public Player getPlayer() {
        return player;
    }

    public void setSlot(AnvilSlot slot, ItemStack item) {
        items.put(slot, item);
    }

    public void open() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Object p = NMSManager.get().getHandle(player);
            Object container = null;
            Method containerAccessAt = null;
            switch (NMSManager.get().getVersion()) {
                case "v1_14_R1":
                    containerAccessAt = NMSManager.get().getMethod(ContainerAccess, "at",
                            NMSManager.get().getNMSClass("World"), BlockPosition);

                    container = ContainerAnvil
                            .getConstructor(int.class, NMSManager.get().getNMSClass("PlayerInventory"), ContainerAccess)

                            .newInstance(1, NMSManager.get().getPlayerField(player, "inventory"),

                                    containerAccessAt.invoke(null, NMSManager.get().getPlayerField(player, "world"),
                                            BlockPosition.getConstructor(int.class, int.class, int.class).newInstance(0, 0,
                                                    0)));
                default:
                    containerAccessAt = NMSManager.get().getMethod(ContainerAccess, "at",
                            NMSManager.get().getNMSClass("World"), BlockPosition);

                    container = ContainerAnvil
                            .getConstructor(int.class, NMSManager.get().getNMSClass("PlayerInventory"), ContainerAccess)

                            .newInstance(1, NMSManager.get().getPlayerField(player, "inventory"),

                                    containerAccessAt.invoke(null, NMSManager.get().getPlayerField(player, "world"),
                                            BlockPosition.getConstructor(int.class, int.class, int.class).newInstance(0, 0,
                                                    0)));
            }
            NMSManager.get().getField(NMSManager.get().getNMSClass("Container"), "checkReachable").set(container,
                    false);

            // Set the items to the items from the inventory given
            Object bukkitView = NMSManager.get().invokeMethod("getBukkitView", container);
            inv = (Inventory) NMSManager.get().invokeMethod("getTopInventory", bukkitView);

            for (AnvilSlot slot : items.keySet()) {
                inv.setItem(slot.getSlot(), items.get(slot));
            }

            // Counter stuff that the game uses to keep track of inventories
            int c = (int) NMSManager.get().invokeMethod("nextContainerCounter", p);

            // Send the packet
            Object playerConnection = null;
            Object packet = null;
            switch (NMSManager.get().getVersion()) {
                case "v1_14_R1":
                    Constructor<?> chatMessageConstructor = ChatMessage.getConstructor(String.class, Object[].class);
                    playerConnection = NMSManager.get().getPlayerField(player, "playerConnection");
                    packet = PacketPlayOutOpenWindow.getConstructor(int.class, NMSManager.get().getNMSClass("Containers"),
                            NMSManager.get().getNMSClass("IChatBaseComponent")).newInstance(c, NMSManager.get().getNMSClass("Containers").getField("ANVIL").get(null),
                            chatMessageConstructor.newInstance("Repairing", new Object[] {}));
                default:
                    chatMessageConstructor = ChatMessage.getConstructor(String.class, Object[].class);
                    playerConnection = NMSManager.get().getPlayerField(player, "playerConnection");
                    packet = PacketPlayOutOpenWindow.getConstructor(int.class, NMSManager.get().getNMSClass("Containers"),
                            NMSManager.get().getNMSClass("IChatBaseComponent")).newInstance(c, NMSManager.get().getNMSClass("Containers").getField("ANVIL").get(null),
                            chatMessageConstructor.newInstance("Repairing", new Object[] {}));

            }
            Method sendPacket = NMSManager.get().getMethod("sendPacket", playerConnection.getClass(),
                    PacketPlayOutOpenWindow);
            sendPacket.invoke(playerConnection, packet);

            // Set their active container to the container
            Field activeContainerField = NMSManager.get().getField(EntityHuman, "activeContainer");
            if (activeContainerField != null) {
                activeContainerField.set(p, container);

                // Set their active container window id to that counter stuff

                NMSManager.get().getField(NMSManager.get().getNMSClass("Container"), "windowId")
                        .set(activeContainerField.get(p), c);

                // Add the slot listener
                NMSManager.get().getMethod("addSlotListener", activeContainerField.get(p).getClass(), p.getClass())
                        .invoke(activeContainerField.get(p), p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        player = null;
        handler = null;
        items = null;

        HandlerList.unregisterAll(listener);

        listener = null;
    }

    public String getItemNameText() {
        // field: repairedItemName
        Object p = NMSManager.get().getHandle(player);

        Field activeContainerField = NMSManager.get().getField(EntityHuman, "activeContainer");
        if (activeContainerField != null) {
            try {
                Field renameText = NMSManager.get().getField(ContainerAnvil, "renameText");
                if (renameText != null) {
                    return (String) renameText.get(activeContainerField.get(p));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public enum AnvilSlot {
        INPUT_LEFT(0), INPUT_RIGHT(1), OUTPUT(2);

        private int slot;

        private AnvilSlot(int slot) {
            this.slot = slot;
        }

        public static AnvilSlot bySlot(int slot) {
            for (AnvilSlot anvilSlot : values()) {
                if (anvilSlot.getSlot() == slot) {
                    return anvilSlot;
                }
            }

            return null;
        }

        public int getSlot() {
            return slot;
        }
    }

    public interface AnvilClickEventHandler {
        void onAnvilClick(AnvilClickEvent event);
    }

    public class AnvilClickEvent {
        private AnvilSlot slot;

        private String name;

        private String renameText;

        private boolean close = false;
        private boolean destroy = false;

        public boolean isCanceled;

        public AnvilClickEvent(AnvilSlot slot, String name, String renameText) {
            this.slot = slot;
            this.name = name;
            this.renameText = renameText;
        }

        public String getRenameText() {
            return renameText;
        }

        public AnvilSlot getSlot() {
            return slot;
        }

        public String getName() {
            return name;
        }

        public boolean getWillClose() {
            return close;
        }

        public void setWillClose(boolean close) {
            this.close = close;
        }

        public boolean getWillDestroy() {
            return destroy;
        }

        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
}