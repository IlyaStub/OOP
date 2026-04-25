package ru.nsu.gstubarev.dsl.dataClasses;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Config {
    private final List<Task> tasks = new LinkedList<>();
    private final List<Group> groups = new LinkedList<>();
    private final List<Checkpoint> checkpoints = new LinkedList<>();

    private final Map<String, List<Long>> checks = new HashMap<>();
    private final Map<String, Map<Long, Integer>> bonuses = new HashMap<>();

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task getTaskById(long id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void addCheckpoint(Checkpoint cp) {
        this.checkpoints.add(cp);
    }

    public void addCheck(String groupName, long taskId) {
        checks.computeIfAbsent(groupName, k -> new LinkedList<>()).add(taskId);
    }

    public Map<String, List<Long>> getChecks() {
        return checks;
    }

    public void addBonus(String gitName, long taskId, int bonus) {
        bonuses.computeIfAbsent(gitName, k -> new HashMap<>()).put(taskId, bonus);
    }

    public int getBonus(String gitName, long taskId) {
        if (bonuses.containsKey(gitName) && bonuses.get(gitName).containsKey(taskId)) {
            return bonuses.get(gitName).get(taskId);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Config{" +
                "tasks=" + tasks +
                '}';
    }
}
