package com.semato.ships.global.payload;

import com.semato.ships.global.Board;

import java.io.Serializable;

public class StartGameRequest implements Serializable {

    private String nick;
    private Board myBoard;

    public StartGameRequest(String nick, Board myBoard){
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
