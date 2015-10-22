package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.selection.define.Point;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lobby extends Selection {
    private List<Arena> possibleArenas = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private World world;
    private int game;

    protected Lobby(World world) {
        String worldName = world.getName();
        String configPrefix = "Selections." + worldName;
        Main plugin = Main.getInstance();
        Set<String> arenaNames = plugin.getConfig().getConfigurationSection(configPrefix).getKeys(false);

        for (String arenaName : arenaNames) {
            int p1x = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point1.x");
            int p1z = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point1.z");
            int p2x = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point2.x");
            int p2z = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point2.z");
            Point point1 = new Point(p1x, p1z, worldName);
            Point point2 = new Point(p2x, p2z, worldName);
            if (arenaName.equals("LOBBY")) {
                if (plugin.getConfig().isSet(configPrefix + "." + arenaName + ".spawn")) {
                    Location spawn = new SpawnData(configPrefix + "." + arenaName + ".spawn").getLocation();
                    set(point1, point2, spawn);
                    this.world = world;
                } else {
                    plugin.getLogger().severe("No spawn set for lobby in world " + worldName);
                    return;
                }
            } else {
                // TODO: get all spawns for arenas from config.
                // possibleArenas.add(new Arena(arenaName, point1, point2, spawns));
            }
        }
    }


    protected List<Player> getPlayers() {
        return players;
    }
}