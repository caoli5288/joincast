package com.mengcraft.joincast;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created on 16-6-10.
 */
@Entity
public class JoincastMessage {
    @Id
    private int id;

    @Column(columnDefinition = "text", nullable = false)
    private String message;

    @Column(nullable = false)
    private String permission;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int slot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
