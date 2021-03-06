package com.mengcraft.joincast.listener;

import com.mengcraft.joincast.Joincast;
import com.mengcraft.joincast.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created on 16-6-13.
 */
public class JoinListener implements Listener {

    private final Map<UUID, Long> timeout = new HashMap<>();
    private final Main main;
    private final int delay;

    public JoinListener(Main main) {
        this.main = main;
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
        main.exec(1, () -> {
            Joincast def = main.getDatabase().find(Joincast.class, p.getUniqueId());
            if (def != null && def.getMessage() != null && p.hasPermission(def.getMessage().getPermission())) {
                main.broadcast(p, def.getMessage().getMessage());
            }
        });
    }
}
