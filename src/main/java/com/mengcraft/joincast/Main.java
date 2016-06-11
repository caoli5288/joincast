package com.mengcraft.joincast;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONValue;

import java.util.List;

/**
 * Created on 16-6-10.
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

    }

    public void execute(Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    public void broadcast(Player p, String message) {
        String name = p.getDisplayName();
        List<String> parsed = (List) JSONValue.parse(message);
        for (String s : parsed) {
            getServer().broadcastMessage(String.format(s, name));
        }
    }
}
