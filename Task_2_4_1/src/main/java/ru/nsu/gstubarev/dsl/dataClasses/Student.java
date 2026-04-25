package ru.nsu.gstubarev.dsl.dataClasses;

public class Student {
    private final String nameGit;
    private final String fio;
    private final String repoLink;

    public Student(String nameGit, String fio, String repoLink) {
        this.nameGit = nameGit;
        this.fio = fio;
        this.repoLink = repoLink;
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
