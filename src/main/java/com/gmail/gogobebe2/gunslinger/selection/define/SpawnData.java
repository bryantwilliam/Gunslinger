package com.gmail.gogobebe2.gunslinger.selection.define;

import com.gmail.gogobebe2.gunslinger.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SpawnData {
    private Location location;
    private String path;

    protected SpawnData(Location location) {
        this.location = location;
    }

    public SpawnData(String path) {
        this.path = path;
    }

    protected void saveToConfig(String path) {
        Main plugin = Main.getInstance();
        plugin.getConfig().set(path + ".world", location.getWorld().getName());
        plugin.getConfig().set(path + ".x", location.getX());
        plugin.getConfig().set(path + ".y", location.getY());
        plugin.getConfig().set(path + ".z", location.getZ());
        plugin.getConfig().set(path + ".pitch", location.getPitch());
        plugin.getConfig().set(path + ".yaw", location.getYaw());
        plugin.saveConfig();
    }

    public Location loadFromConfig() {
        Main plugin = Main.getInstance();
        World world = Bukkit.getWorld(plugin.getConfig().getString(path + ".world"));
        double x = plugin.getConfig().getDouble(path + ".x");
        double y = plugin.getConfig().getDouble(path + ".y");
        double z = plugin.getConfig().getDouble(path + ".z");
        float pitch = (float) plugin.getConfig().getDouble(path + ".pitch");
        float yaw = (float) plugin.getConfig().getDouble(path + ".yaw");
        this.location = new Location(world, x, y, z, pitch, yaw);
        return this.location;
    }
}
