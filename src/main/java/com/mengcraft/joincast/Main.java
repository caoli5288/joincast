package com.mengcraft.joincast;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created on 16-6-10.
 */
public class Main extends JavaPlugin {

    public void execute(Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

}
