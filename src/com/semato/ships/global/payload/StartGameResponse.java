package com.semato.ships.global.payload;

import com.semato.ships.global.Board;


public class StartGameResponse extends Message {

    String enemyNick;
    Board enemyBoard;
    boolean isEnemyTurn;

    public StartGameResponse(String enemyNick, Board enemyBoard, boolean isEnemyTurn) {
        super("Start game response");
        this.enemyNick = enemyNick;
        this.enemyBoard = enemyBoard;
        this.isEnemyTurn = isEnemyTurn;
    }

    public String getEnemyNick() {
        return enemyNick;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public boolean isEnemyTurn() { return isEnemyTurn;}
}
