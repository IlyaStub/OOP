package ru.nsu.gstubarev.dsl.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Config;

/**
 * TEST.
 */
public class RunCommandConfigTest {

    @Test
    public void testCheckCommand() {
        Config config = new Config();
        RunCommandConfig delegate = new RunCommandConfig(config);

        Map<String, Object> checkParams = new HashMap<>();
        checkParams.put("group", "20202");
        checkParams.put("taskId", 10L);

        delegate.check(checkParams);
        assertTrue(config.getChecks().containsKey("20202"));
    }

    @Test
    public void testBonusCommand() {
        Config config = new Config();

        Map<String, Object> bonusParams = new HashMap<>();
        bonusParams.put("studentGit", "gitUser");
        bonusParams.put("taskId", 15L);
        bonusParams.put("points", 5);
        RunCommandConfig delegate = new RunCommandConfig(config);
        delegate.bonus(bonusParams);
        assertEquals(5, config.getBonus("gitUser", 15L));
    }
}