package com.gmail.gogobebe2.gunslinger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
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
            HumanEntity clicker = event.getWhoClicked();
        }
    }

    @EventHandler
    protected static void onInventoryDragEvent(InventoryMoveItemEvent event) {
        if (event.getDestination().getName().equals(GUI_NAME)) event.setCancelled(true);

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
        player.openInventory(inventory);
    }
}