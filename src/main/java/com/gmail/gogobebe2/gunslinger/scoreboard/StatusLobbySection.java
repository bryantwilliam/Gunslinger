package com.gmail.gogobebe2.gunslinger.scoreboard;

import com.gmail.gogobebe2.gunslinger.lobby.Lobby;
import com.gmail.gogobebe2.gunslinger.lobby.LobbyState;
import com.gmail.gogobebe2.gunslinger.lobby.LobbyTimer;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class StatusLobbySection extends StatusSection {
    private Lobby lobby;

    public StatusLobbySection(Scoreboard scoreboard, Objective objective, Lobby lobby) {
        super(scoreboard, objective);
        this.lobby = lobby;
    }

    @Override
    protected String getStatusMessage() {
        LobbyTimer timer = lobby.getTimer();
        String dots = (timer.getSecondsInMinute() % 2 == 0 ? "." : "");
        if (lobby.getState() == LobbyState.WAITING) {
            return ChatColor.GOLD + "Waiting" + dots;
        }
        return ChatColor.GOLD + timer.getTime();
    }
}
