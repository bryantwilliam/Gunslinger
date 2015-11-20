package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.selection.define.Point;
import org.bukkit.Location;

public class Arena extends Selection {
    String arenaName;

    public Arena(String arenaName, Point point1, Point point2, Location... spawns) {
        set(point1, point2, spawns);
        this.arenaName = arenaName;
    }
}
