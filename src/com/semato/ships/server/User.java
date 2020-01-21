package com.semato.ships.server;

import com.semato.ships.global.Board;

public class User {
    private String username;
    private Board board;
    private boolean isUserTurn = false;

    public User(String username, Board board){
        this.username = username;
        this.board = board;
    }

    public String getUsername(){
        return username;
    }

    public Board getBoard() { return this.board; }

    public void setBoard(Board board) { this.board = board;}

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public void setUserTurn(boolean userTurn) {
        isUserTurn = userTurn;
    }
}
