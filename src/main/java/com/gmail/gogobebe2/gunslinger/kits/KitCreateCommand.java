package com.gmail.gogobebe2.gunslinger.kits;

import com.gmail.gogobebe2.gunslinger.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class KitCreateCommand extends Command {
    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission("gs.kits.modify")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return;
        }
        else if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
            return;
        }
        Player player = (Player) commandSender;
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "Error! You need to enter the kit name as parameter for this command!");
            return;
        }
        String kitName = args[0];
        Kits.create(kitName, player);
        player.sendMessage(ChatColor.GREEN + "Kit " + kitName + " has been created!");
    }
}
