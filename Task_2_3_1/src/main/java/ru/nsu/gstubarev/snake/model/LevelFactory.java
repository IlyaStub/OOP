package ru.nsu.gstubarev.snake.model;

import java.util.List;
import ru.nsu.gstubarev.snake.model.enums.Direction;
import ru.nsu.gstubarev.snake.model.enums.Point;

/**
 * Class factory of levels.
 */
public class LevelFactory {
    /**
     * Getter level counts.
     *
     * @return count levels
     */
    public static int getLevelCount() {
        return 2;
    }

    /**
     * Constructor for level config.
     *
     * @param levelNumber - number of level
     * @return configuration of this lvl
     */
    public static LevelConfig getLevel(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> createLevelOne();
            case 2 -> createLevelTwo();
            default -> null;
        };
    }

    private static LevelConfig createLevelOne() {
        List<Point> walls = List.of(
                new Point(5, 5),
                new Point(5, 6),
                new Point(5, 7)
        );

        return new LevelConfig(
                15, 15,
                walls,
                new Point(7, 7),
                Direction.RIGHT,
                1,
                3,
                new FoodGeneratorClassic()
        );
    }

    private static LevelConfig createLevelTwo() {
        List<Point> walls = List.of(
                new Point(3, 3), new Point(3, 4), new Point(3, 5), new Point(3, 6),
                new Point(11, 3), new Point(11, 4), new Point(11, 5), new Point(11, 6),
                new Point(7, 0), new Point(7, 1), new Point(7, 2)
        );

        return new LevelConfig(
                15, 15,
                walls,
                new Point(7, 7),
                Direction.RIGHT,
                2,
                12,
                new FoodGeneratorClassic()
        );
    }
}
