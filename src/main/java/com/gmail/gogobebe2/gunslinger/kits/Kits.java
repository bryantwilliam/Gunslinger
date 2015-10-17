package com.gmail.gogobebe2.gunslinger.kits;

import com.gmail.gogobebe2.gunslinger.Main;
import com.gmail.gogobebe2.gunslinger.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;

import java.util.Set;

public final class Kits {
    protected static void apply(String name, Player player) throws NullPointerException {
        // TODO:
        // [x] Inventory - Armour, Contents and Held Item Slot.
        // [ ] Potion Effects
        // [ ] Exp
        // [ ] Health
        // [ ] Hunger
        // [ ] Gamemode
        // [ ] Flying
        name = name.toLowerCase();
        String configPrefix = "Kits." + name;
        Main plugin = Main.getInstance();
        if (!plugin.getConfig().isSet(configPrefix)) throw new NullPointerException("No such kit called " + name);

        Set<String> slots = plugin.getConfig().getConfigurationSection(configPrefix + ".inventory.contentsSlots").getKeys(false);
        ItemStack[] contents = new ItemStack[slots.size()];
        for (String slotStr : slots) {
            int slot = Integer.parseInt(slotStr);
            contents[slot] = plugin.getConfig().getItemStack(configPrefix + ".inventory.contentsSlots." + slot);
        }

        player.getInventory().setContents(contents);
    }

    protected static void create(String name, Player player) {
        name = name.toLowerCase();
        String configPrefix = "Kits." + name + ".inventory";
        Main plugin = Main.getInstance();
        PlayerInventory inventory = player.getInventory();
        ItemStack[] contents = inventory.getContents();
        ItemStack[] armour = inventory.getArmorContents();
        for (int slot = 0; slot < contents.length; slot++) {
            plugin.getConfig().set(configPrefix + ".contentsSlots." + slot, contents[slot]);
        }
        for (int slot = 0; slot < armour.length; slot++) {
            plugin.getConfig().set(configPrefix + ".armourSlots." + slot, armour[slot]);
        }
        plugin.saveConfig();
        final String finalName = name;
        KitMainCommand.getLegalSubCommands().put("name", new Command() {
            @Override
            protected void onCommand(CommandSender commandSender, String[] args) {
                Permission permission = new Permission("gs.kits.use." + finalName);
                permission.addParent("gs.kits.*", true);
                if (!commandSender.hasPermission(permission)) {
                    commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return;
                }
                else if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(ChatColor.RED + "Error, you have to be a player to use this command!");
                    return;
                }
                Player player = (Player) commandSender;
                // TODO: Select kit for that player later on when the lobby starts.
                apply(finalName, player);
            }
        });
    }
}
