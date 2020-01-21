package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

public class BoardRequest extends Message {

    private String enemyBoard;

    public BoardRequest(Board enemyBoard) {
        super("Board request ");
        this.enemyBoard = Board.serialize(enemyBoard);
    }

    public Board getEnemyBoard() {
        return Board.unserialize(enemyBoard);
    }
}
