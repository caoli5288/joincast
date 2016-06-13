package com.mengcraft.joincast;

import com.mengcraft.joincast.listener.ClickListener;
import com.mengcraft.joincast.listener.JoinListener;
import com.mengcraft.joincast.listener.PermissionFetchedListener;
import com.mengcraft.simpleorm.DatabaseException;
import com.mengcraft.simpleorm.EbeanHandler;
import com.mengcraft.simpleorm.EbeanManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONValue;

import java.util.List;

/**
 * Created on 16-6-10.
 */
public class Main extends JavaPlugin {

    private ShopMXBean shop;

    @Override
    public void onEnable() {
        EbeanHandler db = EbeanManager.DEFAULT.getHandler(this);
        if (db.isNotInitialized()) {
            db.define(JoincastMessage.class);
            db.define(Joincast.class);
            try {
                db.initialize();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        db.reflect();
        db.install();

        Plugin permission = getServer().getPluginManager().getPlugin("Permission");
        if (permission == null) {
            getServer().getPluginManager().registerEvents(new JoinListener(this), this);
            getLogger().warning("Plugin Permission not found");
            getLogger().warning("Build-in shop disabled");
        } else {
            if (getConfig().getBoolean("shop")) {
                Plugin point = getServer().getPluginManager().getPlugin("PlayerPoints");
                if (point == null) {
                    getLogger().warning("Plugin PlayerPoints not found");
                    getLogger().warning("Build-in shop disabled");
                } else {
                    shop = ShopSupport.newInstance();
                }
            }
            getServer().getPluginManager().registerEvents(new PermissionFetchedListener(this), this);
        }

        List<JoincastMessage> messageList = db.find(JoincastMessage.class)
                .orderBy("slot desc")
                .findList();

        getServer().getPluginManager().registerEvents(new ClickListener(this, messageList), this);
        getCommand("joincast").setExecutor(new Commander(this, messageList));

        String[] ad = {
                ChatColor.GREEN + "梦梦家高性能服务器出租店",
                ChatColor.GREEN + "shop105595113.taobao.com"
        };
        getServer().getConsoleSender().sendMessage(ad);
    }

    public void broadcast(Player p, String message) {
        String name = p.getDisplayName();
        List<String> parsed = (List) JSONValue.parse(message);
        for (String s : parsed) {
            getServer().broadcastMessage(String.format(s, name));
        }
    }

    public void execute(Runnable j) {
        getServer().getScheduler().runTaskAsynchronously(this, j);
    }

    public void execute(Runnable j, int delay) {
        getServer().getScheduler().runTaskLaterAsynchronously(this, j, delay);
    }

    public void process(Runnable j) {
        getServer().getScheduler().runTask(this, j);
    }

    public ShopMXBean getShop() {
        return shop;
    }
}
