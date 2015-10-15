package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.selection.Point;

public class Arena {
    private String arenaName;
    private Point point1;
    private Point point2;

    protected Arena(String arenaName, Point point1, Point point2) {
        this.arenaName = arenaName;
        this.point1 = point1;
        this.point2 = point2;
    }
}
