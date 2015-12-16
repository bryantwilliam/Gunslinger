package com.gmail.gogobebe2.gunslinger.lobby;

import com.gmail.gogobebe2.gunslinger.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LobbyTimer extends Timer {
    private Lobby lobby;

    protected LobbyTimer(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    protected void run() {
        LobbyState state = lobby.getState();
        short playerAmount = lobby.getPlayerAmount();
        if (state == LobbyState.STARTING) {
            if (isTimerRunning() && playerAmount < Lobby.MIN_PLAYERS) lobby.setState(LobbyState.WAITING);
        } else {
            if (isTimerRunning() && playerAmount >= Lobby.MIN_PLAYERS) {
                setTime(Lobby.STARTING_TIME);
                lobby.setState(LobbyState.STARTING);
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()) lobby.getStatusLobbySection().display(player);
    }

    @Override
    protected void pause() {
        if (lobby.getState() == LobbyState.WAITING || lobby.getState().equals(LobbyState.INACTIVE)) {
            setTime(Integer.MAX_VALUE + ":");
            start();
        }
        else {
            //  TODO: START GAME
            setTime(Lobby.GAME_TIME);
            start();
        }
    }
}
