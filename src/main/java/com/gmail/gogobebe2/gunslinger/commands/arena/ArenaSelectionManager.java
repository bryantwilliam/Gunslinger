package com.gmail.gogobebe2.gunslinger.commands.arena;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ArenaSelectionManager {
    private final static SelectionListener SELECTION_LISTENER = new SelectionListener();

    private Player player;
    private Point point1;
    private Point point2;

    protected ArenaSelectionManager(Player player) {
        this.player = player;
    }

    protected void setPoint(Action action, int x, int z) {
        if (action == Action.LEFT_CLICK_BLOCK) {
            if (point1 == null) point1 = new Point();
            point1.x = x;
            point1.z = z;
        } else {
            if (point2 == null) point2 = new Point();
            point2.x = x;
            point2.z = z;
        }
    }

    protected void confirmSelection() throws IllegalStateException {
        if (point1 == null) throw new IllegalStateException("Point 1 has not been selected yet!");
        if (point2 == null) throw new IllegalStateException("Point 2 has not been selected yet!");
        // TODO
    }

    private class Point {
        private int x;
        private int z;
    }

    public static Listener getListener() {
        return SELECTION_LISTENER;
    }

    private static class SelectionListener implements Listener {
        @EventHandler
        protected void onSelect(PlayerInteractEvent event) {
            Action action = event.getAction();
            // TODO test if they have wand.
            if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                new ArenaSelectionManager(event.getPlayer()).setPoint(action, event.getClickedBlock().getX(), event.getClickedBlock().getZ());
                event.setCancelled(true);
            }
        }
    }
}
