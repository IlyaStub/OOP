module ru.nsu.gstubarev.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.gstubarev.snake to javafx.fxml;
    exports ru.nsu.gstubarev.snake;
    exports ru.nsu.gstubarev.snake.controller;
    opens ru.nsu.gstubarev.snake.controller to javafx.fxml;
}