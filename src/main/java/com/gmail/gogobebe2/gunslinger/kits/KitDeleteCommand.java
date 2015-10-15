package com.gmail.gogobebe2.gunslinger.kits;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class KitDeleteCommand extends Command {
    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission("gs.kits.modify")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return;
        }
        else if (args.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "Error! You need to enter the kit name as parameter for this command!");
            return;
        }
        String kitName = args[0];
        Main.getInstance().getConfig().set("Kits." + kitName, null);
        Main.getInstance().saveConfig();
        commandSender.sendMessage(ChatColor.GREEN + "Kit " + kitName + " has been removed!");
    }
}
