package ru.nsu.gstubarev.dsl.dataClasses;

public class Student {
    private final String nameGit;
    private final String fio;
    private final String repoLink;

    private final java.util.Map<Long, CheckResult> results = new java.util.HashMap<>();

    public Student(String nameGit, String fio, String repoLink) {
        this.nameGit = nameGit;
        this.fio = fio;
        this.repoLink = repoLink;
    }

    public void addResult(long taskId, CheckResult result) {
        results.put(taskId, result);
    }

    public CheckResult getResult(long taskId) {
        return results.get(taskId);
    }

    public String getNameGit() {
        return nameGit;
    }

    public String getFio() {
        return fio;
    }

    public String getRepoLink() {
        return repoLink;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nameGit='" + nameGit + '\'' +
                ", fio='" + fio + '\'' +
                ", repoLink='" + repoLink + '\'' +
                '}';
    }
}
