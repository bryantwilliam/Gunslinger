package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.selection.define.Point;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class Selection {
    private Point point1;
    private Point point2;
    private Set<Location> spawns = new HashSet<>();

    protected Point getPoint1() {
        return point1;
    }

    protected Point getPoint2() {
        return point2;
    }

    protected Set<Location> getSpawns() {
        return spawns;
    }

    protected void set(Point point1, Point point2, Location... spawns) {
        this.point1 = point1;
        this.point2 = point2;
        this.spawns.addAll(Arrays.asList(spawns));
    }
}
