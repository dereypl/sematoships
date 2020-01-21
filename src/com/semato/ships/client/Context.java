package com.semato.ships.client;

import com.semato.ships.global.Board;
import com.semato.ships.global.BoardGenerator;

public class Context {

    private static Context instance = null;

    private Board myBoard = null;
    private Board enemyBoard = null;
    private String enemyNick;
    private String playerNick;
    private volatile boolean isEnemyTurn = false;

    public static Context getInstance() {

        if (instance == null) {
            instance = new Context();
        }

        return instance;
    }

    private Context() {

        myBoard = new Board();
        enemyBoard = new Board();
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

    public boolean isEnemyTurn() {
        return isEnemyTurn;
    }

    public void setEnemyTurn(boolean enemyTurn) {
        isEnemyTurn = enemyTurn;
    }

    public void reset() {
        instance = null;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public void setEnemyNick(String enemyNick) {
        this.enemyNick = enemyNick;
    }

    public String getEnemyNick() {
        return enemyNick;
    }

    public String getPlayerNick() {
        return playerNick;
    }
}
