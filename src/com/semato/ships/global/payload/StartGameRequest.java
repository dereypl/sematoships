package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

import java.io.Serializable;

public class StartGameRequest extends Message {

    private String nick;
    private Board myBoard;

    public StartGameRequest(String nick, Board myBoard){
        super("Start game request");
        this.nick = nick;
        this.myBoard = myBoard;
    }

    public String getNick(){
        return this.nick;
    }

    public Board getMyBoard() {
        return this.myBoard;
    }
}
