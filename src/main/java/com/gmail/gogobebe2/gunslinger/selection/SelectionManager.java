package com.gmail.gogobebe2.gunslinger.selection;

import com.gmail.gogobebe2.gunslinger.selection.define.Point;
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
    public static List<SelectionManager> selectionManagers = new ArrayList<>();
    public static final ItemStack WAND = initWand();

    private Player player;
    private Point point1;
    private Point point2;

    protected SelectionManager(Player player) {
        this.player = player;
        selectionManagers.add(this);
    }

    protected void setPoint(Action action, int x, int z, String worldName) {
        String pointName;
        if (action == Action.LEFT_CLICK_BLOCK) {
            point1 = new Point(x, z, worldName);
            pointName = "Point 1";
        } else {
            point2 = new Point(x, z, worldName);
            pointName = "Point 2";
        }
        player.sendMessage(ChatColor.GREEN + pointName + " has just been set at " + ChatColor.DARK_GREEN + "x:" + x
                + ChatColor.GREEN + " and " + ChatColor.DARK_GREEN + "z:" + z);
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

    public Player getPlayer() {
        return this.player;
    }
    public Point getPoint1() {
        return this.point1;
    }
    public Point getPoint2() {
        return this.point2;
    }
}
