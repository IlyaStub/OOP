package ru.nsu.gstubarev.dsl;

import groovy.lang.Closure;
import groovy.lang.Script;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.delegates.CheckpointListConfig;
import ru.nsu.gstubarev.dsl.delegates.GroupListConfig;
import ru.nsu.gstubarev.dsl.delegates.RunCommandConfig;
import ru.nsu.gstubarev.dsl.delegates.TaskListConfig;

/**
 * Base class for Groovy DSL scripts.
 */
public abstract class CourseScript extends Script {
    private final Config config = new Config();

    /**
     * Returns course configuration.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Declares task definitions.
     */
    public void declareTasks(Closure<?> closure) {
        TaskListConfig configuration = new TaskListConfig(config);
        closure.setDelegate(configuration);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    /**
     * Declares group definitions.
     */
    public void declareGroups(Closure<?> closure) {
        GroupListConfig configuration = new GroupListConfig(config);
        closure.setDelegate(configuration);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    /**
     * Declares checkpoint definitions.
     */
    public void declareCheckpoints(groovy.lang.Closure<?> closure) {
        CheckpointListConfig configurator = new CheckpointListConfig(config);
        closure.setDelegate(configurator);
        closure.setResolveStrategy(groovy.lang.Closure.DELEGATE_FIRST);
        closure.call();
    }

    /**
     * Registers a run command.
     */
    public void command(String commandName, groovy.lang.Closure<?> closure) {
        RunCommandConfig configurator = new RunCommandConfig(getConfig());
        closure.setDelegate(configurator);
        closure.setResolveStrategy(groovy.lang.Closure.DELEGATE_FIRST);
        closure.call();
    }
}