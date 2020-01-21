package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

public class BoardResponse extends Message {

    private Board myBoard;

    public boolean isTurn() {
        return turn;
    }

    private boolean turn;

    public BoardResponse(Board myBoard, boolean turn) {
        super("Board response");
        this.myBoard = myBoard;
        this.turn = turn;
    }

    public Board getMyBoard() {
        return myBoard;
    }
}
