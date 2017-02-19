package com.mengcraft.joincast;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONValue;

import java.util.List;

import static com.mengcraft.joincast.Main.nil;

/**
 * Created on 16-6-11.
 */
public class Holder implements InventoryHolder {

    private Inventory inventory;

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @SuppressWarnings("unchecked")
    public static Holder of(Player p, List<JoincastMessage> messageList, boolean shop) {
        Holder holder = new Holder();
        holder.inventory = p.getServer().createInventory(holder, 54, "");
        for (int i = 0; i < messageList.size(); i++) {
            JoincastMessage message = messageList.get(i);
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();

            List<String> lore = (List<String>) JSONValue.parse(message.getMessage());
            if (nil(lore)) {
                throw new NullPointerException(message.getMessage());
            }

            if (p.hasPermission(message.getPermission())) {
                item.setTypeId(403);
                meta.setDisplayName(ChatColor.GREEN + "点击选择");
            } else if (shop) {
                meta.setDisplayName(ChatColor.GOLD + "暂未拥有");
                lore.add("");
                if (message.getPrice() > 0) {
                    lore.add(ChatColor.GOLD + "价格: " + message.getPrice() + " 点券30天");
                    lore.add(ChatColor.GOLD + "按住SHIFT点击立即购买");
                } else {
                    lore.add(ChatColor.DARK_RED + "这个物品暂未开放购买");
                }
            } else {
                meta.setDisplayName(ChatColor.GRAY + "暂未拥有");
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
            holder.inventory.setItem(i, item);
        }
        return holder;
    }
}
