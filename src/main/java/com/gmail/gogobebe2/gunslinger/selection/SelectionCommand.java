package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.commands.Command;
import com.gmail.gogobebe2.gunslinger.commands.MultidimensionalCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public final class SelectionCommand extends MultidimensionalCommand {
    @Override
    protected Map<String, Command> initializeLegalSubCommands() {
        Map<String, Command> subCommands = new HashMap<>();
        subCommands.put("confirm", new SelectionConfirmCommand());
        subCommands.put("wand", new SelectionWandCommand());
        return subCommands;
    }

    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission("gs.select.*")) {
            commandSender.sendMessage(ChatColor.RED + "Error! You do not have permission to use this command!");
            return;
        }
        super.onCommand(commandSender, args);
    }
}
