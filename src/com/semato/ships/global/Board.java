package com.semato.ships.global;

import javafx.util.Pair;

import java.util.HashMap;

public class Board {

    public static final int DESTONATION_N = 0;
    public static final int DESTONATION_E = 1;
    public static final int DESTONATION_S = 2;
    public static final int DESTONATION_W = 3;
    public static final int DESTONATION_NE = 4;
    public static final int DESTONATION_SE = 5;
    public static final int DESTONATION_SW = 6;
    public static final int DESTONATION_NW = 7;

    public static final int DIMENSION = 10;
    public static final int NUMBER_OF_FIELDS_WITH_SHIPS =
        BoardGenerator.CARRIER_QUANTITY * BoardGenerator.CARRIER_SIZE
        + BoardGenerator.DESTROYER_QUANTITY * BoardGenerator.DESTROYER_SIZE
        + BoardGenerator.SUBMARINE_QUANTITY * BoardGenerator.SUBMARINE_SIZE
        + BoardGenerator.PATROL_QUANTITY * BoardGenerator.PATROL_SIZE
    ;

    private HashMap<Pair<Integer, Integer>, Field> boardMap = new HashMap<>();

    public Board() {

        for (int x = 0; x < Board.DIMENSION; x++) {
            for (int y = 0; y < Board.DIMENSION; y++) {
                Pair<Integer, Integer> coordinates =  new Pair<>(x, y);
                boardMap.put(coordinates, new Field(x, y));
            }
        }
    }

    public boolean isBoardDestroyed() {
        int hits = 0;
        for (int x = 0; x < Board.DIMENSION; x++) {
            for (int y = 0; y < Board.DIMENSION; y++) {
                if (getField(x, y).isHit()) {
                    hits++;
                }
                if (hits >= NUMBER_OF_FIELDS_WITH_SHIPS) {
                    return true;
                }
            }
        }

        return false;
    }

    public Field getField(int x, int y) {
        Pair<Integer, Integer> coordinates =  new Pair<>(x, y);
        return boardMap.get(coordinates);
    }

    public boolean shoot(int x, int y) {
        return getField(x, y).shoot();
    }

    public String getBoardPlan() {

        String plan = "";

        plan += "    0  1  2  3  4  5  6  7  8  9 ";
        plan += System.lineSeparator();

        for (int x = 0; x < Board.DIMENSION; x++) {
            plan += " " + x + " ";
            for (int y = 0; y < Board.DIMENSION; y++) {
                Field field = getField(x, y);
                if (field.isHit()) {
                    plan += " X ";
                } else if (field.hasShip()) {
                    plan += " H ";
                } else {
                    plan += " - ";
                }
            }

            plan += System.lineSeparator();

        }

        return plan;
    }

    public Field getNeighbour(Field field, int destonation) {
        int x = field.getX();
        int y = field.getY();

        switch (destonation) {
            case DESTONATION_N:
                y = y - 1;
                break;
            case DESTONATION_E:
                x = x + 1;
                break;
            case DESTONATION_S:
                y = y + 1;
                break;
            case DESTONATION_W:
                x = x - 1;
                break;
            case DESTONATION_NE:
                x = x + 1;
                y = y - 1;
                break;
            case DESTONATION_SE:
                x = x + 1;
                y = y + 1;
                break;
            case DESTONATION_SW:
                x = x - 1;
                y = y + 1;
                break;
            case DESTONATION_NW:
                x = x - 1;
                y = y - 1;
                break;
        }

        if (x < 0 || x >= DIMENSION || y < 0 || y >= DIMENSION) {
            return null;
        }

        return getField(x, y);
    }

    public boolean isFree(Field field) {
        if (field.hasShip()) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            Field neighbour = getNeighbour(field, i);
            if (neighbour == null) {
                continue;
            }

            if (neighbour.hasShip()) {
                return false;
            }
        }

        return true;
    }





}
