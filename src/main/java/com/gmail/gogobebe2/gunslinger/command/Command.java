package com.gmail.gogobebe2.gunslinger.command;

import org.bukkit.command.CommandSender;

public abstract class Command  {
    protected abstract void onCommand(CommandSender commandSender, String args[]);
}
