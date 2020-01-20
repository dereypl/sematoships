package com.semato.ships.server;

import com.semato.ships.global.Board;

public class User {
    private boolean isFree = false;
    private String username;
    private Board board;

    public User(String username, Board board){
        this.username = username;
        this.board = board;
    }

    public boolean isFree(){
        return this.isFree;
    }

    public void changeState(){
        isFree = true;
    }

    public String getUsername(){
        return username;
    }

    public Board getBoard() { return this.board; }

    public void setBoard(Board board) { this.board = board;}

}
