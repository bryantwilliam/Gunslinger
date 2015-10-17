package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.command.MainCommand;
import com.gmail.gogobebe2.gunslinger.selection.SelectionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        getLogger().info("Starting up " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
        instance = this;
        Bukkit.getPluginManager().registerEvents(new SelectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        getCommand("gunslinger").setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    public static Main getInstance() {
        return instance;
    }
}
