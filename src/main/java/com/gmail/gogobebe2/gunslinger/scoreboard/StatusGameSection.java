package com.gmail.gogobebe2.gunslinger.scoreboard;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class StatusGameSection extends StatusSection {
    public StatusGameSection(Scoreboard scoreboard, Objective objective) {
        super(scoreboard, objective);
    }

    @Override
    protected String getStatusMessage() {
        return null;
    }
}
