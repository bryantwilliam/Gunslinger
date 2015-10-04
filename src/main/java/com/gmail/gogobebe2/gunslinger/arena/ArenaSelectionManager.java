package com.gmail.gogobebe2.gunslinger.arena;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.commands.Command;
import com.gmail.gogobebe2.gunslinger.commands.MultidimensionalCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaSelectionManager {
    private static List<ArenaSelectionManager> arenaSelectionManagers = new ArrayList<>();

    private Player player;
    private Point point1;
    private Point point2;
    private Main plugin;

    protected ArenaSelectionManager(Player player, Main plugin) {
        this.player = player;
        this.plugin = plugin;
        arenaSelectionManagers.add(this);
    }

    protected void setPoint(Action action, int x, int z, String worldName) {
        Point point;
        if (action == Action.LEFT_CLICK_BLOCK) {
            if (point1 == null) point1 = new Point();
            point = point1;
        } else {
            if (point2 == null) point2 = new Point();
            point = point2;
        }

        point.worldName = worldName;
        point.x = x;
        point.z = z;
    }

    private class Point {
        private int x;
        private int z;
        private String worldName;
    }

    public static class SelectionListener implements Listener {
        private Main plugin;

        public SelectionListener(Main plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        protected void onSelect(PlayerInteractEvent event) {
            Action action = event.getAction();
            // TODO test if they have wand.
            if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();
                Player player = event.getPlayer();
                new ArenaSelectionManager(player, plugin).setPoint(action, block.getX(), block.getZ(), block.getWorld().getName());
                event.setCancelled(true);
            }
        }
    }

    public static final class ArenaCommand extends MultidimensionalCommand {
        @Override
        protected Map<String, Command> initializeLegalSubCommands() {
            Map<String, Command> subCommands = new HashMap<>();
            subCommands.put("confirm", new ArenaConfirmCommand());
            subCommands.put("wand", new ArenaWandCommand());
            return subCommands;
        }
    }

    private static final class ArenaConfirmCommand extends Command {
        @Override
        protected void onCommand(CommandSender commandSender, String[] args) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
                return;
            }
            Player player = (Player) commandSender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Error! You need to enter an arena name as a parameter");
                return;
            }
            String arenaName = args[0];
            ArenaSelectionManager arenaSelectionManager = null;
            for (ArenaSelectionManager manager : arenaSelectionManagers)
                if (manager.player.getUniqueId().equals(player.getUniqueId())) arenaSelectionManager = manager;
            if (arenaSelectionManager == null) {
                player.sendMessage(ChatColor.RED + "Error! You have not selected points yet!");
                return;
            }

            if (arenaSelectionManager.point1 == null) {
                player.sendMessage(ChatColor.RED + "Error! Point 1 has not been selected yet!");
                return;
            }
            if (arenaSelectionManager.point2 == null) {
                player.sendMessage(ChatColor.RED + "Error! Point 2 has not been selected yet!");
                return;
            }

            String worldName = arenaSelectionManager.point1.worldName;
            if (!(worldName.equals(arenaSelectionManager.point2.worldName))) {
                player.sendMessage(ChatColor.RED + "Error! Point number 1 is not in the same world as point number 2!");
                return;
            }

            Main plugin = arenaSelectionManager.plugin;
            plugin.getConfig().set("Arenas." + worldName + "." + arenaName + ".point1.x", arenaSelectionManager.point1.x);
            plugin.getConfig().set("Arenas." + worldName + "." + arenaName + ".point1.z", arenaSelectionManager.point1.z);
            plugin.getConfig().set("Arenas." + worldName + "." + arenaName + ".point2.x", arenaSelectionManager.point2.x);
            plugin.getConfig().set("Arenas." + worldName + "." + arenaName + ".point2.z", arenaSelectionManager.point2.z);

            player.sendMessage(ChatColor.GREEN + "Arena " + ChatColor.DARK_GREEN + arenaName + ChatColor.GREEN
                    +  " has been set in world " + ChatColor.DARK_GREEN + worldName + ChatColor.GREEN + " at "
                    + ChatColor.DARK_GREEN + "x:" + arenaSelectionManager.point1.x + ", z:" + arenaSelectionManager.point1.z
                    + ChatColor.GREEN + " and " + ChatColor.DARK_GREEN + "x: " + arenaSelectionManager.point2.x + ", z:"
                    + arenaSelectionManager.point2.z + ChatColor.GREEN + ".");
        }
    }

    private static final class ArenaWandCommand extends Command {
        @Override
        protected void onCommand(CommandSender commandSender, String[] args) {
            ItemStack wand = new ItemStack(Material.STICK, 1);
            // TODO
        }
    }
}
