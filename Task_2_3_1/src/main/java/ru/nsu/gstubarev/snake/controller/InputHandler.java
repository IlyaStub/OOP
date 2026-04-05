package ru.nsu.gstubarev.snake.controller;

import javafx.scene.input.KeyEvent;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Snake;
import ru.nsu.gstubarev.snake.model.enums.Direction;

/**
 * Handles keyboard input to control the snake's movement direction.
 */
public class InputHandler {
    private boolean isDirectionChangedThisTick = false;

    /**
     * Resets the direction change flag for the current game tick.
     * Ensures the player can only change direction once per tick.
     */
    public void resetTick() {
        isDirectionChangedThisTick = false;
    }

    /**
     * Processes a key press event to update the snake's direction.
     *
     * @param event    the keyboard event
     * @param engine   the game engine state
     * @param isPaused true if the game is currently paused
     */
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
            default -> {
                //do nothing
            }
        }

        if (directionChanged) {
            isDirectionChangedThisTick = true;
        }
    }
}