package com.gmail.gogobebe2.gunslinger.kits;

import com.gmail.gogobebe2.gunslinger.command.Command;
import com.gmail.gogobebe2.gunslinger.command.RecursiveCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public final class KitMainCommand extends RecursiveCommand {
    private static Map<String, Command> legalSubCommands = new HashMap<>();

    @Override
    protected Map<String, Command> initializeLegalSubCommands() {
        legalSubCommands.put("create", new KitCreateCommand());
        legalSubCommands.put("delete", new KitDeleteCommand());
        return legalSubCommands;
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
        return legalSubCommands;
    }
}
