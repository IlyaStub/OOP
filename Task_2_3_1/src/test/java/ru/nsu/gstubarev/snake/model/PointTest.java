package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    public void testPointCreationAndGetters() {
        Point point = new Point(5, 10);

        assertEquals(5, point.x());
        assertEquals(10, point.y());
    }

    @Test
    public void testPointEquality() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(4, 3);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }
}