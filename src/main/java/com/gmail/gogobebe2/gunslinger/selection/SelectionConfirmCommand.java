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

        String worldName = selectionManager.getPoint1().worldName;
        if (!(worldName.equals(selectionManager.getPoint2().worldName))) {
            player.sendMessage(ChatColor.RED + "Error! Point number 1 is not in the same world as point number 2!");
            return;
        }

        String name;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You did not enter an arena name as a parameter. This means you are setting the lobby instead.");
            name = "LOBBY";
        }
        else name = args[0];

        int minX = Math.min(selectionManager.getPoint1().x, selectionManager.getPoint2().x);
        int maxX = Math.max(selectionManager.getPoint1().x, selectionManager.getPoint2().x);
        int minZ = Math.min(selectionManager.getPoint1().z, selectionManager.getPoint2().z);
        int maxZ = Math.max(selectionManager.getPoint1().z, selectionManager.getPoint2().z);

        Main plugin = Main.getInstance();

        if (plugin.getConfig().isSet("Selections." + worldName)) {
            for (String selection : plugin.getConfig()
                    .getConfigurationSection("Selections." + worldName).getKeys(false)) {
                int comparedZ1 = plugin.getConfig().getInt("Selections." + worldName + "." + selection + "point1.z");
                int comparedX1 = plugin.getConfig().getInt("Selections." + worldName + "." + selection + "point1.x");
                int comparedZ2 = plugin.getConfig().getInt("Selections." + worldName + "." + selection + "point2.z");
                int comparedX2 = plugin.getConfig().getInt("Selections." + worldName + "." + selection + "point2.x");

                if (!(in(minX, maxX, Math.min(comparedX1, comparedX2), Math.max(comparedX1, comparedX2))
                        || in(minZ, maxZ, Math.min(comparedZ1, comparedZ2), Math.max(comparedZ1, comparedZ2)))) {
                    player.sendMessage(ChatColor.RED + "Error! The selected selection overlaps selection "
                            + ChatColor.GREEN + selection + ChatColor.RED + "!");
                    return;
                }
            }
        }

        plugin.getConfig().set("Selections." + worldName + "." + name + ".point1.x", selectionManager.getPoint1().x);
        plugin.getConfig().set("Selections." + worldName + "." + name + ".point1.z", selectionManager.getPoint1().z);
        plugin.getConfig().set("Selections." + worldName + "." + name + ".point2.x", selectionManager.getPoint2().x);
        plugin.getConfig().set("Selections." + worldName + "." + name + ".point2.z", selectionManager.getPoint2().z);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GREEN + "Selection " + ChatColor.DARK_GREEN + name + ChatColor.GREEN
                + " has been set in world " + ChatColor.DARK_GREEN + worldName + ChatColor.GREEN + " at "
                + ChatColor.DARK_GREEN + "x:" + selectionManager.getPoint1().x + ", z:" + selectionManager.getPoint1().z
                + ChatColor.GREEN + " and " + ChatColor.DARK_GREEN + "x: " + selectionManager.getPoint2().x + ", z:"
                + selectionManager.getPoint2().z + ChatColor.GREEN + ".");
    }

    private static boolean in(int min1, int max1, int min2, int max2) {
        return max1 < min2 || min1 > max2;
    }
}
