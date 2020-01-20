package com.semato.ships.global.payload;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private String messageName;

    public Message(String messageName) {
        this.messageName= messageName;
    }

    public String getMessageName(){
        return this.messageName;
    }
}
