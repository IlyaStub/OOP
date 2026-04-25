package ru.nsu.gstubarev.dsl;

import groovy.lang.Closure;
import groovy.lang.Script;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.Task;
import ru.nsu.gstubarev.dsl.delegates.CheckpointListConfig;
import ru.nsu.gstubarev.dsl.delegates.GroupListConfig;
import ru.nsu.gstubarev.dsl.delegates.RunCommandConfig;
import ru.nsu.gstubarev.dsl.delegates.TaskListConfig;

import java.time.LocalDate;
import java.util.Map;

public abstract class CourseScript extends Script {
    private final Config config = new Config();

    public Config getConfig() {
        return config;
    }

    public void declareTasks(Closure<?> closure) {
        TaskListConfig configuration = new TaskListConfig(config);
        closure.setDelegate(configuration);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void declareGroups(Closure<?> closure) {
        GroupListConfig configuration = new GroupListConfig(config);
        closure.setDelegate(configuration);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void declareCheckpoints(groovy.lang.Closure<?> closure) {
        CheckpointListConfig configurator = new CheckpointListConfig(config);
        closure.setDelegate(configurator);
        closure.setResolveStrategy(groovy.lang.Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void command(String commandName, groovy.lang.Closure<?> closure) {
        RunCommandConfig configurator = new RunCommandConfig(getConfig());
        closure.setDelegate(configurator);
        closure.setResolveStrategy(groovy.lang.Closure.DELEGATE_FIRST);
        closure.call();
    }
}