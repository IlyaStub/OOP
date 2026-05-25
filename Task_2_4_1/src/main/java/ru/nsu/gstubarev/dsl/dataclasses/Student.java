package ru.nsu.gstubarev.dsl.dataclasses;

/**
 * Represents a single student.
 */
public class Student {
    private final String nameGit;
    private final String fio;
    private final String repoLink;
    private final java.util.Map<Long, CheckResult> results = new java.util.HashMap<>();

    /**
     * Constructs a student record.
     */
    public Student(String nameGit, String fio, String repoLink) {
        this.nameGit = nameGit;
        this.fio = fio;
        this.repoLink = repoLink;
    }

    /**
     * Saves task check result.
     */
    public void addResult(long taskId, CheckResult result) {
        results.put(taskId, result);
    }

    /**
     * Returns task check result.
     */
    public CheckResult getResult(long taskId) {
        return results.get(taskId);
    }

    /**
     * Returns GitHub username.
     */
    public String getNameGit() {
        return nameGit;
    }

    /**
     * Returns full name.
     */
    public String getFio() {
        return fio;
    }

    /**
     * Returns repository link.
     */
    public String getRepoLink() {
        return repoLink;
    }

    /**
     * Returns student string representation.
     */
    @Override
    public String toString() {
        return "Student{"
                + "nameGit='"
                + nameGit + '\''
                + ", fio='" + fio + '\''
                + ", repoLink='"
                + repoLink + '\'' + '}';
    }
}