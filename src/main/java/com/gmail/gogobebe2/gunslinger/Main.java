package com.gmail.gogobebe2.gunslinger;

import com.gmail.gogobebe2.gunslinger.commands.MainCommand;
import com.gmail.gogobebe2.gunslinger.arena.ArenaSelectionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting up " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
        Bukkit.getPluginManager().registerEvents(new ArenaSelectionManager.SelectionListener(this), this);
        getCommand("gunslinger").setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
    }
}