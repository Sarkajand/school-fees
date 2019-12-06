package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class StudentDaoTest {

    private static final String DB_NAME = "schoolFees.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:src\\test\\resources\\database\\" + DB_NAME;
    private static Connection connection;
    private static StudentDao studentDao;

    @BeforeAll
    public static void setup() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("connection to database failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Closing failed: " + e.getMessage());
        }
    }

    @BeforeEach
    public void startTransaction() {
        try {
            studentDao = new StudentDao(connection);
            connection.beginRequest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void rollback() {
        try {
            connection.rollback();
            studentDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkFirstStudentForQueryAllStudents() {
        List<Student> students = studentDao.queryAllStudents();
        Student student = firstStudentInDatabase();
        assert students.get(0).equals(student);
    }

    private Student firstStudentInDatabase() {
        Student student = new Student();
        student.setVS(254325);
        student.setClassName("PODMOŘSKÝ SVĚT");
        student.setLastName("Novák");
        student.setFirstName("Petr");
        student.setFees(1500.0);
        student.setMotherPhone("731 252 396");
        student.setFatherPhone("756 249 365");
        student.setMotherEmail("matka@email.cz");
        student.setFatherEmail("otec@email.cz");
        student.setNotes("poznamky");
        student.setSchoolStage("MŠ");
        student.setPaymentNotes("poznámky k platbám");
        student.setShouldPay(0.0);
        student.setPayed(0.0);
        student.setSummaryLastYear(0.0);
        return student;
    }

    @Test
    void queryStudentsBySchoolStageFromZs() {
        List<Student> students = studentDao.queryStudentsBySchoolStage("ZŠ");
        for (Student student : students) {
            assert student.getSchoolStage().equals("ZŠ");
        }
    }

    @Test
    void queryStudentsBySchoolStageFromMs() {
        List<Student> students = studentDao.queryStudentsBySchoolStage("MŠ");
        for (Student student : students) {
            assert student.getSchoolStage().equals("MŠ");
        }
    }

    @Test
    void queryStudentsByClass() {
        List<Student> students = studentDao.queryStudentsByClass("PODMOŘSKÝ SVĚT");
        for (Student student : students) {
            assert student.getClassName().equals("PODMOŘSKÝ SVĚT");
        }
    }

    @Test
    void insertStudentWithAllFields() {
        Student studentToInsert = firstStudentInDatabase();
        studentToInsert.setVS(111);
        try {
            studentDao.insertStudent(studentToInsert, 2);
            Student insertedStudent = getStudentFromDatabase();
            assert studentToInsert.equals(insertedStudent);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void insertExistingStudent() {
        Student existingStudent = firstStudentInDatabase();
        try {
            studentDao.insertStudent(existingStudent, 2);
            fail("Should throw exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void insertStudentOnlyWithRequiredValues() {
        Student studentToInsert = new Student();
        studentToInsert.setVS(111);
        studentToInsert.setLastName("lastName");
        studentToInsert.setFirstName("firstName");
        studentToInsert.setSchoolStage("MŠ");
        studentToInsert.setClassName("POHÁDKA");
        try {
            studentDao.insertStudent(studentToInsert, 1);
            Student insertedStudent = getStudentFromDatabase();
            assert studentToInsert.equals(insertedStudent);
        } catch (Exception e) {
            fail();
        }
    }

    private Student getStudentFromDatabase() throws Exception {
        try {
            PreparedStatement findStudentForTest = connection.prepareStatement("SELECT * FROM students_list WHERE VS = 111");
            ResultSet results = findStudentForTest.executeQuery();
            Student student = new Student();
            while (results.next()) {
                student.setClassName(results.getString(1));
                student.setLastName(results.getString(2));
                student.setFirstName(results.getString(3));
                student.setFees(results.getDouble(4));
                student.setVS(results.getInt(5));
                student.setMotherPhone(results.getString(6));
                student.setFatherPhone(results.getString(7));
                student.setMotherEmail(results.getString(8));
                student.setFatherEmail(results.getString(9));
                student.setNotes(results.getString(10));
                student.setSchoolStage(results.getString(11));
                student.setPaymentNotes(results.getString(12));
                student.setShouldPay(results.getDouble(13));
                student.setPayed(results.getDouble(14));
                student.setSummaryLastYear(results.getDouble(15));
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Test
    void deleteFirstStudent() {
        Student studentToDelete = firstStudentInDatabase();
        int vs = studentToDelete.getVS();
        try {
            List<Student> students = studentDao.queryAllStudents();
            assertEquals(students.get(0), studentToDelete);
            studentDao.deleteStudent(vs);
            students = studentDao.queryAllStudents();
            assertNotEquals(students.get(0), studentToDelete);
            studentDao.insertStudent(studentToDelete, 2);
            students = studentDao.queryAllStudents();
            assertEquals(students.get(0), studentToDelete);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void insertAndDeleteStudent() {
        Student studentToDelete = new Student();
        studentToDelete.setVS(111);
        studentToDelete.setLastName("lastName");
        studentToDelete.setFirstName("firstName");
        studentToDelete.setSchoolStage("MŠ");
        studentToDelete.setClassName("POHÁDKA");
        try {
            List<Student> students = studentDao.queryAllStudents();
            int sizeBeforeInsert = students.size();
            studentDao.insertStudent(studentToDelete, 1);
            students = studentDao.queryAllStudents();
            int sizeAfterInsert = students.size();
            assertEquals(--sizeAfterInsert, sizeBeforeInsert);
            studentDao.deleteStudent(111);
            students = studentDao.queryAllStudents();
            int sizeAfterDelete = students.size();
            assertEquals(sizeAfterDelete, sizeBeforeInsert);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void tryDeleteNotExistingStudent() {
        try {
            studentDao.deleteStudent(0);
            fail();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}