package ru.nsu.gstubarev.snake.model.enums;

import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.enums.Direction;

import static ru.nsu.gstubarev.snake.model.enums.Direction.LEFT;

/**
 * Represents predefined level configurations, dictating initial speed and starting parameters.
 */
public enum Level {
    L1(1, 2, new Point(2, 3), LEFT);

    private final int lvl;
    private final int speed;
    private final Point point;
    private final Direction direction;

    /**
     * Constructor for level.
     */
    Level(int lvl, int speed, Point point, Direction direction) {
        this.lvl = lvl;
        this.speed = speed;
        this.point = point;
        this.direction = direction;
    }

    /**
     * Gets the level identifier number.
     *
     * @return the level number
     */
    public int getLvl() {
        return lvl;
    }

    /**
     * Gets the initial speed multiplier for this level.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the starting coordinate point for the snake.
     *
     * @return the starting point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Gets the initial starting direction for the snake.
     *
     * @return the starting direction
     */
    public Direction getDirection() {
        return direction;
    }
}
