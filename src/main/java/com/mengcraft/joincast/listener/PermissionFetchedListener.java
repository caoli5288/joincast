package com.mengcraft.joincast.listener;

import com.mengcraft.joincast.Main;
import com.mengcraft.permission.event.PermissionFetchedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created on 16-6-13.
 */
public class PermissionFetchedListener extends JoinListener implements Listener {

    public PermissionFetchedListener(Main main) {
        super(main);
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        event.setJoinMessage("");
    }

    @EventHandler
    public void handle(PermissionFetchedEvent event) {
        super.handle(new PlayerJoinEvent(event.getPlayer(), ""));
    }
}
