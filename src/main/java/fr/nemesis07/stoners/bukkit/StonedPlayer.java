package fr.nemesis07.stoners.bukkit;

import fr.nemesis07.stoners.bukkit.languages.Lang;
import fr.nemesis07.stoners.bukkit.rank.Rank;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class StonedPlayer {

    private static final Map<UUID, StonedPlayer> stonedPlayer = new HashMap<>();
    private final Player player;
    private final OfflinePlayer offlinePlayer;
    private final PlayerData playerData;

    public StonedPlayer(UUID uuid) {
        this.player = Bukkit.getPlayer(uuid);
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        this.playerData = new PlayerData(player.getUniqueId());

        stonedPlayer.put(uuid, this);
    }

    public Lang getLang() {
        return Core.getInstance().getLanguageManager()
                .getLangs().stream()
                .filter(p -> p.getLanguage().equalsIgnoreCase(getLanguage()))
                .findFirst().orElse(null);
    }

    private String getName() {
        Rank r = Core.getInstance().getRankManager().getRankOfPlayer(player);
        return ChatColor.translateAlternateColorCodes('&', r.getRankPrefix() + player.getDisplayName() + r.getRankSuffix());
    }

    public static StonedPlayer getStonedPlayer(Player player) {
        return stonedPlayer.get(player.getUniqueId());
    }

    public static StonedPlayer getStonedPlayer(UUID uuid) {
        return stonedPlayer.get(uuid);
    }

    public boolean hasPermission(String permission) {
        return Core.getInstance().getPermissionManager().hasPermission(getPlayer(), permission);
    }

    public Integer getPing() {
        EntityPlayer ep = ((CraftPlayer)getPlayer()).getHandle();
        return Integer.valueOf(ep.ping);
    }

    public boolean isSprint() {
        EntityPlayer ep = ((CraftPlayer)getPlayer()).getHandle();
        return ep.isSprinting();
    }

    public boolean isSneaking() {
        EntityPlayer ep = ((CraftPlayer)getPlayer()).getHandle();
        return ep.isSneaking();
    }

    public void setVelocity(double x, double y, double z) {
        getPlayer().setVelocity(new Vector(x, y, z));
    }

    public boolean inventoryHasEmptySlot() {
        ItemStack[] arItemStacks;
        int j = (arItemStacks = getPlayer().getInventory().getContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack items = arItemStacks[i];
            if(items == null) {
                return true;
            }
        }
        return false;
    }

    public Date getFirstJoin() { return playerData.getFirstJoin(); }

    public Duration getTimeBetweenLastNow() {
        LocalDateTime date1 = playerData.getLastSeen().toLocalDateTime();
        LocalDateTime date2 = LocalDateTime.now();
        return Duration.between(date1, date2);
    }

    public void setLastSeen(Date date) { playerData.setLastSeen(new java.sql.Timestamp(date.getTime())); }

    public long getPlayedTime() { return player.getPlayerTime(); }

    public String getLanguage() {
        return playerData.getLanguage();
    }

    public void setLanguage(String lang) {
        playerData.setLanguage(lang);
    }

    public int getCoins() {
        return playerData.getCoins();
    }

    public void addCoins(int amount) {
        playerData.addCoins(amount);
    }

    public void remCoins(int amount) {
        playerData.removeCoins(amount);
    }

    public int getKarma() {
        return playerData.getKarma();
    }

    public void addKarma(int amount) {
        playerData.addKarma(amount);
    }

    public void remKarma(int amount) {
        playerData.removeKarma(amount);
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void openInventory(Inventory inventory) {
        getPlayer().playSound(getPlayer().getLocation(), Sound.CHEST_OPEN, 10, 0);
        getPlayer().openInventory(inventory);
    }

    public void closeInventory() {
        getPlayer().playSound(getPlayer().getLocation(), Sound.CHEST_CLOSE, 10, 0);
        getPlayer().closeInventory();
    }

    public UUID getUUID() {
        return getPlayer().getUniqueId();
    }
}
