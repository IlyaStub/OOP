package ru.nsu.gstubarev.snake.model;

import java.util.LinkedList;
import ru.nsu.gstubarev.snake.model.enums.Direction;

/**
 * Represents the player's snake entity in the game.
 */
public class Snake {
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
    public Snake(Point startPosition, Direction startDirection, int speed) {
        this.body = new LinkedList<>();
        this.body.add(startPosition);
        this.currentDirection = startDirection;
        this.speed = speed;
    }

    /**
     * Gets the list of points making up the snake's body.
     *
     * @return the snake's body segments
     */
    public LinkedList<Point> getBody() {
        return body;
    }

    /**
     * Gets the current speed of the snake.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets a new speed for the snake.
     *
     * @param speed the new speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Gets the position of the snake's head.
     *
     * @return the head point
     */
    public Point getHead() {
        return body.getFirst();
    }

    /**
     * Gets the current moving direction of the snake.
     *
     * @return the current direction
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Sets a new moving direction for the snake.
     *
     * @param direction the new direction
     */
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    /**
     * Gets the current length of the snake.
     *
     * @return the number of body segments
     */
    public int getLength() {
        return body.size();
    }
}