package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.Timer;
import com.gmail.gogobebe2.gunslinger.selection.define.Point;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lobby extends Selection {
    private List<Arena> possibleArenas = new ArrayList<>();
    private Set<Player> players = new HashSet<>();
    private World world;
    private LobbyState state = LobbyState.WAITING;
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private LobbyTimer timer;
    private static final short MIN_PLAYERS = 3;
    private static final String STARTING_TIME = "1:30";

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
                } else plugin.getLogger().severe("No spawn set for arena " + arenaName + " in world " + worldName);
            }
        }
        timer = new LobbyTimer();
        lobbies.add(this);
    }


    protected boolean isPlayerInside(Player player) {
        return players.contains(player);
    }

    protected void join(Player player) {
        players.add(player);
    }

    protected void leave(Player player) {
        players.remove(player);
    }

    private enum LobbyState {WAITING, STARTING}

    public class LobbyTimer extends Timer {
        @Override
        protected void run() {
            // TODO: scoreboard shit
            if (state == LobbyState.STARTING) {
                if (isTimerRunning() && players.size() < MIN_PLAYERS) {
                    // TODO: Display on scoreboard ChatColor.RED + "---------"
                    state = LobbyState.WAITING;
                }
            } else {
                if (isTimerRunning() && players.size() >= MIN_PLAYERS) {
                    setTime(STARTING_TIME);
                    state = LobbyState.STARTING;
                }
            }
        }

        @Override
        protected void pause() {
            if (state == LobbyState.WAITING) {
                setTime(Integer.MAX_VALUE + ":");
                start();
            }
            else {
                //  TODO: START GAME
            }
        }
    }
}