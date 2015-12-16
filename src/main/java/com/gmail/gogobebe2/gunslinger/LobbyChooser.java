package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.lobby.Lobby;
import com.gmail.gogobebe2.gunslinger.lobby.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LobbyChooser implements Listener {
    protected final static ItemStack ITEM = initItem();
    private final static String GUI_NAME = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Select Lobby";

    @EventHandler
    protected static void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        ItemStack item = event.getItem();
        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
            if (item != null && item.getType() == ITEM.getType() && item.getItemMeta().getLore() == ITEM.getItemMeta().getLore()) {
                openGUI(event.getPlayer());
            }
        }
    }

    @EventHandler
    protected static void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(GUI_NAME)) {
            event.setCancelled(true);
            Player clicker = (Player) event.getWhoClicked();
            ItemStack button = event.getClickedInventory().getItem(event.getSlot());
            if (button.getType() == Material.REDSTONE_BLOCK)
                clicker.sendMessage(ChatColor.RED + "Error! This lobby is inactive at the moment!");
            else {
                int lobbyID = Integer.parseInt(button.getItemMeta().getDisplayName().replace("Lobby ", ""));
                Lobby lobby = Lobby.getLobbies().get(lobbyID - 1);
                clicker.sendMessage(ChatColor.GREEN + "Joining lobby " + lobbyID + "...");
                lobby.join(clicker);
            }
        }
    }

    @EventHandler
    protected static void onInventoryDragEvent(InventoryMoveItemEvent event) {
        if (event.getDestination().getName().equals(GUI_NAME) || event.getSource().getName().equals(GUI_NAME)) event.setCancelled(true);
    }

    private static ItemStack initItem() {
        ItemStack item = new ItemStack(Material.ENDER_PORTAL_FRAME, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(GUI_NAME);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "Right click");
        lore.add(ChatColor.BLUE + "to select");
        lore.add(ChatColor.BLUE + "a lobby");
        return item;
    }

    protected static void openGUI(Player player) {
        player.sendMessage(ChatColor.GREEN + "Opening Lobby Selector GUI");
        Inventory inventory = Bukkit.createInventory(null, 9, GUI_NAME);
        int lobbyNumber = 1;
        for (Lobby lobby : Lobby.getLobbies()) {
            ItemStack button;
            if (lobby.getState().equals(LobbyState.INACTIVE) || lobby.isFull())
                button = new ItemStack(Material.REDSTONE_BLOCK, 1);
            else button = new ItemStack(Material.EMERALD_BLOCK, 1);
            ItemMeta meta = button.getItemMeta();
            meta.setDisplayName("Lobby " + lobbyNumber);
            button.setItemMeta(meta);
            inventory.addItem();
        }
        player.openInventory(inventory);
    }
}