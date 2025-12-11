package ru.nsu.gstubarev.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Electronic grade book for FIT student with various calculation functions.
 */
public class GradeBook {
    private final Student student;
    private final List<Semester> semesters;

    /**
     * Creates a new grade book for a student.
     *
     * @param student the student
     * @param semesters list of semesters with academic records
     */
    public GradeBook(Student student, List<Semester> semesters) {
        this.student = Objects.requireNonNull(student);
        this.semesters = new ArrayList<>(Objects.requireNonNull(semesters));
        this.semesters.sort(Comparator.comparingInt(Semester::getNumber));
    }

    /**
     * Gets the student.
     *
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Gets all semesters.
     *
     * @return unmodifiable list of semesters
     */
    public List<Semester> getSemesters() {
        return Collections.unmodifiableList(semesters);
    }

    /**
     * Calculates current average grade for all studies.
     * Only numeric grades are considered in calculation.
     *
     * @return average grade
     */
    public double calcCurAverage() {
        return semesters.stream()
                .flatMap(semester -> semester.getRecords().stream())
                .filter(record -> record.getGrade().hasNumericValue())
                .mapToInt(record -> record.getGrade().getNumericValue())
                .average()
                .orElse(0.0);
    }

    /**
     * Checks if transfer from fee-paying to scholarship basis is possible.
     * Student must have no satisfactory grades in exams for last two semesters.
     *
     * @return true if student can transfer to scholarship
     */
    public boolean transToBudget() {
        if (!student.isFeePaying()) {
            return false;
        }

        return semesters.stream()
                .sorted(Comparator.comparingInt(Semester::getNumber).reversed())
                .limit(2)
                .flatMap(semester -> semester.getRecords().stream())
                .filter(record -> record.getType().isExam())
                .noneMatch(record -> record.getGrade().isSatisfactory());
    }

    /**
     * Checks if student can get honors diploma (red diploma).
     *
     * @return true if honors diploma is possible
     */
    public boolean getRedDiploma() {
        List<AcademicRecord> allRecords = semesters.stream()
                .flatMap(semester -> semester.getRecords().stream())
                .collect(Collectors.toList());

        boolean hasBadThesis = allRecords.stream()
                .filter(record -> record.getType().isThesis())
                .anyMatch(record -> !record.getGrade().isExcellent());

        if (hasBadThesis) {
            return false;
        }

        List<AcademicRecord> examRecords = allRecords.stream()
                .filter(record -> record.getType().isExamOrDiffCredit())
                .collect(Collectors.toList());

        if (examRecords.isEmpty()) {
            return true;
        }

        boolean hasBadActualGrade = examRecords.stream()
                .anyMatch(record -> record.getGrade().isBad());

        if (hasBadActualGrade) {
            return false;
        }

        List<Grade> actualNumGrades = examRecords.stream()
                .map(AcademicRecord::getGrade)
                .filter(grade -> grade != Grade.NULL_GRADE)
                .filter(Grade::hasNumericValue)
                .collect(Collectors.toList());

        if (actualNumGrades.isEmpty()) {
            return true;
        }

        long currentExcellent = actualNumGrades.stream()
                .filter(Grade::isExcellent)
                .count();

        long countWithoutGrade = examRecords.stream()
                .filter(record -> record.getGrade() == Grade.NULL_GRADE)
                .count();

        long totalCourses = actualNumGrades.size() + countWithoutGrade;
        long projectedExcellent = currentExcellent + countWithoutGrade;

        return (double) projectedExcellent / totalCourses >= 0.75;
    }

    /**
     * Checks if student can get increased scholarship this semester.
     * Student must be on scholarship basis and have all excellent grades in exams.
     *
     * @return true if increased scholarship is possible
     */
    public boolean getScholarship() {
        if (student.isFeePaying()) {
            return false;
        }

        return semesters.stream()
                .max(Comparator.comparingInt(Semester::getNumber))
                .map(semester -> semester.getRecords().stream()
                        .filter(record -> record.getType().isExam())
                        .allMatch(record -> record.getGrade().isExcellent()))
                .orElse(false);
    }

    @Override
    public String toString() {
        return String.format("Зачетная книжка: студент=%s, семестров=%d, средняя оценка=%.2f",
                student.getName(), semesters.size(), calcCurAverage());
    }
}