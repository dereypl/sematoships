package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

public class BoardRequest extends Message {

    private Board enemyBoard;

    public BoardRequest(Board enemyBoard) {
        super("Board request ");
        this.enemyBoard = enemyBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }
}
