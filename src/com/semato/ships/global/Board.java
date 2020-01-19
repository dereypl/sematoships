package com.semato.ships.global;

import javafx.util.Pair;

import java.util.HashMap;

public class Board {

    /**
     * @Front statuse dla frontu
     */
    public static final int STATUS_WATER = 0;
    public static final int STATUS_SHIP = 1;
    public static final int STATUS_MISS = 2;
    public static final int STATUS_HIT = 3;
    public static final int STATUS_SUNK = 4;

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

    /**
     * @Front Czy plansza przegrała
     * @return
     */
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

    /**
     * @Front strzel
     * @param x
     * @param y
     * @return
     */
    public boolean shoot(int x, int y) {
        return getField(x, y).shoot();
    }

    public String getBoardPlan(boolean forEnemy) {

        String plan = "";

        plan += "    0  1  2  3  4  5  6  7  8  9 ";
        plan += System.lineSeparator();

        for (int x = 0; x < Board.DIMENSION; x++) {
            plan += " " + x + " ";
            for (int y = 0; y < Board.DIMENSION; y++) {
                Field field = getField(x, y);
                int status = (forEnemy) ? getStatusForEnemy(x, y) : getStatus(x, y);
                String sign = "-";

                switch (status) {
                    case STATUS_WATER:
                        sign = "-";
                        break;
                    case STATUS_SHIP:
                        sign = "H";
                        break;
                    case STATUS_HIT:
                        sign = "X";
                        break;
                    case STATUS_MISS:
                        sign = "~";
                        break;
                    case STATUS_SUNK:
                        sign = "#";
                        break;
                }
                plan += " " + sign + " ";
            }

            plan += System.lineSeparator();

        }

        plan += System.lineSeparator();

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

    /**
     * Odpowiada na pytanie: czy na tym polu może zostaś postawiony segment statku.
     * @param field
     * @return
     */
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

    public boolean isSunk(int x, int y) {
        Field field = getField(x, y);
        Ship ship = Ship.findShipByField(field, this);
        if (ship == null) {
            return false;
        }
        return ship.isSunk();
    }

    /**
     * @Front zwraca status pola dla właściciela (nietrafione statki widoczne)
     * @param x
     * @param y
     * @return
     */
    public int getStatus(int x, int y) {

        Field field = getField(x, y);

        if (isSunk(x,y)) {
            return STATUS_SUNK;
        }

        if (field.isHit()) {
            return STATUS_HIT;
        }

        if (field.isShotAt()) {
            return STATUS_MISS;
        }

        if (field.hasShip()) {
            return STATUS_SHIP;
        }

        return STATUS_WATER;
    }

    /**
     * @Front zwraca status pola dla przeciwnika (nietrafione statki niewidoczne)
     * @param x
     * @param y
     * @return
     */
    public int getStatusForEnemy(int x, int y) {
        int status = getStatus(x, y);
        if (status == STATUS_SHIP) {
            return STATUS_WATER;
        } else {
            return status;
        }

    }

}
