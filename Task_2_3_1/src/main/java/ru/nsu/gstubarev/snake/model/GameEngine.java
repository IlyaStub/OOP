package ru.nsu.gstubarev.snake.model;

import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.food.Apple;
import ru.nsu.gstubarev.snake.model.interfaces.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private final Random random = new Random();
    private final Board board;
    private final Snake playerSnake;
    private final List<Food> foods;
    private int score;
    private boolean isGameOver;
    private boolean shouldGrow;

    public GameEngine(Board board, Snake playerSnake) {
        this.board = board;
        this.playerSnake = playerSnake;
        this.foods = new ArrayList<>();
        this.isGameOver = false;
        this.shouldGrow = false;
        this.score = 0;

        spawnFood();
    }

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
            shouldGrow = false; // Змейка выросла, просто не удаляем хвост в этот тик
        } else {
            playerSnake.getBody().removeLast(); // Обычный шаг - удаляем хвост
        }
    }

    private boolean checkCollision(Point newHead) {
        return board.isOutOfBounds(newHead) || playerSnake.getBody().contains(newHead) || board.isWall(newHead);
    }

    private void spawnFood() {
        Point pointFood;
        while(true) {
            int x = random.nextInt(board.getWidth());
            int y = random.nextInt(board.getHeight());
            pointFood = new Point(x, y);

            if (!checkCollision(pointFood)) {
                break;
            }
        }
        foods.add(new Apple(pointFood));
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Snake getPlayerSnake() {
        return playerSnake;
    }

    public void setShouldGrow(boolean shouldGrow) {
        this.shouldGrow = shouldGrow;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public Board getBoard() {
        return board;
    }
}