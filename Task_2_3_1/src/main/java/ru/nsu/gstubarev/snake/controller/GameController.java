package ru.nsu.gstubarev.snake.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ru.nsu.gstubarev.snake.model.Board;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Point;
import ru.nsu.gstubarev.snake.model.Snake;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label lengthLabel;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button restartBtn;

    private GameEngine engine;
    private Timeline timeline;
    private boolean isPaused = false;

    private GameRenderer renderer;
    private InputHandler inputHandler;

    @FXML
    public void initialize() {
        renderer = new GameRenderer(gameCanvas);
        inputHandler = new InputHandler();

        gameCanvas.widthProperty().bind(gamePane.widthProperty());
        gameCanvas.heightProperty().bind(gamePane.heightProperty());

        gameCanvas.widthProperty().addListener((obs, oldVal, newVal) -> renderer.draw(engine));
        gameCanvas.heightProperty().addListener((obs, oldVal, newVal) -> renderer.draw(engine));

        pauseBtn.setOnAction(e -> togglePause());
        restartBtn.setOnAction(e -> restartGame());

        restartGame();

        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> runTick()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void runTick() {
        engine.update();
        inputHandler.resetTick();
        renderer.draw(engine);

        double currentRate = 1.0 + (engine.getPlayerSnake().getSpeed() - 1) * 0.2;
        timeline.setRate(currentRate);

        javafx.application.Platform.runLater(() -> {
            scoreLabel.setText("Score: " + engine.getScore());
            lengthLabel.setText("Length: " + engine.getPlayerSnake().getLength());
        });
    }

    public void handleKeyPress(KeyEvent event) {
        inputHandler.handleKeyPress(event, engine, isPaused);
    }

    private void togglePause() {
        if (isPaused) {
            timeline.play();
            pauseBtn.setText("Pause");
        } else {
            timeline.pause();
            pauseBtn.setText("Resume");
        }
        isPaused = !isPaused;
    }

    private void restartGame() {
        Board board = new Board(15, 15);
        board.addWall(new Point(5, 5));
        board.addWall(new Point(5, 6));
        board.addWall(new Point(5, 7));

        Snake player = new Snake(new Point(7, 7), Direction.RIGHT, 1);

        engine = new GameEngine(board, player);

        isPaused = false;
        pauseBtn.setText("Pause");
        inputHandler.resetTick();

        if (timeline != null) {
            timeline.play();
        }

        renderer.draw(engine);
    }
}