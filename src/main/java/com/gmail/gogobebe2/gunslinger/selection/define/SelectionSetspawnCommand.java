package com.gmail.gogobebe2.gunslinger.selection.define;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.command.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class SelectionSetspawnCommand extends PlayerCommand {
    @Override
    protected void onCommand(Player player, String[] args) {
        String name;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You did not enter an arena name as a parameter. This means you are setting the spawn of the main spawn area instead.");
            name = null;
        }
        else name = args[0].toLowerCase();

        String worldName = player.getWorld().getName();
        Main plugin = Main.getInstance();
        if (!plugin.getConfig().isSet("Selections." + worldName + "." + name)) {
            player.sendMessage(ChatColor.RED + "Error! " + ChatColor.DARK_RED + name + ChatColor.RED
                    + " hasn't been set yet!");
            return;
        }
        Location location = player.getLocation();
        String configPathPrefix = "Selections.";
        if (name == null) {
            new SpawnData(location).saveToConfig(configPathPrefix + "SPAWN");
        }
        else if (name.equalsIgnoreCase("lobby")) {
            new SpawnData(location).saveToConfig(configPathPrefix + location.getWorld().getName() + "." + name + ".spawn");
            player.sendMessage(ChatColor.GREEN + "Lobby's spawn has been set!");
        }
        else {
            configPathPrefix = configPathPrefix + location.getWorld().getName() + "." + name + ".spawns";
            int nextID = 0;
            for (String idString : plugin.getConfig().getConfigurationSection(configPathPrefix).getKeys(false)) {
                int id = Integer.parseInt(idString);
                if (nextID <= id) nextID = id + 1;
            }
            new SpawnData(location).saveToConfig(configPathPrefix + "." + nextID);
            player.sendMessage(ChatColor.GREEN + "New spawn for selection " + ChatColor.DARK_GREEN + name + ChatColor.GREEN + " has been set!");
        }
    }
}
