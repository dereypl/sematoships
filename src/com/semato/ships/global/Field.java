package com.semato.ships.global;

import java.io.Serializable;

public class Field implements Serializable {

    private boolean hasShip = false;
    private boolean isShotAt = false;
    private int x;
    private int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasShip() {
        return hasShip;
    }

    public Field setShip() {
        hasShip = true;
        return this;
    }

    public boolean isShotAt() {
        return isShotAt;
    }

    public boolean shoot() {
        isShotAt = true;
        return this.isHit();
    }

    public boolean isHit() {
        return (hasShip && isShotAt);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
