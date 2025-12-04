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
     * Requirements: excellent thesis, no satisfactory grades,
     * at least 75% excellent grades.
     *
     * @return true if honors diploma is possible
     */
    public boolean getRedDiploma() {
        return hasExcellentThesis()
                && hasNoBadFinalGrades()
                && hasExcellentGradesPercentage();
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

    private boolean hasExcellentThesis() {
        return semesters.stream()
                .flatMap(semester -> semester.getRecords().stream())
                .filter(record -> record.getType().isThesis())
                .anyMatch(record -> record.getGrade().isExcellent());
    }

    private boolean hasNoBadFinalGrades() {
        return semesters.stream()
                .flatMap(semester -> semester.getRecords().stream())
                .filter(record -> record.getType().isExamOrDiffCredit())
                .noneMatch(record -> record.getGrade().isBad());
    }

    private boolean hasExcellentGradesPercentage() {
        List<Grade> gradesForDiploma = semesters.stream()
                .flatMap(semester -> semester.getRecords().stream())
                .filter(record -> record.getType().isExamOrDiffCredit())
                .map(AcademicRecord::getGrade)
                .collect(Collectors.toList());

        if (gradesForDiploma.isEmpty()) {
            return false;
        }

        long excellentCount = gradesForDiploma.stream()
                .filter(Grade::isExcellent)
                .count();

        return (double) excellentCount / gradesForDiploma.size() >= 0.75;
    }

    @Override
    public String toString() {
        return String.format("Зачетная книжка: студент=%s, семестров=%d, средняя оценка=%.2f",
                student.getName(), semesters.size(), calcCurAverage());
    }
}