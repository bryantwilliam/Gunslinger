package com.gmail.gogobebe2.gunslinger.selection.define;

import com.gmail.gogobebe2.gunslinger.command.PlayerCommand;
import com.gmail.gogobebe2.gunslinger.selection.SelectionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class SelectionWandCommand extends PlayerCommand {
    @Override
    protected void onCommand(Player player, String[] args) {
        if (player.getInventory().addItem(SelectionManager.WAND).isEmpty())
            player.sendMessage(ChatColor.BLUE + "A wand has been added to your inventory.");
        else player.sendMessage(ChatColor.RED + "Error! Your inventory is full.");
    }
}
