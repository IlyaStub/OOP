package ru.nsu.gstubarev.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.gstubarev.snake.controller.GameController;

/**
 * Main application class responsible for launching the JavaFX application.
 */
public class SnakeApplication extends Application {
    /**
     * Initializes and displays the main game stage.
     *
     * @param stage the primary stage for this application
     * @throws Exception if FXML loading fails
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class
                .getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        GameController controller = fxmlLoader.getController();
        scene.setOnKeyPressed(controller::handleKeyPress);

        stage.setMinWidth(700);
        stage.setMinHeight(500);

        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main entry point for the Java application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}