package ru.nsu.gstubarev.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class GradeBookTest {

    @Test
    void testGradeBookCreation() {
        Student student = new Student("Илья", true);
        Semester semester = new Semester(1, Arrays.asList());
        GradeBook gradeBook = new GradeBook(student, Arrays.asList(semester));

        assertEquals(student, gradeBook.getStudent());
        assertEquals(1, gradeBook.getSemesters().size());
        assertEquals(semester, gradeBook.getSemesters().get(0));
    }

    @Test
    void testCalcCurAverage() {
        Student student = new Student("Илья", false);

        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord os = new AcademicRecord(
                "ОС", Grade.GOOD, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord history = new AcademicRecord(
                "История", Grade.PASS, AcademicRecord.AssessmentType.CREDIT, 1);

        List<AcademicRecord> records = Arrays.asList(oop, os, history);
        Semester semester = new Semester(1, records);
        GradeBook gradeBook = new GradeBook(student, Arrays.asList(semester));

        assertEquals(4.5, gradeBook.calcCurAverage(), 0.01);
    }

    @Test
    void testCalcCurAverageEmpty() {
        Student student = new Student("Илья", false);
        Semester semester = new Semester(1, Arrays.asList());
        GradeBook gradeBook = new GradeBook(student, Arrays.asList(semester));

        assertEquals(0.0, gradeBook.calcCurAverage(), 0.01);
    }

    @Test
    void testTransToBudgetEligible() {
        Student payingStudent = new Student("Илья", true);

        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord os = new AcademicRecord(
                "ОС", Grade.GOOD, AcademicRecord.AssessmentType.EXAM, 1);

        List<AcademicRecord> records = Arrays.asList(oop, os);
        Semester semester = new Semester(1, records);
        GradeBook gradeBook = new GradeBook(payingStudent, Arrays.asList(semester));

        assertTrue(gradeBook.transToBudget());
    }

    @Test
    void testTransToBudgetNotEligible() {
        Student payingStudent = new Student("Илья Данунеможетонплатноучится", true);

        AcademicRecord os = new AcademicRecord(
                "ОС", Grade.SATISFACTORY, AcademicRecord.AssessmentType.EXAM, 1);

        List<AcademicRecord> records = Arrays.asList(os);
        Semester semester = new Semester(1, records);
        GradeBook gradeBook = new GradeBook(payingStudent, Arrays.asList(semester));

        assertFalse(gradeBook.transToBudget());
    }

    @Test
    void testTransToBudgetAlreadyBudget() {
        Student budgetStudent = new Student("Илья", false);
        Semester semester = new Semester(1, Arrays.asList());
        GradeBook gradeBook = new GradeBook(budgetStudent, Arrays.asList(semester));

        assertFalse(gradeBook.transToBudget());
    }

    @Test
    void testTransToBudgetDiffCreditAllowed() {
        Student payingStudent = new Student("Илья Илиможет", true);

        AcademicRecord os = new AcademicRecord(
                "ОС", Grade.SATISFACTORY,
                AcademicRecord.AssessmentType.DIFFERENTIATED_CREDIT, 1);
        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);

        List<AcademicRecord> records = Arrays.asList(os, oop);
        Semester semester = new Semester(1, records);
        GradeBook gradeBook = new GradeBook(payingStudent, Arrays.asList(semester));

        assertTrue(gradeBook.transToBudget());
    }

    @Test
    void testGetScholarshipEligible() {
        Student budgetStudent = new Student("Илья", false);

        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord diff = new AcademicRecord(
                "Диффуры", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);

        List<AcademicRecord> records = Arrays.asList(oop, diff);
        Semester semester = new Semester(1, records);
        GradeBook gradeBook = new GradeBook(budgetStudent, Arrays.asList(semester));

        assertTrue(gradeBook.getScholarship());
    }

    @Test
    void testGetScholarshipNotEligible() {
        Student budgetStudent = new Student("Илюха", false);

        AcademicRecord os = new AcademicRecord(
                "ОС", Grade.GOOD, AcademicRecord.AssessmentType.EXAM, 1);

        List<AcademicRecord> records = Arrays.asList(os);
        Semester semester = new Semester(1, records);
        GradeBook gradeBook = new GradeBook(budgetStudent, Arrays.asList(semester));

        assertFalse(gradeBook.getScholarship());
    }

    @Test
    void testGetScholarshipPayingStudent() {
        Student payingStudent = new Student("ЭХХ Илья", true);
        Semester semester = new Semester(1, Arrays.asList());
        GradeBook gradeBook = new GradeBook(payingStudent, Arrays.asList(semester));

        assertFalse(gradeBook.getScholarship());
    }

    @Test
    void testGetRedDiplomaEligible() {
        Student student = new Student("Илья Опять", false);

        AcademicRecord diploma = new AcademicRecord(
                "Диплом", Grade.EXCELLENT,
                AcademicRecord.AssessmentType.FINAL_QUALIFICATION_WORK_DEFENSE, 4);
        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord physics = new AcademicRecord(
                "Физика", Grade.EXCELLENT,
                AcademicRecord.AssessmentType.DIFFERENTIATED_CREDIT, 1);
        AcademicRecord math = new AcademicRecord(
                "Математика", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord imperativka = new AcademicRecord(
                "Императивка", Grade.GOOD, AcademicRecord.AssessmentType.EXAM, 2);

        Semester semester1 = new Semester(1, Arrays.asList(oop, physics, math));
        Semester semester2 = new Semester(2, Arrays.asList(imperativka));
        Semester semester4 = new Semester(4, Arrays.asList(diploma));

        GradeBook gradeBook = new GradeBook(student,
                Arrays.asList(semester1, semester2, semester4));

        assertTrue(gradeBook.getRedDiploma());
    }

    @Test
    void testGetRedDiplomaNoThesis() {
        Student student = new Student("Ilya", false);

        AcademicRecord oop = new AcademicRecord(
                "OOP", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);

        List<AcademicRecord> records = Arrays.asList(oop);
        Semester semester = new Semester(1, records);
        GradeBook gradeBook = new GradeBook(student, Arrays.asList(semester));

        assertFalse(gradeBook.getRedDiploma());
    }

    @Test
    void testGetRedDiplomaWithSatisfactory() {
        Student student = new Student("Иван", false);

        AcademicRecord diploma = new AcademicRecord(
                "Диплом", Grade.EXCELLENT,
                AcademicRecord.AssessmentType.FINAL_QUALIFICATION_WORK_DEFENSE, 4);
        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.SATISFACTORY, AcademicRecord.AssessmentType.EXAM, 1);

        Semester semester1 = new Semester(1, Arrays.asList(oop));
        Semester semester4 = new Semester(4, Arrays.asList(diploma));

        GradeBook gradeBook = new GradeBook(student,
                Arrays.asList(semester1, semester4));

        assertFalse(gradeBook.getRedDiploma());
    }

    @Test
    void testGetRedDiplomaNotEnoughExcellent() {
        Student student = new Student("Иван Неожидали?", false);

        AcademicRecord diploma = new AcademicRecord(
                "Диплом", Grade.EXCELLENT,
                AcademicRecord.AssessmentType.FINAL_QUALIFICATION_WORK_DEFENSE, 4);
        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord physics = new AcademicRecord(
                "Физика", Grade.GOOD, AcademicRecord.AssessmentType.EXAM, 1);
        AcademicRecord imperativka = new AcademicRecord(
                "Императивка", Grade.GOOD, AcademicRecord.AssessmentType.EXAM, 2);

        Semester semester1 = new Semester(1, Arrays.asList(oop, physics));
        Semester semester2 = new Semester(2, Arrays.asList(imperativka));
        Semester semester4 = new Semester(4, Arrays.asList(diploma));

        GradeBook gradeBook = new GradeBook(student,
                Arrays.asList(semester1, semester2, semester4));

        assertFalse(gradeBook.getRedDiploma());
    }

    @Test
    void testToString() {
        Student student = new Student("Илья", true);
        Semester semester = new Semester(1, Arrays.asList());
        GradeBook gradeBook = new GradeBook(student, Arrays.asList(semester));

        String str = gradeBook.toString();
        assertTrue(str.contains("Илья"));
        assertTrue(str.contains("1"));
        assertTrue(str.contains("0,00") || str.contains("0.00"));
    }
}