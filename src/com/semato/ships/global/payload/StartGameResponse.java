package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

import java.io.Serializable;

public class StartGameResponse extends Message implements Serializable {

    String enemyNick;
    Board enemyBoard;

    public StartGameResponse(String enemyNick, Board enemyBoard) {
        super("startGame");
        this.enemyNick = enemyNick;
        this.enemyBoard = enemyBoard;
    }

    public String getEnemyNick() {
        return enemyNick;
    }
}
