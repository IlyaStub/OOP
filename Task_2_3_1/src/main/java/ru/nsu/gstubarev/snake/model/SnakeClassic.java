package ru.nsu.gstubarev.snake.model;

import java.util.LinkedList;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.interfaces.Snake;

/**
 * Represents the player's snake entity in the game.
 */
public class SnakeClassic implements Snake {
    private final LinkedList<Point> body;
    private Direction currentDirection;
    private int speed;

    /**
     * Constructs a new snake.
     *
     * @param startPosition the initial position of the snake's head
     * @param startDirection the initial moving direction
     * @param speed the initial movement speed
     */
    public SnakeClassic(Point startPosition, Direction startDirection, int speed) {
        this.body = new LinkedList<>();
        this.body.add(startPosition);
        this.currentDirection = startDirection;
        this.speed = speed;
    }

    @Override
    public Point getNextHead() {
        Point head = getHead();
        return switch (currentDirection) {
            case UP -> new Point(head.x(), head.y() - 1);
            case DOWN -> new Point(head.x(), head.y() + 1);
            case LEFT -> new Point(head.x() - 1, head.y());
            case RIGHT -> new Point(head.x() + 1, head.y());
        };
    }

    @Override
    public void move(Point newHead, boolean grow) {
        body.addFirst(newHead);
        if (!grow) {
            body.removeLast();
        }
    }

    @Override
    public LinkedList<Point> getBody() {
        return body;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public Point getHead() {
        return body.getFirst();
    }

    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    @Override
    public int getLength() {
        return body.size();
    }
}