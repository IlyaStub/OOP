package ru.nsu.gstubarev.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void testStudentIsFee() {
        Student student = new Student("Илья Стубарев", true);

        assertEquals("Илья Стубарев", student.getName());
        assertTrue(student.isFeePaying());
    }

    @Test
    void testStudentIsNotFee() {
        Student student = new Student("Илья Стубарев", false);

        assertEquals("Илья Стубарев", student.getName());
        assertFalse(student.isFeePaying());
    }

    @Test
    void testToString() {
        Student payingStudent = new Student("Илья1", true);
        Student budgetStudent = new Student("Илья2", false);

        assertTrue(payingStudent.toString().contains("Илья1"));
        assertTrue(payingStudent.toString().contains("на платке"));
        assertTrue(budgetStudent.toString().contains("не на платке"));
    }
}