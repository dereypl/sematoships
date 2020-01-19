package com.semato.ships.global.payload;

import java.io.Serializable;

public class StartGameResponse extends Response implements Serializable {

    public StartGameResponse() {
        super("startGame");
    }
}
