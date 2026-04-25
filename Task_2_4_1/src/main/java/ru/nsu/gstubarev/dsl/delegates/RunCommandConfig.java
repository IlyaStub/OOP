package ru.nsu.gstubarev.dsl.delegates;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import java.util.Map;

public class RunCommandConfig {
    private final Config config;
    public RunCommandConfig(Config config) {
        this.config = config;
    }

    public void check(Map<String, Object> params) {
        String group = (String) params.get("group");
        long taskId = ((Number) params.get("taskId")).longValue();
        config.addCheck(group, taskId);
    }

    public void bonus(Map<String, Object> params) {
        String studentGit = (String) params.get("studentGit");
        long taskId = ((Number) params.get("taskId")).longValue();
        int points = ((Number) params.get("points")).intValue();
        config.addBonus(studentGit, taskId, points);
    }
}