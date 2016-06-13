package com.mengcraft.joincast;

import org.bukkit.entity.Player;

/**
 * Created on 16-6-13.
 */
public interface ShopMXBean {
    void addPermission(Player p, String permission);

    boolean removePoint(Player p, int point);
}
