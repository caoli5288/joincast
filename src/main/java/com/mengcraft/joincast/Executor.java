package com.mengcraft.joincast;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created on 16-6-10.
 */
public class Executor implements Listener {

    private final Main main;

    public Executor(Main main) {
        this.main = main;
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        main.execute(() -> {
            Joincast joincast = main.getDatabase().find(Joincast.class, event.getPlayer().getUniqueId());
            if (joincast != null && joincast.getMessage() != null) {
                main.getServer().broadcastMessage(String.format(joincast.getMessage().getMessage(), event.getPlayer().getDisplayName()));
            }
        });
        event.setJoinMessage("");
    }
}
