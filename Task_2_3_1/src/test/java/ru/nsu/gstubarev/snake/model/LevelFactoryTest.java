package ru.nsu.gstubarev.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;

public class LevelFactoryTest
{
    @Test
    public void testGetLevelCount()
    {
        assertEquals(2, LevelFactory.getLevelCount());
    }

    @Test
    public void testGetLevelOne()
    {
        LevelConfig config = LevelFactory.getLevel(1);

        assertNotNull(config);
        assertEquals(15, config.getBoardWidth());
        assertEquals(15, config.getBoardHeight());
        assertEquals(new Point(7, 7), config.getSnakeStartPos());
        assertEquals(Direction.RIGHT, config.getSnakeStartDir());
        assertEquals(1, config.getSnakeStartSpeed());
        assertEquals(3, config.getTargetScore());
        assertEquals(3, config.getWalls().size());
    }

    @Test
    public void testGetLevelTwo()
    {
        LevelConfig config = LevelFactory.getLevel(2);

        assertNotNull(config);
        assertEquals(15, config.getBoardWidth());
        assertEquals(15, config.getBoardHeight());
        assertEquals(new Point(7, 7), config.getSnakeStartPos());
        assertEquals(Direction.RIGHT, config.getSnakeStartDir());
        assertEquals(2, config.getSnakeStartSpeed());
        assertEquals(12, config.getTargetScore());
        assertEquals(11, config.getWalls().size());
    }

    @Test
    public void testGetInvalidLevel()
    {
        assertNull(LevelFactory.getLevel(0));
        assertNull(LevelFactory.getLevel(3));
    }
}