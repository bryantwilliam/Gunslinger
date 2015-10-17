package com.gmail.gogobebe2.gunslinger.kits;

import com.gmail.gogobebe2.gunslinger.command.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class KitCreateCommand extends PlayerCommand {
    @Override
    protected void onCommand(Player player, String[] args) {
        if (!player.hasPermission("gs.kits.modify")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return;
        }
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Error! You need to enter the kit name as parameter for this command!");
            return;
        }
        String kitName = args[0];
        Kits.create(kitName, player);
        player.sendMessage(ChatColor.GREEN + "Kit " + kitName + " has been created!");
    }
}
