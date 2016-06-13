package com.mengcraft.joincast;

import com.mengcraft.permission.Permission;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created on 16-6-13.
 */
public class ShopSupport implements ShopMXBean {
    private PlayerPointsAPI point;
    private Permission permission;

    public ShopSupport(PlayerPointsAPI point, Permission permission) {
        this.point = point;
        this.permission = permission;
    }

    @Override
    public void addPermission(Player p, String perm) {
        permission.addPermission(p, perm, 30);
    }

    @Override
    public boolean removePoint(Player p, int i) {
        return point.take(p.getUniqueId(), i);
    }

    public static ShopMXBean newInstance() {
        return new ShopSupport(JavaPlugin.getPlugin(PlayerPoints.class).getAPI(), Bukkit.getServicesManager().getRegistration(Permission.class).getProvider());
    }
}
