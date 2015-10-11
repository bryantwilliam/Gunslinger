package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.selection.SelectionListener;
import com.gmail.gogobebe2.gunslinger.commands.MainCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        getLogger().info("Starting up " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
        Bukkit.getPluginManager().registerEvents(new SelectionListener(), this);
        getCommand("gunslinger").setExecutor(new MainCommand());
        instance = this;
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    public static Main getInstance() {
        return instance;
    }
}
