package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SelectionDeleteCommand extends Command {
    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
           commandSender.sendMessage(ChatColor.RED + "Error! You need to be a player to use this command!");
            return;
        }
        Player player = (Player) commandSender;
        String name;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You did not enter an arena name as a parameter. This means you are deleting the lobby instead.");
            name = "LOBBY";
        }
        else name = args[0].toLowerCase();
        String worldName = player.getWorld().getName();
        Main plugin = Main.getInstance();
        plugin.getConfig().set("Selections." + worldName + "." + name, null);
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Selection " + ChatColor.DARK_GREEN + name + ChatColor.GREEN
                + " has been removed in world " + ChatColor.DARK_GREEN + worldName);
    }
}
