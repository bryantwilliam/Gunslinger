package com.gmail.gogobebe2.gunslinger.commands;

import org.bukkit.command.CommandSender;

public abstract class Command  {
    protected abstract void onCommand(CommandSender commandSender, String args[]);
}
