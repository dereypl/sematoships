package test.com.semato.ships.global;

import com.semato.ships.global.Board;
import com.semato.ships.global.BoardGenerator;
import com.semato.ships.global.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getField() {
        Board board = new Board();
        Field field1 = board.getField(2,3);
        assertTrue(field1 instanceof Field);

    }


    @Test
    void getSerialization() {

        Board board = new Board();
        BoardGenerator boardGenerator = new BoardGenerator();
        boardGenerator.putShips(board);
        board.shoot(2,2);
        board.shoot(3,3);
        board.shoot(4,4);
        board.shoot(5,5);

        System.out.print(board.getBoardPlan(false));

        String serializedBoard = Board.serialize(board);

        Board board1 = Board.unserialize(serializedBoard);

        System.out.println(board1.getBoardPlan(false));

        for (int x = 0; x < Board.DIMENSION; x++) {
            for (int y = 0; y < Board.DIMENSION; y++) {
                assertEquals(board.getStatus(x, y), board1.getStatus(x,y));
            }
        }
    }

}