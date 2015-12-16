package com.gmail.gogobebe2.gunslinger.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Map;

public abstract class RecursiveCommand extends Command {
    private Map<String, Command> legalSubCommands;

    protected RecursiveCommand() {
        this.legalSubCommands = initializeLegalSubCommands();
    }

    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (args.length != 0) {
            String subCommandLabel = args[0];
            String[] subArgs = {};
            if (args.length > 1) subArgs = Arrays.copyOfRange(args, 1, args.length);
            if (legalSubCommands.containsKey(subCommandLabel)) {
                legalSubCommands.get(subCommandLabel).onCommand(commandSender, subArgs);
            }
            else {
                commandSender.sendMessage(ChatColor.RED + "Error! " + ChatColor.AQUA +
                        subCommandLabel + ChatColor.RED + " is not an appropriate sub-command!");
                showHelpMenu(commandSender);
            }
        }
        else showHelpMenu(commandSender);
    }

    protected abstract Map<String, Command> initializeLegalSubCommands();

    private void showHelpMenu(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error! These are the only legal parameters:");
        for (String legalSubCommand : legalSubCommands.keySet()) {
            commandSender.sendMessage(ChatColor.DARK_RED + " - " + legalSubCommand);
        }
    }
}
