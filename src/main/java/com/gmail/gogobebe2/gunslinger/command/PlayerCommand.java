package com.gmail.gogobebe2.gunslinger.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command {
    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Error! You need to be a player to use this command!");
            return;
        }
        onCommand((Player) commandSender, args);
    }

    protected abstract void onCommand(Player player, String args[]);
}
