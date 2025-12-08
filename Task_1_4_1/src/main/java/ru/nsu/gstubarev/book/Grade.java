package ru.nsu.gstubarev.book;

/**
 * Represents possible grades in the grading system.
 */
public enum Grade {
    EXCELLENT("отлично", 5, true),
    GOOD("хорошо", 4, true),
    SATISFACTORY("удовлетворительно", 3, true),
    FAIL("неудовлетворительно", 2, true),
    PASS("зачет", -1, false),
    FAIL_PASS("не зачет", -1, false),
    NULL_GRADE("нет оценки", -1, true);

    private final String description;
    private final int numericValue;
    private final boolean hasNumericValue;

    Grade(String description, int numericValue, boolean hasNumericValue) {
        this.description = description;
        this.numericValue = numericValue;
        this.hasNumericValue = hasNumericValue;
    }

    /**
     * Gets textual description of the grade.
     *
     * @return textual description of the grade
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets numeric value of the grade.
     *
     * @return numeric value of the grade or -1 for non-numeric grades
     */
    public int getNumericValue() {
        return numericValue;
    }

    /**
     * Checks if grade has a numeric value.
     *
     * @return true if the grade has a numeric value
     */
    public boolean hasNumericValue() {
        return hasNumericValue;
    }

    /**
     * Checks if grade is EXCELLENT.
     *
     * @return true if the grade is EXCELLENT
     */
    public boolean isExcellent() {
        return this == EXCELLENT;
    }

    /**
     * Checks if grade is SATISFACTORY or FAIL.
     *
     * @return true if the grade is bad
     */
    public boolean isBad() {
        return this == SATISFACTORY
                || this == FAIL
                || this == FAIL_PASS;
    }

    /**
     * Checks if grade is SATISFACTORY.
     *
     * @return true if the grade is SATISFACTORY
     */
    public boolean isSatisfactory() {
        return this == SATISFACTORY;
    }
}