package ru.nsu.gstubarev.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AcademicRecordTest {

    @Test
    void testAcademicRecordCreation() {
        AcademicRecord record = new AcademicRecord(
                "ООП",
                Grade.EXCELLENT,
                AcademicRecord.AssessmentType.EXAM,
                1
        );

        assertEquals("ООП", record.getCourseName());
        assertEquals(Grade.EXCELLENT, record.getGrade());
        assertEquals(AcademicRecord.AssessmentType.EXAM, record.getType());
        assertEquals(1, record.getSemester());
    }

    @Test
    void testAssessmentTypeMethods() {
        assertTrue(AcademicRecord.AssessmentType.EXAM.isExam());
        assertFalse(AcademicRecord.AssessmentType.DIFFERENTIATED_CREDIT.isExam());

        assertTrue(AcademicRecord.AssessmentType.EXAM.isExamOrDiffCredit());
        assertTrue(AcademicRecord.AssessmentType.DIFFERENTIATED_CREDIT.isExamOrDiffCredit());
        assertFalse(AcademicRecord.AssessmentType.CREDIT.isExamOrDiffCredit());

        assertTrue(AcademicRecord.AssessmentType.FINAL_QUALIFICATION_WORK_DEFENSE.isThesis());
        assertFalse(AcademicRecord.AssessmentType.EXAM.isThesis());
    }

    @Test
    void testAssessmentTypeDescription() {
        assertEquals("Экзамен", AcademicRecord.AssessmentType.EXAM.getDescription());
        assertEquals("Зачет", AcademicRecord.AssessmentType.CREDIT.getDescription());
        assertEquals("Дифференцированный зачет",
                AcademicRecord.AssessmentType.DIFFERENTIATED_CREDIT.getDescription());
    }

    @Test
    void testToString() {
        AcademicRecord record = new AcademicRecord(
                "ОС",
                Grade.GOOD,
                AcademicRecord.AssessmentType.EXAM,
                2
        );

        String str = record.toString();
        assertTrue(str.contains("ОС"));
        assertTrue(str.contains("хорошо"));
        assertTrue(str.contains("Экзамен"));
        assertTrue(str.contains("2"));
    }

    @Test
    void testEqualsAndHashCode() {
        AcademicRecord record1 = new AcademicRecord(
                "ООП",
                Grade.EXCELLENT,
                AcademicRecord.AssessmentType.EXAM,
                1
        );

        AcademicRecord record2 = new AcademicRecord(
                "ООП",
                Grade.EXCELLENT,
                AcademicRecord.AssessmentType.EXAM,
                1
        );

        AcademicRecord record3 = new AcademicRecord(
                "ОС",
                Grade.EXCELLENT,
                AcademicRecord.AssessmentType.EXAM,
                1
        );

        assertEquals(record1, record2);
        assertNotEquals(record1, record3);
        assertEquals(record1.hashCode(), record2.hashCode());
    }
}