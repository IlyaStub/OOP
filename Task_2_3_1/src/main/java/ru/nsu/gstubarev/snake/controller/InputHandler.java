package ru.nsu.gstubarev.snake.controller;

import javafx.scene.input.KeyEvent;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Snake;

public class InputHandler {
    private boolean isDirectionChangedThisTick = false;

    public void resetTick() {
        isDirectionChangedThisTick = false;
    }

    public void handleKeyPress(KeyEvent event, GameEngine engine, boolean isPaused) {
        if (engine == null || isPaused || engine.isGameOver() || isDirectionChangedThisTick) {
            return;
        }

        Snake snake = engine.getPlayerSnake();
        Direction current = snake.getCurrentDirection();
        boolean directionChanged = false;

        switch (event.getCode()) {
            case UP -> {
                if (current != Direction.DOWN) {
                    snake.setCurrentDirection(Direction.UP);
                    directionChanged = true;
                }
            }
            case DOWN -> {
                if (current != Direction.UP) {
                    snake.setCurrentDirection(Direction.DOWN);
                    directionChanged = true;
                }
            }
            case LEFT -> {
                if (current != Direction.RIGHT) {
                    snake.setCurrentDirection(Direction.LEFT);
                    directionChanged = true;
                }
            }
            case RIGHT -> {
                if (current != Direction.LEFT) {
                    snake.setCurrentDirection(Direction.RIGHT);
                    directionChanged = true;
                }
            }
        }

        if (directionChanged) {
            isDirectionChangedThisTick = true;
        }
    }
}