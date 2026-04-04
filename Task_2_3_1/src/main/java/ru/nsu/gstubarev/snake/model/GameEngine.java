package ru.nsu.gstubarev.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.foods.Apple;
import ru.nsu.gstubarev.snake.model.foods.GoldApple;
import ru.nsu.gstubarev.snake.model.interfaces.Food;

/**
 * Core game engine responsible for processing game logic, updates, and collisions.
 */
public class GameEngine {
    private final Random random = new Random();
    private final Board board;
    private final Snake playerSnake;
    private final List<Food> foods;
    private int score;
    private boolean isGameOver;
    private boolean shouldGrow;

    /**
     * Initializes the game engine with a board and player snake.
     *
     * @param board       the game board
     * @param playerSnake the player's snake
     */
    public GameEngine(Board board, Snake playerSnake) {
        this.board = board;
        this.playerSnake = playerSnake;
        this.foods = new ArrayList<>();
        this.isGameOver = false;
        this.shouldGrow = false;
        this.score = 0;

        spawnFood();
    }

    /**
     * Advances the game state by a single tick, moving the snake and handling collisions.
     */
    public void update() {
        if (isGameOver) {
            return;
        }

        Point head = playerSnake.getHead();
        Direction direction = playerSnake.getCurrentDirection();

        Point newHead = switch (direction) {
            case UP -> new Point(head.x(), head.y() - 1);
            case DOWN -> new Point(head.x(), head.y() + 1);
            case LEFT -> new Point(head.x() - 1, head.y());
            case RIGHT -> new Point(head.x() + 1, head.y());
        };

        if (checkCollision(newHead)) {
            isGameOver = true;
            return;
        }

        playerSnake.getBody().addFirst(newHead);

        Food eatenFood = null;
        for (Food food : foods) {
            if (food.getPosition().equals(newHead)) {
                eatenFood = food;
                break;
            }
        }

        if (eatenFood != null) {
            eatenFood.consume(this);
            foods.remove(eatenFood);
            spawnFood();
        }

        if (shouldGrow) {
            shouldGrow = false;
        } else {
            playerSnake.getBody().removeLast();
        }
    }

    private boolean checkCollision(Point newHead) {
        return board.isOutOfBounds(newHead)
                || playerSnake.getBody().contains(newHead)
                || board.isWall(newHead);
    }

    private void spawnFood() {
        Point pointFood;
        do {
            int x = random.nextInt(board.getWidth());
            int y = random.nextInt(board.getHeight());
            pointFood = new Point(x, y);
        } while (checkCollision(pointFood));

        if (random.nextDouble() < 0.2) {
            foods.add(new GoldApple(pointFood));
        } else {
            foods.add(new Apple(pointFood));
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game has ended, false otherwise
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Gets the player's snake entity.
     *
     * @return the snake
     */
    public Snake getPlayerSnake() {
        return playerSnake;
    }

    /**
     * Flags whether the snake should grow its body on the next movement tick.
     *
     * @param shouldGrow true to make the snake grow
     */
    public void setShouldGrow(boolean shouldGrow) {
        this.shouldGrow = shouldGrow;
    }

    /**
     * Adds the specified points to the current game score.
     *
     * @param points the amount to add
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Getter for score.
     *
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for food.
     *
     * @return list of food
     */
    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Getter for board.
     *
     * @return current board
     */
    public Board getBoard() {
        return board;
    }
}