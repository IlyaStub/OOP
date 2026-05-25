package ru.nsu.gstubarev.dsl.dataclasses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Stores course configuration data.
 */
public class Config {
    private final List<Task> tasks = new LinkedList<>();
    private final List<Group> groups = new LinkedList<>();
    private final List<Checkpoint> checkpoints = new LinkedList<>();

    private final Map<String, List<Long>> checks = new HashMap<>();
    private final Map<String, Map<Long, Integer>> bonuses = new HashMap<>();

    /**
     * Adds a new task.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Finds task by identifier.
     */
    public Task getTaskById(long id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a student group.
     */
    public void addGroup(Group group) {
        this.groups.add(group);
    }

    /**
     * Returns all groups.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Adds a new checkpoint.
     */
    public void addCheckpoint(Checkpoint cp) {
        this.checkpoints.add(cp);
    }

    /**
     * Records a task check.
     */
    public void addCheck(String groupName, long taskId) {
        checks.computeIfAbsent(groupName, k -> new LinkedList<>()).add(taskId);
    }

    /**
     * Returns checks mapping.
     */
    public Map<String, List<Long>> getChecks() {
        return checks;
    }

    /**
     * Records a bonus score.
     */
    public void addBonus(String gitName, long taskId, int bonus) {
        bonuses.computeIfAbsent(gitName, k -> new HashMap<>()).put(taskId, bonus);
    }

    /**
     * Returns all checkpoints.
     */
    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    /**
     * Gets bonus for task.
     */
    public int getBonus(String gitName, long taskId) {
        if (bonuses.containsKey(gitName) && bonuses.get(gitName).containsKey(taskId)) {
            return bonuses.get(gitName).get(taskId);
        }
        return 0;
    }

    /**
     * Returns config string representation.
     */
    @Override
    public String toString() {
        return "Config{"
                + "tasks="
                + tasks
                + '}';
    }
}