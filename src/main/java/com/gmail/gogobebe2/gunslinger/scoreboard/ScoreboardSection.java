package com.gmail.gogobebe2.gunslinger.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public abstract class ScoreboardSection {
    private Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardSection(Scoreboard scoreboard, Objective objective) {
        this.scoreboard = scoreboard;
        this.objective = objective;
    }

    public void display(Player player) {
        arrangeSection();
        player.setScoreboard(scoreboard);
    }

    protected abstract void arrangeSection();

    protected Scoreboard getScoreboard() {
        return scoreboard;
    }

    protected Objective getObjective() {
        return objective;
    }
}
