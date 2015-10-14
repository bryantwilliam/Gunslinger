package com.gmail.gogobebe2.gunslinger.kits;

import com.gmail.gogobebe2.gunslinger.command.Command;
import com.gmail.gogobebe2.gunslinger.command.MultidimensionalCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class KitCommand extends MultidimensionalCommand {
    @Override
    protected Map<String, Command> initializeLegalSubCommands() {
        Map<String, Command> subCommands = new HashMap<>();
        subCommands.put("create", new KitCreateCommand());
        subCommands.put("delete", new KitDeleteCommand());
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

    protected static Map<String, Command> getLegalSubCommands() {
        return getLegalSubCommands();
    }
}
