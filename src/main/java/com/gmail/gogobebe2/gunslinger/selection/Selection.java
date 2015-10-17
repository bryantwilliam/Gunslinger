package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.selection.define.Point;
import org.bukkit.Location;

public abstract class Selection {
    private Point point1;
    private Point point2;
    private Location spawn;

    protected Point getPoint1() {
        return point1;
    }

    protected Point getPoint2() {
        return point2;
    }

    protected Location getSpawn() {
        return spawn;
    }

    protected void set(Point point1, Point point2, Location spawn) {
        this.point1 = point1;
        this.point2 = point2;
        this.spawn = spawn;
    }
}
