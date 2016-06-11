package com.mengcraft.joincast;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created on 16-6-11.
 */
public class Holder implements InventoryHolder {

    private Inventory inventory;

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static Holder of(Player p, List<JoincastMessage> messageList) {
        Holder holder = new Holder();
        holder.inventory = p.getServer().createInventory(holder, 54, "");
        for (int i = 0; i < messageList.size(); i++) {
            JoincastMessage message = messageList.get(i);
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            if (p.hasPermission(message.getPermission())) {
                item.setType(Material.BOOK);
            } else {
                // TODO
            }
            item.setItemMeta(meta);
            holder.inventory.setItem(i, item);
        }
        return holder;
    }
}
