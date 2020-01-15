package test.com.semato.ships.global;

import com.semato.ships.global.Board;
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
}