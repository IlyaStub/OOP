package ru.nsu.gstubarev.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.gstubarev.snake.controller.GameController;

public class SnakeApplication extends Application {
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

    public static void main(String[] args) {
        launch();
    }
}