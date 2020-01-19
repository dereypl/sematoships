package test.com.semato.ships.global;

import com.semato.ships.global.Board;
import com.semato.ships.global.BoardGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGeneratorTest {

    @Test
    void putShips() {

        Board board = new Board();
        System.out.print(board.getBoardPlan(false));

        BoardGenerator boardGenerator = new BoardGenerator();

        boardGenerator.putShips(board);

        board.shoot(2,2);
        board.shoot(3,3);
        board.shoot(4,4);
        board.shoot(5,5);

        System.out.print(board.getBoardPlan(false));
        System.out.print(board.getBoardPlan(true));

        assertFalse(board.isBoardDestroyed());

        for (int x = 0; x < Board.DIMENSION; x++) {
            for (int y = 0; y < Board.DIMENSION; y++) {
                board.shoot(x, y);
            }
        }

        System.out.print(board.getBoardPlan(true));

        assertTrue(board.isBoardDestroyed());

    }
}