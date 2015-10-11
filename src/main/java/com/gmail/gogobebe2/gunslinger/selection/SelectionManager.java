package com.gmail.gogobebe2.gunslinger.selection;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SelectionManager {
    protected static List<SelectionManager> selectionManagers = new ArrayList<>();
    protected static final ItemStack WAND = initWand();

    private Player player;
    private Point point1;
    private Point point2;

    protected SelectionManager(Player player) {
        this.player = player;
        selectionManagers.add(this);
    }

    protected void setPoint(Action action, int x, int z, String worldName) {
        Point point;
        String pointName;
        if (action == Action.LEFT_CLICK_BLOCK) {
            if (point1 == null) point1 = new Point();
            point = point1;
            pointName = "Point 1";
        } else {
            if (point2 == null) point2 = new Point();
            point = point2;
            pointName = "Point 2";
        }
        point.worldName = worldName;
        point.x = x;
        point.z = z;
        player.sendMessage(ChatColor.GREEN + pointName + " has just been set at " + ChatColor.DARK_GREEN + "x:" + x
                + ChatColor.GREEN + " and " + ChatColor.DARK_GREEN + "z:" + z);
    }

    protected class Point {
        protected int x;
        protected int z;
        protected String worldName;
    }

    private static ItemStack initWand() {
        ItemStack wand = new ItemStack(Material.STICK, 1);
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "WAND");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Left click to select point 1");
        lore.add(ChatColor.AQUA + "Right click to select point 2");
        lore.add(ChatColor.BLUE + "Type " + ChatColor.DARK_BLUE + "/gs select confirm" + ChatColor.BLUE + " when you're done");
        meta.setLore(lore);
        wand.setItemMeta(meta);
        return wand;
    }

    protected Player getPlayer() {
        return this.player;
    }
    protected Point getPoint1() {
        return this.point1;
    }
    protected Point getPoint2() {
        return this.point2;
    }
}
