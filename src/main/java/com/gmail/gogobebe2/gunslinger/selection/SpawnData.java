package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SpawnData {
    private Location location;
    private Main plugin;

    public SpawnData(Location location) {
        plugin = Main.getInstance();
        this.location = location;
    }

    protected SpawnData(String path) {
        plugin = Main.getInstance();
        World world = Bukkit.getWorld(plugin.getConfig().getString(path + ".world"));
        double x = plugin.getConfig().getDouble(path + ".x");
        double y = plugin.getConfig().getDouble(path + ".y");
        double z = plugin.getConfig().getDouble(path + ".z");
        float pitch = (float) plugin.getConfig().getDouble(path + ".pitch");
        float yaw = (float) plugin.getConfig().getDouble(path + ".yaw");
        this.location = new Location(world, x, y, z, pitch, yaw);
    }

    public void saveToConfig(String path) {
        plugin.getConfig().set(path + ".world", location.getWorld().getName());
        plugin.getConfig().set(path + ".x", location.getX());
        plugin.getConfig().set(path + ".y", location.getY());
        plugin.getConfig().set(path + ".z", location.getZ());
        plugin.getConfig().set(path + ".pitch", location.getPitch());
        plugin.getConfig().set(path + ".yaw", location.getYaw());
        plugin.saveConfig();
    }

    protected Location getLocation() {
        return this.location;
    }
}
