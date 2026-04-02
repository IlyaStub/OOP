package ru.nsu.gstubarev.snake.model.enums;

import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.enums.Direction;

import static ru.nsu.gstubarev.snake.model.enums.Direction.LEFT;

public enum Level {
    L1(1, 2, new Point(2, 3), LEFT);

    private final int lvl;
    private final int speed;
    private final Point point;
    private final Direction direction;

    Level(int lvl, int speed, Point point, Direction direction) {
        this.lvl = lvl;
        this.speed = speed;
        this.point = point;
        this.direction = direction;
    }

    public int getLvl() {
        return lvl;
    }

    public int getSpeed() {
        return speed;
    }

    public Point getPoint() {
        return point;
    }

    public Direction getDirection() {
        return direction;
    }
}
