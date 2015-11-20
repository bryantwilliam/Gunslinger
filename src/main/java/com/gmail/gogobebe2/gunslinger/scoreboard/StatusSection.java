package com.gmail.gogobebe2.gunslinger.scoreboard;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public abstract class StatusSection extends SingleScoreboardSection {
    public StatusSection(Scoreboard scoreboard, Objective objective) {
        super(scoreboard, objective);
    }

    @Override
    protected void arrangeSection() {
        setLabel(getStatusMessage(), 0);
    }

    protected abstract String getStatusMessage();
}
