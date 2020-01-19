package test.com.semato.ships.global;

import com.semato.ships.global.Board;
import com.semato.ships.global.BoardGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGeneratorTest {

    @Test
    void putShips() {

        Board board = new Board();
//        System.out.print(board.getBoardPlan());

        BoardGenerator boardGenerator = new BoardGenerator();

        boardGenerator.putShips(board);
        System.out.print(board.getBoardPlan());

    }
}