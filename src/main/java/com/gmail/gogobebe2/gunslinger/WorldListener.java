package com.gmail.gogobebe2.gunslinger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldListener implements Listener {
    @EventHandler
    protected void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

    }
}
