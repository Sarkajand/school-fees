package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StudentTest {
    private Student student = new Student();
    private Student sameStudent = student;
    private Student newStudentWithSameValues = new Student();
    private Student differentStudent = createDifferentStudent();
    private Student nullStudent = null;

    private static Student createDifferentStudent() {
        Student student = new Student();
        student.setVS(111);
        return student;
    }

    @Test
    public void testEqualsReflexive() {
        assertEquals(student, student);
        assertEquals(student, sameStudent);
    }

    @Test
    public void testEqualsSymmetric() {
        assertEquals(student, sameStudent);
        assertEquals(sameStudent, student);
        assertEquals(student, newStudentWithSameValues);
        assertEquals(newStudentWithSameValues, student);
        assertNotEquals(student, differentStudent);
        assertNotEquals(differentStudent, student);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(student, nullStudent);
        assertNotEquals(nullStudent, differentStudent);
    }

    @Test
    public void testEqualsTransitive() {
        assertEquals(student, sameStudent);
        assertEquals(sameStudent, newStudentWithSameValues);
        assertEquals(student, newStudentWithSameValues);
    }

    @Test
    public void testHashCode() {
        assertEquals(student.hashCode(), sameStudent.hashCode());
        assertEquals(student.hashCode(), newStudentWithSameValues.hashCode());
        assertNotEquals(student.hashCode(), differentStudent.hashCode());
    }

    @Test
    public void testCountOverPayment() {
        student.countOverPayment();
        assertEquals(0, student.getOverPayment());
    }

    @Test
    public void testPositiveOverPayment() {
        Student student = new Student();
        student.setShouldPay(10);
        student.setPayed(100);
        student.countOverPayment();
        assertEquals(90, student.getOverPayment());
    }

    @Test
    public void testNegativeOverPaymentReturnNull() {
        Student student = new Student();
        student.setShouldPay(100);
        student.setPayed(10);
        student.countOverPayment();
        assertEquals(0, student.getOverPayment());
    }

    @Test
    public void testPositiveOverPaymentWithLastYearSummary() {
        Student student = new Student();
        student.setShouldPay(10);
        student.setPayed(100);
        student.setSummaryLastYear(100);
        student.countOverPayment();
        assertEquals(190, student.getOverPayment());
    }

    @Test
    public void testNegativeOverPaymentWithLastYearSummary() {
        Student student = new Student();
        student.setShouldPay(100);
        student.setPayed(1000);
        student.setSummaryLastYear(-1000);
        student.countOverPayment();
        assertEquals(0, student.getOverPayment());
    }

    @Test
    public void testCountUnderPayment() {
        student.countUnderPayment();
        assertEquals(0, student.getUnderPayment());
    }

    @Test
    public void testPositiveUnderPayment() {
        Student student = new Student();
        student.setShouldPay(589);
        student.setPayed(12);
        student.countUnderPayment();
        assertEquals(577, student.getUnderPayment());
    }

    @Test
    public void testNegativeUnderPaymentReturnNull() {
        Student student = new Student();
        student.setShouldPay(145);
        student.setPayed(12548);
        student.countUnderPayment();
        assertEquals(0, student.getUnderPayment());
    }

    @Test
    public void testPositiveUnderPaymentWithLastYearSummary() {
        Student student = new Student();
        student.setShouldPay(568);
        student.setPayed(25);
        student.setSummaryLastYear(-58);
        student.countUnderPayment();
        assertEquals(601, student.getUnderPayment());
    }

    @Test
    public void testNegativeUnderPaymentWithLastYearSummary() {
        Student student = new Student();
        student.setShouldPay(5684);
        student.setPayed(367);
        student.setSummaryLastYear(45297);
        student.countUnderPayment();
        assertEquals(0, student.getUnderPayment());
    }
}