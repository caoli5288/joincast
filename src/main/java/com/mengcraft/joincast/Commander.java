package com.mengcraft.joincast;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created on 16-6-12.
 */
public class Commander implements CommandExecutor {

    private final List<JoincastMessage> messageList;
    private final Main main;

    public Commander(Main main, List<JoincastMessage> messageList) {
        this.messageList = messageList;
        this.main = main;
    }

    private void openChest(Player p) {
        p.openInventory(Holder.of(p, messageList, main.getShop() != null).getInventory());
    }

    @Override
    public boolean onCommand(CommandSender p, Command cmd, String s, String[] args) {
        if (p instanceof Player) {
            openChest((Player) p);
            return true;
        }
        return false;
    }
}
