package com.semato.ships.global.payload;

import java.io.Serializable;

public class Response implements Serializable {

    private String responseName;

    public Response(String responseName) {
        this.responseName = responseName;
    }

    public String getResponseName(){
        return this.responseName;
    }
}
