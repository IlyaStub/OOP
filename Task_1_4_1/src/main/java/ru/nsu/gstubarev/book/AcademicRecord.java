package ru.nsu.gstubarev.book;

import java.util.Objects;

/**
 * Represents a single academic record (grade for a course).
 */
public class AcademicRecord {
    private final String courseName;
    private final Grade grade;
    private final AssessmentType type;
    private final int semester;

    /**
     * Creates a new academic record.
     *
     * @param courseName name of the course
     * @param grade grade received
     * @param type type of assessment
     * @param semester semester number
     */
    public AcademicRecord(String courseName, Grade grade, AssessmentType type, int semester) {
        this.courseName = Objects.requireNonNull(courseName);
        this.grade = Objects.requireNonNull(grade);
        this.type = Objects.requireNonNull(type);
        this.semester = semester;
    }

    /**
     * Gets course name.
     *
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Gets grade for this record.
     *
     * @return grade for this record
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Gets type of assessment.
     *
     * @return type of assessment
     */
    public AssessmentType getType() {
        return type;
    }

    /**
     * Gets semester number.
     *
     * @return semester number
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Types of academic assessments.
     */
    public enum AssessmentType {
        CONTROL_WORK("Контрольная"),
        COLLOQUIUM("Коллоквиум"),
        EXAM("Экзамен"),
        DIFFERENTIATED_CREDIT("Дифференцированный зачет"),
        CREDIT("Зачет"),
        PRACTICE_REPORT_DEFENSE("Защита отчета по практике"),
        FINAL_QUALIFICATION_WORK_DEFENSE("Защита ВКР"),
        COURSE_WORK("Курсовая работа"),
        COURSE_PROJECT("Курсовой проект");

        private final String description;

        AssessmentType(String description) {
            this.description = description;
        }

        /**
         * Gets textual description of the assessment type.
         *
         * @return textual description of the assessment type
         */
        public String getDescription() {
            return description;
        }

        /**
         * Checks if this is an EXAM.
         *
         * @return true if this is an EXAM
         */
        public boolean isExam() {
            return this == EXAM;
        }

        /**
         * Checks if this is EXAM or DIFFERENTIATED_CREDIT.
         *
         * @return true if this is EXAM or DIFFERENTIATED_CREDIT
         */
        public boolean isExamOrDiffCredit() {
            return this == EXAM
                    || this == DIFFERENTIATED_CREDIT;
        }

        /**
         * Checks if this is FINAL_QUALIFICATION_WORK_DEFENSE.
         *
         * @return true if this is FINAL_QUALIFICATION_WORK_DEFENSE
         */
        public boolean isThesis() {
            return this == FINAL_QUALIFICATION_WORK_DEFENSE;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    @Override
    public String toString() {
        return String.format("AcademicRecord: курс='%s', оценка=%s, тип=%s, семестр=%d",
                courseName, grade.getDescription(), type.getDescription(), semester);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AcademicRecord that = (AcademicRecord) o;
        return semester == that.semester &&
                Objects.equals(courseName, that.courseName) &&
                grade == that.grade &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, grade, type, semester);
    }
}