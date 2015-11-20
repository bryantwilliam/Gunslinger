package com.gmail.gogobebe2.gunslinger.scoreboard;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public abstract class SingleScoreboardSection extends ScoreboardSection {
    private Score label = null;

    public SingleScoreboardSection(Scoreboard scoreboard, Objective objective) {
        super(scoreboard, objective);
    }

    @Override
    public void display(Player player) {
        if (isLabelSet()) {
            getScoreboard().resetScores(label.getEntry());
        }
        super.display(player);
    }

    protected boolean isLabelSet() {
        return label != null;
    }

    protected void setLabel(String label, int index) {
        this.label = getObjective().getScore(label);
        this.label.setScore(index);
    }

    @Deprecated
    protected void setLabel(OfflinePlayer label, int index) {
        this.label = getObjective().getScore(label);
        this.label.setScore(index);
    }
}