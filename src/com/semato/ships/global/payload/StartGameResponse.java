package com.semato.ships.global.payload;

import com.semato.ships.global.Board;


public class StartGameResponse extends Message {

    String enemyNick;
    Board enemyBoard;

    public StartGameResponse(String enemyNick, Board enemyBoard) {
        super("Start game response");
        this.enemyNick = enemyNick;
        this.enemyBoard = enemyBoard;
    }

    public String getEnemyNick() {
        return enemyNick;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }
}
