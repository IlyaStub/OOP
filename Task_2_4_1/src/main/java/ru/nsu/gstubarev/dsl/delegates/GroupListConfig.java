package ru.nsu.gstubarev.dsl.delegates;

import groovy.lang.Closure;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.Group;

/**
 * Configures group list entries.
 */
public class GroupListConfig {
    private final Config config;

    /**
     * Wraps config for group setup.
     */
    public GroupListConfig(Config config) {
        this.config = config;
    }

    /**
     * Adds group using closure delegate.
     */
    public void addGroup(String name, Closure<?> closure) {
        Group group = new Group(name);
        GroupConfig configurator = new GroupConfig(group);
        closure.setDelegate(configurator);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();

        config.addGroup(group);
    }
}