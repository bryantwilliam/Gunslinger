package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.commands.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SelectionWandCommand extends Command {
    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.getInventory().addItem(SelectionManager.WAND).isEmpty())
                player.sendMessage(ChatColor.BLUE + "A wand has been added to your inventory.");
            else player.sendMessage(ChatColor.RED + "Error! Your inventory is full.");
        } else commandSender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
    }
}
