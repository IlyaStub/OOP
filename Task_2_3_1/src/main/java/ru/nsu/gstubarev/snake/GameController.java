package ru.nsu.gstubarev.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.nsu.gstubarev.snake.model.Board;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.Snake;
import ru.nsu.gstubarev.snake.model.interfaces.Food;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    private GameEngine engine;

    private static final int TILE_SIZE = 25;

    @FXML
    public void initialize() {
        Board board = new Board(20, 20);

        board.addWall(new Point(5, 5));
        board.addWall(new Point(5, 6));
        board.addWall(new Point(5, 7));

        Snake player = new Snake(new Point(10, 10), Direction.RIGHT, 1);
        engine = new GameEngine(board, player);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> runTick()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void runTick() {
        engine.update();
        draw();
    }

    private void draw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setFill(Color.GRAY);
        // Не самый оптимальный перебор (лучше отдавать стены из board), но для старта сойдет
        for (int x = 0; x < engine.getBoard().getWidth(); x++) {
            for (int y = 0; y < engine.getBoard().getHeight(); y++) {
                if (engine.getBoard().isWall(new Point(x, y))) {
                    gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
                }
            }
        }

        gc.setFill(Color.RED);
        for (Food food : engine.getFoods()) {
            Point p = food.getPosition();
            gc.fillOval(p.x() * TILE_SIZE, p.y() * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        gc.setFill(Color.GREEN);
        for (Point p : engine.getPlayerSnake().getBody()) {
            gc.fillRect(p.x() * TILE_SIZE, p.y() * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + engine.getScore(), 10, 20);

        if (engine.isGameOver()) {
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER!", 220, 250);
        }
    }

    public void handleKeyPress(KeyEvent event) {
        Snake snake = engine.getPlayerSnake();
        Direction current = snake.getCurrentDirection();

        switch (event.getCode()) {
            case UP -> { if (current != Direction.DOWN) {
                snake.setCurrentDirection(Direction.UP);
            } }
            case DOWN -> { if (current != Direction.UP) {
                snake.setCurrentDirection(Direction.DOWN);
            } }
            case LEFT -> { if (current != Direction.RIGHT) {
                snake.setCurrentDirection(Direction.LEFT);
            } }
            case RIGHT -> { if (current != Direction.LEFT) {
                snake.setCurrentDirection(Direction.RIGHT);
            } }
        }
    }
}