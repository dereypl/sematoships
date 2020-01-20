package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

public class BoardRequest extends Message {

    Board board;

    public BoardRequest(Board board) {
        super("Board request ");
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
