package com.mengcraft.joincast;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16-6-12.
 */
public class Commander {

    private final List<JoincastMessage> messageList;
    private final Main main;

    public Commander(List<JoincastMessage> messageList, Main main) {
        this.messageList = messageList;
        this.main = main;
    }

    private void openChest(Player p) {
        p.openInventory(Holder.of(p, messageList).getInventory());
    }

}
