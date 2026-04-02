package ru.nsu.gstubarev.snake.model;

import ru.nsu.gstubarev.snake.model.enums.Direction;

import java.util.LinkedList;

public class Snake {
    private final LinkedList<Point> body;
    private Direction currentDirection;
    private int speed;

    public Snake(Point startPosition, Direction startDirection, int speed) {
        this.body = new LinkedList<>();
        this.body.add(startPosition);
        this.currentDirection = startDirection;
        this.speed = speed;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Point getHead() {
        return body.getFirst();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }
}