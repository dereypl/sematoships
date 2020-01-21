package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

public class BoardResponse extends Message {

    private Board myBoard;

    public BoardResponse(Board myBoard) {
        super("Board response");
        this.myBoard = myBoard;
    }

    public Board getMyBoard() {
        return myBoard;
    }
}
