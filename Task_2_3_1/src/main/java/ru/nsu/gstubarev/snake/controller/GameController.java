package ru.nsu.gstubarev.snake.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ru.nsu.gstubarev.snake.model.*;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.interfaces.Snake;

/**
 * Controller class managing UI interactions, the game loop, and input delegation.
 */
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
    @FXML
    private ComboBox<String> levelSelector;

    private GameEngine engine;
    private Timeline timeline;
    private int currentLevelNumber = 1;
    private LevelConfig currentConfig;
    private boolean isPaused = false;
    private GameRenderer renderer;
    private InputHandler inputHandler;

    /**
     * Initializes the controller, sets up renderers, and binds canvas dimensions.
     * Automatically called by JavaFX after FXML loading.
     */
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

        levelSelector.getItems().clear();
        int totalLevels = LevelFactory.getLevelCount();

        for (int i = 1; i <= totalLevels; i++) {
            levelSelector.getItems().add("Level " + i);
        }

        levelSelector.getSelectionModel().selectFirst();

        levelSelector.setOnAction(e -> {
            int selectedIndex = levelSelector.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                int selectedLevel = selectedIndex + 1;
                if (selectedLevel != currentLevelNumber) {
                    currentLevelNumber = selectedLevel;
                    loadLevel(currentLevelNumber);
                }
            }
        });

        restartGame();

        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> runTick()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void runTick() {
        engine.update();
        inputHandler.resetTick();

        if (engine.isGameWon() || engine.getScore() >= currentConfig.getTargetScore()) {
            timeline.stop();
            renderer.draw(engine);
            return;
        }

        if (engine.isGameOver()) {
            timeline.stop();
            renderer.draw(engine);
            return;
        }

        renderer.draw(engine);

        double currentRate = 1.0 + (engine.getPlayerSnake().getSpeed() - 1) * 0.2;
        timeline.setRate(currentRate);

        javafx.application.Platform.runLater(() -> {
            scoreLabel.setText("Score: " + engine.getScore());
            lengthLabel.setText("Length: " + engine.getPlayerSnake().getLength());
        });
    }

    /**
     * Handles keyboard events for controlling the game.
     *
     * @param event the triggered key event
     */
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

    private void loadLevel(int levelNumber) {
        currentConfig = LevelFactory.getLevel(levelNumber);

        Board board = new Board(currentConfig.getBoardWidth(), currentConfig.getBoardHeight());
        for (Point wall : currentConfig.getWalls()) {
            board.addWall(wall);
        }

        Snake player = new SnakeClassic(
                currentConfig.getSnakeStartPos(),
                currentConfig.getSnakeStartDir(),
                currentConfig.getSnakeStartSpeed()
        );

        engine = new GameEngine(board, player, currentConfig.getFoodGenerator(),
                currentConfig.getTargetScore());

        isPaused = false;
        pauseBtn.setText("Pause");
        inputHandler.resetTick();

        if (timeline != null) {
            timeline.play();
        }
        renderer.draw(engine);
    }

    private void restartGame() {
        currentLevelNumber = levelSelector.getSelectionModel().getSelectedIndex() + 1;
        loadLevel(currentLevelNumber);
    }
}