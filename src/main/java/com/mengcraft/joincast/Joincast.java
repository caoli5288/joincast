package com.mengcraft.joincast;

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

    @OneToOne
    private JoincastMessage message;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public JoincastMessage getMessage() {
        return message;
    }

    public void setMessage(JoincastMessage message) {
        this.message = message;
    }
}
