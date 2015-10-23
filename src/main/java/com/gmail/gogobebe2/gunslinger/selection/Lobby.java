package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.selection.define.Point;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lobby extends Selection {
    private List<Arena> possibleArenas = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private World world;
    private LobbyState state = LobbyState.WAITNG;

    private static List<Lobby> lobbies = new ArrayList<>();

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
                if (plugin.getConfig().isSet(configPrefix + ".LOBBY.spawn")) {
                    Location spawn = new SpawnData(configPrefix + ".LOBBY.spawn").getLocation();
                    set(point1, point2, spawn);
                    this.world = world;
                } else plugin.getLogger().severe("No spawn set for lobby in world " + worldName);
            } else {
                if (plugin.getConfig().isSet(configPrefix + "." + arenaName + ".spawns")) {
                    Set<Location> spawns = new HashSet<>();
                    for (String id : plugin.getConfig().getConfigurationSection(configPrefix + "." + arenaName).getKeys(false))
                        spawns.add(new SpawnData(configPrefix + "." + arenaName + "." + id).getLocation());
                    possibleArenas.add(new Arena(arenaName, point1, point2, spawns.toArray(new Location[spawns.size()])));
                }
                else plugin.getLogger().severe("No spawn set for arena " + arenaName + " in world " + worldName);
            }
        }
        lobbies.add(this);
    }

    protected List<Player> getPlayers() {
        return players;
    }

    private enum LobbyState {WAITNG, VOTING, STARTING}
}