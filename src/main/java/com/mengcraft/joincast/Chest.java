package com.mengcraft.joincast;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;

/**
 * Created on 16-6-11.
 */
public class Chest implements InventoryHolder {

    private Map<Integer, JoincastMessage> mapping;

    @Override
    public Inventory getInventory() {
        return null;
    }

    public Map<Integer, JoincastMessage> getMapping() {
        return mapping;
    }

    public void setMapping(Map<Integer, JoincastMessage> mapping) {
        this.mapping = mapping;
    }
}
