package com.gmail.gogobebe2.gunslinger.lobby;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.scoreboard.StatusLobbySection;
import com.gmail.gogobebe2.gunslinger.selection.Arena;
import com.gmail.gogobebe2.gunslinger.selection.Selection;
import com.gmail.gogobebe2.gunslinger.selection.define.Point;
import com.gmail.gogobebe2.gunslinger.selection.define.SpawnData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
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
    private StatusLobbySection statusLobbySection;
    private LobbyTimer timer;
    protected static final short MIN_PLAYERS = 3;
    protected static final String STARTING_TIME = "1:30";
    protected static final String GAME_TIME = "15:00";

    private static List<Lobby> lobbies = new ArrayList<>();

    protected Lobby(World world) {
        Objective sideObjective = getObjective(scoreboard, "side_obj");
        sideObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.statusLobbySection = new StatusLobbySection(scoreboard, sideObjective, this);

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
                    Location spawn = new SpawnData(configPrefix + ".LOBBY.spawn").loadFromConfig();
                    set(point1, point2, spawn);
                    this.world = world;
                } else plugin.getLogger().severe("No spawn set for lobby in world " + worldName);
            } else {
                if (plugin.getConfig().isSet(configPrefix + "." + arenaName + ".spawns")) {
                    Set<Location> spawns = new HashSet<>();
                    for (String id : plugin.getConfig().getConfigurationSection(configPrefix + "." + arenaName).getKeys(false))
                        spawns.add(new SpawnData(configPrefix + "." + arenaName + "." + id).loadFromConfig());
                    possibleArenas.add(new Arena(arenaName, point1, point2, spawns.toArray(new Location[spawns.size()])));
                } else plugin.getLogger().severe("No spawn set for arena " + arenaName + " in world " + worldName);
            }
        }
        timer = new LobbyTimer(this);
        lobbies.add(this);
    }

    private Objective getObjective(Scoreboard scoreboard, String name) {
        if (!scoreboard.getObjectives().isEmpty()) {
            for (Objective objective : scoreboard.getObjectives()) {
                if (objective.getName().equals(name)) {
                    return scoreboard.getObjective(name);
                }
            }
        }
        return scoreboard.registerNewObjective(name, "dummy");
    }

    protected boolean isPlayerInside(Player player) {
        return players.contains(player);
    }

    protected void join(Player player) {
        players.add(player);
        player.setScoreboard(scoreboard);
    }

    protected void leave(Player player) {
        players.remove(player);
    }

    public LobbyState getState() {
        return state;
    }
    protected void setState(LobbyState state) {
        this.state = state;
    }

    protected short getPlayerAmount() {
        return (short) players.size();
    }

    public LobbyTimer getTimer() {
        return this.timer;
    }

    protected StatusLobbySection getStatusLobbySection() {
        return statusLobbySection;
    }
}