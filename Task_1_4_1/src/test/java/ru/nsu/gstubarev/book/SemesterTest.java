package ru.nsu.gstubarev.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SemesterTest {

    @Test
    void testSemesterCreation() {
        AcademicRecord os = new AcademicRecord(
                "ОС", Grade.GOOD, AcademicRecord.AssessmentType.EXAM, 1
        );
        AcademicRecord oop = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1
        );

        List<AcademicRecord> records = Arrays.asList(os, oop);
        Semester semester = new Semester(1, records);

        assertEquals(1, semester.getNumber());
        assertEquals(2, semester.getRecords().size());
        assertTrue(semester.getRecords().contains(os));
        assertTrue(semester.getRecords().contains(oop));
    }

    @Test
    void testAddRecord() {
        Semester semester = new Semester(1, Arrays.asList());

        AcademicRecord record = new AcademicRecord(
                "ООП", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1
        );

        semester.addRecord(record);

        assertEquals(1, semester.getRecords().size());
        assertEquals(record, semester.getRecords().get(0));
    }

    @Test
    void testGetRecordsUnmodifiable() {
        AcademicRecord record = new AcademicRecord(
                "Диффуры", Grade.EXCELLENT, AcademicRecord.AssessmentType.EXAM, 1
        );
        Semester semester = new Semester(1, Arrays.asList(record));

        List<AcademicRecord> records = semester.getRecords();

        assertThrows(UnsupportedOperationException.class, () -> {
            records.add(record);
        });
    }

    @Test
    void testToString() {
        Semester semester = new Semester(3, Arrays.asList());

        String str = semester.toString();
        assertTrue(str.contains("3"));
        assertTrue(str.contains("0"));
    }
}