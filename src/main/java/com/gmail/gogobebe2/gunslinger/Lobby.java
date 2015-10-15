package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.Arena;
import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.selection.Point;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lobby {
    private World world;
    private List<Arena> possibleArenas = new ArrayList<>();

    protected Lobby(World world) {
        this.world = world;
        String worldName = world.getName();
        String configPrefix = "Selections." + worldName;
        Main plugin = Main.getInstance();
        Set<String> arenaNames = plugin.getConfig().getConfigurationSection(configPrefix).getKeys(false);

        for (String arenaName : arenaNames) if (!arenaName.equals("LOBBY")) {
            int p1x = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point1.x");
            int p1z = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point1.z");
            int p2x = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point2.x");
            int p2z = plugin.getConfig().getInt(configPrefix + "." + arenaName + "point2.z");
            possibleArenas.add(new Arena(arenaName, new Point(p1x, p1z, worldName), new Point(p2x, p2z, worldName)));
        }
    }
}
