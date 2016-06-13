package com.mengcraft.joincast;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created on 16-6-10.
 */
public class Executor implements Listener {

    private final Map<UUID, Long> timeout = new HashMap<>();
    private final List<JoincastMessage> messageList;
    private final int messageListSize;
    private final Main main;
    private final int delay;

    public Executor(Main main, List<JoincastMessage> messageList) {
        this.main = main;
        this.messageList = messageList;
        messageListSize = messageList.size();
        delay = main.getConfig().getInt("delay") * 1000;
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (check(p)) {
            fetch(p);
        }
        event.setJoinMessage("");
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

    private boolean check(Player p) {
        Long pre = timeout.get(p.getUniqueId());
        long now = System.currentTimeMillis();
        if (pre == null || pre + delay < now) {
            timeout.put(p.getUniqueId(), now);
            return true;
        }
        return false;
    }

    private void fetch(Player p) {
        main.execute(() -> {
            Joincast def = main.getDatabase().find(Joincast.class, p.getUniqueId());
            if (def != null && def.getMessage() != null && p.hasPermission(def.getMessage().getPermission())) {
                main.broadcast(p, def.getMessage().getMessage());
            }
        }, 1);
    }
}
