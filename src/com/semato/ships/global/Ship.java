package com.semato.ships.global;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Ship {

    private HashMap<Pair<Integer, Integer>, Field> shipMap = new HashMap<>();

    private Ship() {

    }

    public int getSize() {
        return shipMap.size();
    }

    public static Ship findShipByField(Field field, Board board) {
        if (! field.hasShip()) {
            return null;
        }
        Ship ship = new Ship();
        ship.addFieldAndNeighbours(field, board);
        return ship;
    }

    private void addFieldAndNeighbours(Field field, Board board) {
        if (field == null || ! this.addField(field)) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            addFieldAndNeighbours(board.getNeighbour(field, i), board);
        }
    }

    private boolean addField(Field field) {

        if (! field.hasShip()) {
            return false;
        }

        Pair<Integer, Integer> coordinates =  new Pair<>(field.getX(), field.getY());

        if (shipMap.containsKey(coordinates)) {
            return false;
        }

        shipMap.put(coordinates, field);
        return true;
    }

    public boolean isSunk() {

        int hitCounter = 0;

        Iterator it = shipMap.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry)it.next();
            Field field = (Field) pair.getValue();
            if (field.isHit()) {
                hitCounter++;
            }
        }

        return (hitCounter >= getSize()) ? true : false;

    }

}
