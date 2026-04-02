package ru.nsu.gstubarev.snake.model;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final int width;
    private final int height;
    private final Set<Point> walls;


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new HashSet<>();
    }

    public void addWall(Point wall) {
        walls.add(wall);
    }

    public boolean isWall(Point p) {
        return walls.contains(p);
    }

    public boolean isOutOfBounds(Point p) {
        return p.x() < 0 || p.x() >= width || p.y() < 0 || p.y() >= height;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
