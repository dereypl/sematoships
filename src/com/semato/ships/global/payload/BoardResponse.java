package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

public class BoardResponse extends Message {

    Board board;

    public BoardResponse(Board board) {
        super("Board response");
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
