package tests;

import org.junit.jupiter.api.Test;
import sliding_puzzle.Board;
import sliding_puzzle.BoardItem;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testInvalidN() {
        // write your code here
        for (int i = 1; i < 26; i++) {
            new Board(i, new BoardItem[(int)Math.sqrt(i+1)][(int)Math.sqrt(i+1)]);
        }
    }

}