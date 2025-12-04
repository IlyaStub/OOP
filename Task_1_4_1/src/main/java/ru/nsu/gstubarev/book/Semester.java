package ru.nsu.gstubarev.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents an academic semester with records of student achievements.
 */
public class Semester {
    private final int number;
    private final List<AcademicRecord> records;

    /**
     * Creates a new semester with records.
     *
     * @param number semester number
     * @param records list of academic records for this semester
     */
    public Semester(int number, List<AcademicRecord> records) {
        this.number = number;
        this.records = new ArrayList<>(Objects.requireNonNull(records));
    }

    /**
     * Gets semester number.
     *
     * @return semester number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets all academic records for this semester.
     *
     * @return unmodifiable list of academic records
     */
    public List<AcademicRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    /**
     * Adds a new academic record to this semester.
     *
     * @param record academic record to add
     */
    public void addRecord(AcademicRecord record) {
        records.add(record);
    }

    /**
     * Override method toString
     *
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Семестр: номер=%d, записей=%d}", number, records.size());
    }
}