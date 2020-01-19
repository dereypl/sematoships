package com.semato.ships.global.payload;

import java.io.Serializable;

public abstract class Request implements Serializable {

    private String requestName;

    public Request(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestName(){
        return this.requestName;
    }
}
