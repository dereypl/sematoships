package com.semato.ships.client;

import com.semato.ships.global.Board;
import com.semato.ships.global.BoardGenerator;

public class Context {

    private static Context instance = null;

    private Board myBoard = null;
    private Board enemyBoard = null;

    public static Context getInstance() {

        if (instance == null) {
            instance = new Context();
        }

        return instance;
    }

    private Context() {

        myBoard = new Board();
        BoardGenerator boardGenerator = new BoardGenerator();
        boardGenerator.putShips(myBoard);
    }

    public void setMyBoard(Board board) {
        myBoard = board;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public void setEnemyBoard(Board board) {
        enemyBoard = board;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

}
