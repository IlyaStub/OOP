package ru.nsu.gstubarev.snake.model.interfaces;

import java.util.List;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;

/**
 * Interface of snake.
 */
public interface Snake {
    /**
     * Gets the position of the snake's head.
     *
     * @return the head point
     */
    Point getHead();

    /**
     * Gets the list of points making up the snake's body.
     *
     * @return the snake's body segments
     */
    List<Point> getBody();

    /**
     * Gets the current moving direction of the snake.
     *
     * @return the current direction
     */
    Direction getCurrentDirection();

    /**
     * Sets a new moving direction for the snake.
     *
     * @param direction the new direction
     */
    void setCurrentDirection(Direction direction);

    /**
     * Calculates the next position of the snake's head based on its current direction.
     *
     * @return the coordinates of the next cell
     */
    Point getNextHead();

    /**
     * Gets the current length of the snake.
     *
     * @return the number of body segments
     */
    int getLength();

    /**
     * Gets the current speed of the snake.
     *
     * @return the speed
     */
    int getSpeed();

    /**
     * Sets a new speed for the snake.
     *
     * @param speed the new speed
     */
    void setSpeed(int speed);

    /**
     * Method for moving.
     *
     * @param newHead - next position of head
     * @param grow - need grow or not
     */
    void move(Point newHead, boolean grow);
}