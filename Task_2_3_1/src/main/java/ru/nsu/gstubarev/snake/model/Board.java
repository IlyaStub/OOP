package ru.nsu.gstubarev.snake.model;

import ru.nsu.gstubarev.snake.model.enums.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the game board, its boundaries, and wall obstacles.
 */
public class Board {
    private final int width;
    private final int height;
    private final Set<Point> walls;

    /**
     * Constructs a new game board with the specified dimensions.
     *
     * @param width  the width of the board
     * @param height the height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new HashSet<>();
    }

    /**
     * Adds a wall obstacle at the specified point.
     *
     * @param wall the coordinate point of the wall
     */
    public void addWall(Point wall) {
        walls.add(wall);
    }

    /**
     * Checks if there is a wall at the specified point.
     *
     * @param p the point to check
     * @return true if a wall exists at the point, false otherwise
     */
    public boolean isWall(Point p) {
        return walls.contains(p);
    }

    /**
     * Checks if a point is outside the board boundaries.
     *
     * @param p the point to check
     * @return true if the point is out of bounds, false otherwise
     */
    public boolean isOutOfBounds(Point p) {
        return p.x() < 0 || p.x() >= width || p.y() < 0 || p.y() >= height;
    }

    /**
     * Gets the width of the board.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the board.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
