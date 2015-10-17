package com.gmail.gogobebe2.gunslinger.selection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectionListener implements Listener {
    private static boolean isWand(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            ItemMeta wandMeta = SelectionManager.WAND.getItemMeta();
            if (meta.hasLore() && meta.getLore().equals(wandMeta.getLore()) && meta.hasDisplayName()
                    && meta.getDisplayName().equals(wandMeta.getDisplayName())) return true;
        }
        return false;
    }
    @EventHandler
    private void onSelect(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (isWand(event.getItem()) && (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            SelectionManager selectionManager = null;
            for (SelectionManager manager : SelectionManager.selectionManagers)
                if (manager.getPlayer().getUniqueId().equals(player.getUniqueId())) selectionManager = manager;
            if (selectionManager == null)
                selectionManager = new SelectionManager(player);
            selectionManager.setPoint(action, block.getX(), block.getZ(), block.getWorld().getName());
            event.setCancelled(true);
        }
    }
}
