package com.gmail.gogobebe2.gunslinger.command;

import com.gmail.gogobebe2.gunslinger.selection.SelectionCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public final class MainCommand extends MultidimensionalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command cmd, String alias, String[] args) {
        onCommand(commandSender, args);
        return true;
    }

    @Override
    protected Map<String, Command> initializeLegalSubCommands() {
        Map<String, Command> subCommands = new HashMap<>();
        subCommands.put("select", new SelectionCommand());
        return subCommands;
    }
}