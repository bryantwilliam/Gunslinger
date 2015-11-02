package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.selection.Lobby;
import com.gmail.gogobebe2.gunslinger.selection.define.SpawnData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements Listener {
    private List<Lobby> lobbies = new ArrayList<>();

    @EventHandler
    protected void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(new SpawnData("Selections." + player.getWorld().getName() + ".SPAWN").loadFromConfig());
        player.getInventory().addItem(LobbyChooser.ITEM);
        LobbyChooser.openGUI(player);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Right click your lobby chooser to choose a lobby.");
    }
}
