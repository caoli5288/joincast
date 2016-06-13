package com.mengcraft.joincast;

import com.mengcraft.simpleorm.DatabaseException;
import com.mengcraft.simpleorm.EbeanHandler;
import com.mengcraft.simpleorm.EbeanManager;
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

        List<JoincastMessage> messageList = db
                .find(JoincastMessage.class)
                .orderBy("slot desc")
                .findList();

        if (getConfig().getBoolean("shop")) {
            Plugin permission = getServer().getPluginManager().getPlugin("Permission");
            Plugin point = getServer().getPluginManager().getPlugin("PlayerPoints");
            if (permission != null && point != null) {
                shop = ShopSupport.newInstance();
            } else {
                throw new NullPointerException("Build-in shop depend with Permission and PlayerPoints");
            }
        }

        getServer().getPluginManager().registerEvents(new Executor(this, messageList), this);
        getCommand("joincast").setExecutor(new Commander(this, messageList));
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
