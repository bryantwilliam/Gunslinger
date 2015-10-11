package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.commands.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectionConfirmCommand extends Command {
    @Override
    protected void onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
            return;
        }
        Player player = (Player) commandSender;

        SelectionManager selectionManager = null;
        for (SelectionManager manager : SelectionManager.selectionManagers)
            if (manager.getPlayer().getUniqueId().equals(player.getUniqueId())) selectionManager = manager;
        if (selectionManager == null) {
            player.sendMessage(ChatColor.RED + "Error! You have not selected points yet!");
            return;
        }

        if (selectionManager.getPoint1() == null) {
            player.sendMessage(ChatColor.RED + "Error! Point 1 has not been selected yet!");
            return;
        }
        if (selectionManager.getPoint2() == null) {
            player.sendMessage(ChatColor.RED + "Error! Point 2 has not been selected yet!");
            return;
        }

        String name;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You did not enter an arena name as a parameter. This means you are setting the lobby instead.");
            name = "LOBBY";
        }
        else name = args[0].toLowerCase();
        String worldName = selectionManager.getPoint1().worldName;
        int p1x = selectionManager.getPoint1().x;
        int p1z = selectionManager.getPoint1().z;
        int p2x = selectionManager.getPoint2().x;
        int p2z = selectionManager.getPoint2().z;

        if (!(worldName.equals(selectionManager.getPoint2().worldName))) {
            player.sendMessage(ChatColor.RED + "Error! Point number 1 is not in the same world as point number 2!");
            return;
        }

        Main plugin = Main.getInstance();

        if (plugin.getConfig().isSet("Selections")) {
            for (String selection : plugin.getConfig()
                    .getConfigurationSection("Selections").getKeys(false)) {
                if (plugin.getConfig().getString("Selections." + selection + ".world").equals(worldName)) {
                    int compared1z = plugin.getConfig().getInt("Selections." + selection + ".point1.z");
                    int compared1x = plugin.getConfig().getInt("Selections." + selection + ".point1.x");
                    int compared2z = plugin.getConfig().getInt("Selections." + selection + ".point2.z");
                    int compared2x = plugin.getConfig().getInt("Selections." + selection + ".point2.x");

                    if (!(in(Math.min(p1x, p2x), Math.max(p1x, p2x), Math.min(compared1x, compared2x), Math.max(compared1x, compared2x))
                            || in(Math.min(p1z, p2z), Math.max(p1z, p2z), Math.min(compared1z, compared2z), Math.max(compared1z, compared2z)))) {
                        player.sendMessage(ChatColor.RED + "Error! The selected selection overlaps selection "
                                + ChatColor.GREEN + selection + ChatColor.RED + "!");
                        return;
                    }
                }
            }
        }

        plugin.getConfig().set("Selections." + worldName + "." + name + ".point1.x", p1x);
        plugin.getConfig().set("Selections." + worldName + "." + name + ".point1.z", p1z);

        plugin.getConfig().set("Selections." + worldName + "." + name + ".point2.x", p2x);
        plugin.getConfig().set("Selections." + worldName + "." + name + ".point2.z", p2z);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GREEN + "Selection " + ChatColor.DARK_GREEN + name + ChatColor.GREEN
                + " has been set in world " + ChatColor.DARK_GREEN + worldName + ChatColor.GREEN + " at "
                + ChatColor.DARK_GREEN + "x:" + p1x + ", z:" + p1z + ChatColor.GREEN + " and " + ChatColor.DARK_GREEN
                + "x: " + p2x + ", z:" + p2z + ChatColor.GREEN + ".");
    }

    private static boolean in(int min1, int max1, int min2, int max2) {
        return max1 < min2 || min1 > max2;
    }
}
