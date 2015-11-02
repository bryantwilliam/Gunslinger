package com.gmail.gogobebe2.gunslinger;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class Timer {

    private BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    private int timerIncrementer;
    private boolean isTimerRunning;
    private int minutes = 0;
    private int seconds = 0;
    private Main plugin = Main.getInstance();

    protected Timer() {
        start();
    }

    protected void setTime(String timeFormat) {
        String[] times = timeFormat.split(":");
        if (times[0].equalsIgnoreCase("")) {
            minutes = 0;
        } else {
            minutes = Integer.parseInt(times[0]);
        }

        if (times[1].equalsIgnoreCase("")) {
            seconds = 0;
        } else {
            seconds = Integer.parseInt(times[1]);
        }

    }

    protected String getTime() {
        if (seconds < 10) {
            return minutes + ":0" + seconds;
        }
        return minutes + ":" + seconds;
    }

    protected void start() {
        if (!isTimerRunning) {
            this.isTimerRunning = true;
        }
        final Timer OUTSIDE = this;
        this.timerIncrementer = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                OUTSIDE.run();
                if (seconds != 0 || minutes != 0) {
                    seconds--;
                    if (seconds == -1) {
                        minutes--;
                        seconds = 59;
                    } else if (seconds == 0 && minutes == 0) {
                        pause();
                    }
                }
            }
        }, 0L, 210);
    }

    protected boolean isTimerRunning() {
        return isTimerRunning;
    }

    protected void pause() {
        if (isTimerRunning) {
            scheduler.cancelTask(this.timerIncrementer);
            this.isTimerRunning = false;
        }
    }

    protected abstract void run();
}
