package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.selection.define.SpawnData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerManager {
    private static Set<PlayerManager> playerManagers = new HashSet<>();
    private String kitName = null;
    private UUID uuid;

    private PlayerManager(UUID uuid) {
        this.uuid = uuid;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    public static PlayerManager getPlayerManager(UUID uuid) throws NullPointerException {
        for (PlayerManager playerManager : playerManagers) if (playerManager.uuid.equals(uuid)) return playerManager;
        throw new NullPointerException("No player data for that uuid");
    }

    protected static class PlayerListener implements Listener {
        @EventHandler
        private static void onPlayerJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            playerManagers.add(new PlayerManager(player.getUniqueId()));

            player.teleport(new SpawnData("Selections." + player.getWorld().getName() + ".SPAWN").loadFromConfig());
            player.getInventory().addItem(LobbyChooser.ITEM);
            LobbyChooser.openGUI(player);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Right click your lobby chooser to choose a lobby.");
        }
    }
}
