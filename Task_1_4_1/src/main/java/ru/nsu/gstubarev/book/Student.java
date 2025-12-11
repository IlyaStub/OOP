package ru.nsu.gstubarev.book;

/**
 * Represents a university student with name and payment status.
 */
public class Student {
    private final String name;
    private final boolean isFee;

    /**
     * Creates a new student.
     *
     * @param name student's name
     * @param isFeePaying true if student pays for education
     */
    public Student(String name, boolean isFeePaying) {
        this.name = name;
        this.isFee = isFeePaying;
    }

    /**
     * Gets student's name.
     *
     * @return student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if student pays for education.
     *
     * @return true if student pays for education
     */
    public boolean isFeePaying() {
        return isFee;
    }

    @Override
    public String toString() {
        return "Имя студента = '" + name + "', он " + (isFee ? "" : "не ") + "на платке";
    }
}