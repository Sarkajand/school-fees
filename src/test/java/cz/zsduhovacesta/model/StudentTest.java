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
    public static void setup() {
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
    void testEqualsSymmetric() {
        assertEquals(student, sameStudent);
        assertEquals(sameStudent, student);
        assertEquals(student, newStudentWithSameValues);
        assertEquals(newStudentWithSameValues, student);
        assertNotEquals(student, differentStudent);
        assertNotEquals(differentStudent, student);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(student, nullStudent);
        assertNotEquals(nullStudent, differentStudent);
    }

    @Test
    void testEqualsTransitive() {
        assertEquals(student, sameStudent);
        assertEquals(sameStudent, newStudentWithSameValues);
        assertEquals(student, newStudentWithSameValues);
    }

    @Test
    void testHashCode() {
        assertEquals(student.hashCode(), sameStudent.hashCode());
        assertEquals(student.hashCode(), newStudentWithSameValues.hashCode());
        assertNotEquals(student.hashCode(), differentStudent.hashCode());
    }

    @Test
    void countOverPayment() {
        student.countOverPayment();
        assertEquals(0,student.getOverPayment());
    }

    @Test
    void testPositiveOverPayment() {
        Student positiveOverPayment = new Student();
        positiveOverPayment.setShouldPay(10);
        positiveOverPayment.setPayed(100);
        positiveOverPayment.countOverPayment();
        assertEquals(90,positiveOverPayment.getOverPayment());
    }

    @Test
    void testNegativeOverPayment() {
        Student negativeOverpayment = new Student();
        negativeOverpayment.setShouldPay(100);
        negativeOverpayment.setPayed(10);
        negativeOverpayment.countOverPayment();
        assertEquals(0,negativeOverpayment.getOverPayment());
    }

    @Test
    void testOverPaymentWithLastYearSummary() {
        Student positiveOverPayment = new Student();
        positiveOverPayment.setShouldPay(10);
        positiveOverPayment.setPayed(100);
        positiveOverPayment.setSummaryLastYear(100);
        positiveOverPayment.countOverPayment();
        assertEquals(190, positiveOverPayment.getOverPayment());
        Student negativeOverpayment = new Student();
        negativeOverpayment.setShouldPay(100);
        negativeOverpayment.setPayed(1000);
        negativeOverpayment.setSummaryLastYear(-1000);
        negativeOverpayment.countOverPayment();
        assertEquals(0,negativeOverpayment.getOverPayment());
    }

    @Test
    void countUnderPayment() {
        student.countUnderPayment();
        assertEquals(0, student.getUnderPayment());
    }

    @Test
    void testPositiveUnderPayment() {
        Student positiveUnderPayment = new Student();
        positiveUnderPayment.setShouldPay(589);
        positiveUnderPayment.setPayed(12);
        positiveUnderPayment.countUnderPayment();
        assertEquals(577, positiveUnderPayment.getUnderPayment());
    }

    @Test
    void testNegativeUnderPayment() {
        Student negativeUnderPayment = new Student();
        negativeUnderPayment.setShouldPay(145);
        negativeUnderPayment.setPayed(12548);
        negativeUnderPayment.countUnderPayment();
        assertEquals(0, negativeUnderPayment.getUnderPayment());
    }

    @Test
    void testUnderPaymentWithLastYearSummary() {
        Student positiveUnderPayment = new Student();
        positiveUnderPayment.setShouldPay(568);
        positiveUnderPayment.setPayed(25);
        positiveUnderPayment.setSummaryLastYear(-58);
        positiveUnderPayment.countUnderPayment();
        assertEquals(601, positiveUnderPayment.getUnderPayment());
        Student negativeUnderpayment = new Student();
        negativeUnderpayment.setShouldPay(5684);
        negativeUnderpayment.setPayed(367);
        negativeUnderpayment.setSummaryLastYear(45297);
        negativeUnderpayment.countUnderPayment();
        assertEquals(0,negativeUnderpayment.getUnderPayment());
    }
}