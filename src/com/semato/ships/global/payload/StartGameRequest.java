package com.semato.ships.global.payload;

import java.io.Serializable;

public class StartGameRequest implements Serializable {

    private String nick;

    public StartGameRequest(String nick){
        this.nick = nick;
    }

    public String getNick(){
        return this.nick;
    }
}
