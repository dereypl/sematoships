package com.semato.ships.global.payload;

import java.io.Serializable;

public abstract class Message implements Serializable {
    String name;

    public Message(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
