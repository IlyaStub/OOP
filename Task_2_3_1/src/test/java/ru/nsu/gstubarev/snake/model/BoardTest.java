package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.enums.Point;

public class BoardTest {
    @Test
    public void testBoardDimensions() {
        Board board = new Board(15, 20);

        assertEquals(15, board.getWidth());
        assertEquals(20, board.getHeight());
    }

    @Test
    public void testWalls() {
        Board board = new Board(10, 10);
        Point wallPoint = new Point(5, 5);

        assertFalse(board.isWall(wallPoint));

        board.addWall(wallPoint);

        assertTrue(board.isWall(wallPoint));
    }

    @Test
    public void testOutOfBounds() {
        Board board = new Board(10, 10);

        assertFalse(board.isOutOfBounds(new Point(5, 5)));
        assertFalse(board.isOutOfBounds(new Point(0, 0)));
        assertFalse(board.isOutOfBounds(new Point(9, 9)));

        assertTrue(board.isOutOfBounds(new Point(-1, 5)));
        assertTrue(board.isOutOfBounds(new Point(5, -1)));
        assertTrue(board.isOutOfBounds(new Point(10, 5)));
        assertTrue(board.isOutOfBounds(new Point(5, 10)));
    }
}