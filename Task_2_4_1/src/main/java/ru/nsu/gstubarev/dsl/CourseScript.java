package ru.nsu.gstubarev.dsl;

import groovy.lang.Closure;
import groovy.lang.Script;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.Task;

import java.time.LocalDate;
import java.util.Map;

public abstract class CourseScript extends Script {
    private final Config config = new Config();

    public Config getConfig() {
        return config;
    }

    public void tasks(Closure<?> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void task(Map<String, Object> params) {
        int id = (Integer) params.get("id");
        String name = (String) params.get("name");
        int maxScores = (Integer) params.get("maxScores");

        LocalDate soft = LocalDate.parse((String) params.get("softDeadline"));
        LocalDate hard = LocalDate.parse((String) params.get("hardDeadline"));

        Task t = new Task(id, name, maxScores, soft, hard);
        config.addTask(t);
    }
}