package ru.nsu.gstubarev.snake.model;

import java.util.List;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.interfaces.FoodGenerator;

/**
 * Class for saving settings of some level.
 */
public class LevelConfig {
    private final int boardWidth;
    private final int boardHeight;
    private final List<Point> walls;
    private final Point snakeStartPos;
    private final Direction snakeStartDir;
    private final int snakeStartSpeed;
    private final int targetScore;
    private final FoodGenerator foodGenerator;

    /**
     * Constructs level configuration.
     */
    public LevelConfig(int boardWidth, int boardHeight, List<Point> walls,
                       Point snakeStartPos, Direction snakeStartDir, int snakeStartSpeed,
                       int targetScore, FoodGenerator foodGenerator) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.walls = walls;
        this.snakeStartPos = snakeStartPos;
        this.snakeStartDir = snakeStartDir;
        this.snakeStartSpeed = snakeStartSpeed;
        this.targetScore = targetScore;
        this.foodGenerator = foodGenerator;
    }

    /**
     * Returns the width of the game board.
     *
     * @return board width in cells
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Returns the height of the game board.
     *
     * @return board height in cells
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Returns the list of wall positions.
     *
     * @return list of points where walls are located
     */
    public List<Point> getWalls() {
        return walls;
    }

    /**
     * Returns the starting position of the snake.
     *
     * @return snake start position as a Point
     */
    public Point getSnakeStartPos() {
        return snakeStartPos;
    }

    /**
     * Returns the starting direction of the snake.
     *
     * @return snake start direction (UP, DOWN, LEFT, RIGHT)
     */
    public Direction getSnakeStartDir() {
        return snakeStartDir;
    }

    /**
     * Returns the starting movement speed of the snake.
     *
     * @return snake start speed in cells per second
     */
    public int getSnakeStartSpeed() {
        return snakeStartSpeed;
    }

    /**
     * Returns the target score required to complete the level.
     *
     * @return target score value
     */
    public int getTargetScore() {
        return targetScore;
    }

    /**
     * Returns the food generator strategy for this level.
     *
     * @return food generator implementation
     */
    public FoodGenerator getFoodGenerator() {
        return foodGenerator;
    }
}