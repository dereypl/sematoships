package com.semato.ships.global;

import org.junit.platform.engine.DiscoveryFilter;

import java.util.LinkedList;
import java.util.Random;

public class BoardGenerator {

    public static int CARRIER_QUANTITY = 1;
    public static int DESTROYER_QUANTITY = 2;
    public static int SUBMARINE_QUANTITY = 3;
    public static int PATROL_QUANTITY = 4;
    
    public static int CARRIER_SIZE = 4;
    public static int DESTROYER_SIZE = 3;
    public static int SUBMARINE_SIZE = 2;
    public static int PATROL_SIZE = 1;

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void putShips(Board board) {
        putShipsOfType(board, CARRIER_QUANTITY, CARRIER_SIZE);
        putShipsOfType(board, DESTROYER_QUANTITY, DESTROYER_SIZE);
        putShipsOfType(board, SUBMARINE_QUANTITY, SUBMARINE_SIZE);
        putShipsOfType(board, PATROL_QUANTITY, PATROL_SIZE);
    }

    private void putShipsOfType(Board board, int quantity, int size) {

        for (int i = 0; i < quantity; i++) {
            putShip(board, size);
        }
    }

    private void putShip(Board board, int size) {

        LinkedList<Field> ship = null;
        while(ship == null) {
            ship = findPlaceForShip(board, size);
        }

        for (Field field: ship) {
            field.setShip();
        }

    }

    private LinkedList<Field> findPlaceForShip(Board board, int shipSize) {

        LinkedList<Field> ship = new LinkedList<>();

        int destination = getRandomNumberInRange(0, 3);
        int x = getRandomNumberInRange(0, Board.DIMENSION - 1);
        int y = getRandomNumberInRange(0, Board.DIMENSION - 1);

        Field field = board.getField(x, y);
        boolean isFirst = true;

        for (int i = 0; i < shipSize; i++) {
            if (! isFirst) {
                field = board.getNeighbour(field, destination);
            }

            isFirst = false;

            if (field == null) {
                return null;
            }

            if (board.isFree(field)) {
                ship.add(field);
            } else {
                return null;
            }
        }

        return ship;
    }
    
    
}
