package cz.zsduhovacesta.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private static Student student;
    private static Student sameStudent;
    private static Student newStudentWithSameValues;
    private static Student differentStudent;
    private static Student nullStudent;

    @BeforeAll
    public static void setup () {
        student = new Student();
        sameStudent = student;
        newStudentWithSameValues = new Student();
        differentStudent = new Student();
        differentStudent.setVS(111);
        nullStudent = null;
    }


    @Test
    void testEqualsReflexive() {
        assertEquals(student, student);
        assertEquals(student, sameStudent);
    }

    @Test
    void testEqualsSymmetric () {
        assertEquals(student, sameStudent);
        assertEquals(sameStudent, student);
        assertEquals(student, newStudentWithSameValues);
        assertEquals(newStudentWithSameValues, student);
        assertNotEquals(student, differentStudent);
        assertNotEquals(differentStudent, student);
    }

    @Test
    void testEqualsNull () {
        assertNotEquals(student, nullStudent);
        assertNotEquals(nullStudent, differentStudent);
    }

    @Test
    void testEqualsTransitive () {
        assertEquals(student, sameStudent);
        assertEquals(sameStudent, newStudentWithSameValues);
        assertEquals(student, newStudentWithSameValues);
    }

    @Test
    void testHashCode () {
        assertEquals(student.hashCode(), sameStudent.hashCode());
        assertEquals(student.hashCode(), newStudentWithSameValues.hashCode());
        assertNotEquals(student.hashCode(), differentStudent.hashCode());
    }
}