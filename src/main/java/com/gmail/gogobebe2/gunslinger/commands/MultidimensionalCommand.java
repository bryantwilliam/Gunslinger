package com.gmail.gogobebe2.gunslinger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Map;

public abstract class MultidimensionalCommand extends Command {
    private Map<String, Command> legalSubCommands;

    protected MultidimensionalCommand() {
        this.legalSubCommands = initializeLegalSubCommands();
    }

    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (args != null) {
            String subCommandLabel = args[0];
            String[] subArgs = null;
            if (args.length != 1) subArgs = Arrays.copyOfRange(args, 1, args.length - 1);
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

    }
}
