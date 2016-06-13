package com.mengcraft.joincast;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

/**
 * Created on 16-6-10.
 */
@Entity
public class Joincast {
    @Id
    private UUID id;

    @Column(length = 16, nullable = false)
    private String name;

    @OneToOne
    private JoincastMessage message;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JoincastMessage getMessage() {
        return message;
    }

    public void setMessage(JoincastMessage message) {
        this.message = message;
    }
}
