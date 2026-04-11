package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;
import ru.nsu.gstubarev.snake.model.interfaces.FoodGenerator;

public class LevelConfigTest
{
    @Test
    public void testLevelConfigGetters()
    {
        List<Point> walls = List.of(new Point(1, 1));
        FoodGenerator generator = new FoodGeneratorClassic();
        LevelConfig config = new LevelConfig(20, 15, walls, new Point(10, 10),
                Direction.UP, 3, 10, generator);

        assertEquals(20, config.getBoardWidth());
        assertEquals(15, config.getBoardHeight());
        assertEquals(walls, config.getWalls());
        assertEquals(new Point(10, 10), config.getSnakeStartPos());
        assertEquals(Direction.UP, config.getSnakeStartDir());
        assertEquals(3, config.getSnakeStartSpeed());
        assertEquals(10, config.getTargetScore());
        assertEquals(generator, config.getFoodGenerator());
    }
}