package com.mengcraft.joincast.listener;

import com.mengcraft.joincast.Holder;
import com.mengcraft.joincast.Joincast;
import com.mengcraft.joincast.JoincastMessage;
import com.mengcraft.joincast.Main;
import com.mengcraft.joincast.ShopMXBean;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

/**
 * Created on 16-6-10.
 */
public class ClickListener implements Listener {

    private final List<JoincastMessage> messageList;
    private final int messageListSize;
    private final Main main;

    public ClickListener(Main main, List<JoincastMessage> messageList) {
        this.main = main;
        this.messageList = messageList;
        messageListSize = messageList.size();
    }

    @EventHandler
    public void handle(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Holder) {
            int slot = event.getRawSlot();
            if (slot > -1 && slot < messageListSize) {
                JoincastMessage message = messageList.get(slot);
                if (event.getWhoClicked().hasPermission(message.getPermission())) {
                    process(event.getWhoClicked(), message);
                } else if (main.getShop() != null && event.getClick() == ClickType.SHIFT_LEFT && message.getPrice() > 0) {
                    processBuy(event.getWhoClicked(), message);
                }
            }
            event.setCancelled(true);
        }
    }

    private void processBuy(HumanEntity human, JoincastMessage message) {
        Player p = (Player) human;
        main.execute(() -> {
            ShopMXBean shop = main.getShop();
            if (shop.removePoint(p, message.getPrice())) {
                shop.addPermission(p, message.getPermission());
                main.process(() -> {
                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "购买成功");
                });
            } else {
                p.closeInventory();
                p.sendMessage(ChatColor.DARK_RED + "购买失败");
            }
        });
    }

    private void process(HumanEntity p, JoincastMessage message) {
        main.execute(() -> {
            Joincast fetched = main.getDatabase().find(Joincast.class, p.getUniqueId());
            Joincast def;
            if (fetched == null) {
                def = new Joincast();
                def.setId(p.getUniqueId());
                def.setName(p.getName());
            } else {
                def = fetched;
            }
            def.setMessage(message);
            main.getDatabase().save(def);
            main.process(() -> {
                p.closeInventory();
                p.sendMessage(ChatColor.GREEN + "设置成功");
            });
        });
    }

}
