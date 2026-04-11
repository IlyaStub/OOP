package ru.nsu.gstubarev.snake.model;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.interfaces.Food;
import ru.nsu.gstubarev.snake.model.interfaces.FoodGenerator;
import ru.nsu.gstubarev.snake.model.interfaces.Snake;

/**
 * Core game engine responsible for processing game logic, updates, and collisions.
 */
public class GameEngine {
    private final Board board;
    private final Snake playerSnake;
    private final FoodGenerator foodGenerator;
    private final List<Food> foods;
    private int score;
    private boolean isGameOver;
    private boolean isGameWon;
    private boolean shouldGrow;
    private final int targetScore;

    /**
     * Initializes the game engine with a board and player snake.
     *
     * @param board the game board
     * @param playerSnake the player's snake
     */
    public GameEngine(Board board, Snake playerSnake,
                      FoodGenerator foodGenerator, int targetScore) {
        this.board = board;
        this.playerSnake = playerSnake;
        this.foodGenerator = foodGenerator;
        this.targetScore = targetScore;

        this.foods = new ArrayList<>();
        this.isGameOver = false;
        this.isGameWon = false;
        this.shouldGrow = false;
        this.score = 0;

        spawnFood();
    }

    /**
     * Advances the game state by a single tick, moving the snake and handling collisions.
     */
    public void update() {
        if (isGameOver || isGameWon) {
            return;
        }

        Point newHead = playerSnake.getNextHead();

        if (checkCollision(newHead)) {
            isGameOver = true;
            return;
        }

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

        playerSnake.move(newHead, shouldGrow);
        shouldGrow = false;
    }

    private boolean checkCollision(Point newHead) {
        return board.isOutOfBounds(newHead)
                || playerSnake.getBody().contains(newHead)
                || board.isWall(newHead);
    }

    private void spawnFood() {
        Food newFood = foodGenerator.generate(board, playerSnake);
        if (newFood == null) {
            isGameWon = true;
        } else {
            foods.add(newFood);
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
        if (this.score >= targetScore) {
            this.isGameWon = true;
        }
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

    /**
     * Getter for win.
     *
     * @return won or not
     */
    public boolean isGameWon() {
        return isGameWon;
    }
}