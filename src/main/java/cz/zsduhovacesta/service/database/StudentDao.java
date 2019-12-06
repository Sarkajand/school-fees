package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentDao {

    final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_STUDENTS_VS = "VS";
    public static final String COLUMN_STUDENTS_LAST_NAME = "last_name";
    public static final String COLUMN_STUDENTS_FIRST_NAME = "first_name";
    public static final String COLUMN_STUDENTS_CLASS = "class";
    public static final String COLUMN_STUDENTS_FEES = "fees";
    public static final String COLUMN_STUDENTS_MOTHER_PHONE = "mother_phone";
    public static final String COLUMN_STUDENTS_FATHER_PHONE = "father_phone";
    public static final String COLUMN_STUDENTS_MOTHER_EMAIL = "mother_email";
    public static final String COLUMN_STUDENTS_FATHER_EMAIL = "father_email";
    public static final String COLUMN_STUDENTS_NOTES = "notes";
    public static final String COLUMN_STUDENTS_PAYMENT_NOTES = "payment_notes";
    public static final String COLUMN_STUDENTS_SHOULD_PAY = "should_pay";
    public static final String COLUMN_STUDENTS_PAYED = "payed";
    public static final String COLUMN_STUDENTS_SUMMARY_LAST_YEAR = "summary_last_year";

    public static final int INDEX_STUDENT_VS = 1;
    public static final int INDEX_STUDENT_LAST_NAME = 2;
    public static final int INDEX_STUDENT_FIRST_NAME = 3;
    public static final int INDEX_STUDENT_CLASS = 4;
    public static final int INDEX_STUDENT_FEES = 5;
    public static final int INDEX_STUDENT_MOTHER_PHONE = 6;
    public static final int INDEX_STUDENT_FATHER_PHONE = 7;
    public static final int INDEX_STUDENT_MOTHER_EMAIL = 8;
    public static final int INDEX_STUDENT_FATHER_EMAIL = 9;
    public static final int INDEX_STUDENT_NOTES = 10;
    public static final int INDEX_STUDENT_PAYMENT_NOTES = 11;
    public static final int INDEX_STUDENT_SHOULD_PAY = 12;
    public static final int INDEX_STUDENT_PAYED = 13;

    public static final String VIEW_STUDENT_LIST = "students_list";
    public static final String COLUMN_STUDENT_LIST_STAGE = "stage";
    public static final String COLUMN_STUDENTS_LIST_CLASS = "class_name";
    public static final int INDEX_STUDENT_LIST_CLASS_NAME = 1;
    public static final int INDEX_STUDENT_LIST_LAST_NAME = 2;
    public static final int INDEX_STUDENT_LIST_FIRST_NAME = 3;
    public static final int INDEX_STUDENT_LIST_FEES = 4;
    public static final int INDEX_STUDENT_LIST_VS = 5;
    public static final int INDEX_STUDENT_LIST_MOTHER_PHONE = 6;
    public static final int INDEX_STUDENT_LIST_FATHER_PHONE = 7;
    public static final int INDEX_STUDENT_LIST_MOTHER_EMAIL = 8;
    public static final int INDEX_STUDENT_LIST_FATHER_EMAIL = 9;
    public static final int INDEX_STUDENT_LIST_NOTES = 10;
    public static final int INDEX_STUDENT_LIST_STAGE = 11;
    public static final int INDEX_STUDENT_LIST_PAYMENT_NOTES = 12;
    public static final int INDEX_STUDENT_LIST_SHOULD_PAY = 13;
    public static final int INDEX_STUDENT_LIST_PAYED = 14;
    public static final int INDEX_STUDENT_LIST_SUMMARY_LAST_YEAR = 15;

    public static final String QUERY_ALL_STUDENTS =
            "SELECT * FROM " + VIEW_STUDENT_LIST;
    public static final String QUERY_STUDENTS_BY_SCHOOL_STAGE =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + COLUMN_STUDENT_LIST_STAGE + " = ?";
    public static final String QUERY_STUDENTS_BY_CLASS =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + COLUMN_STUDENTS_LIST_CLASS + " = ?";
//    todo - zatim nemam metodu pro hledání podle vs, nevim jestli jí budu potřebovat - případně nezapomenout smazat
    public static final String QUERY_STUDENT_BY_VS =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + COLUMN_STUDENTS_VS + " = ?";
    public static final String INSERT_STUDENT =
            "INSERT INTO " + TABLE_STUDENTS + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_STUDENT =
            "DELETE FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENTS_VS + " = ?";


    private PreparedStatement queryAllStudents;
    private PreparedStatement queryStudentsBySchoolStage;
    private PreparedStatement queryStudentsByClass;
    private PreparedStatement queryStudentByVs;
    private PreparedStatement insertStudent;
    private PreparedStatement deleteStudent;


    StudentDao(Connection connection) throws SQLException {
        try {
            queryAllStudents = connection.prepareStatement(QUERY_ALL_STUDENTS);
            queryStudentsBySchoolStage = connection.prepareStatement(QUERY_STUDENTS_BY_SCHOOL_STAGE);
            queryStudentsByClass = connection.prepareStatement(QUERY_STUDENTS_BY_CLASS);
            queryStudentByVs = connection.prepareStatement(QUERY_STUDENT_BY_VS);
            insertStudent = connection.prepareStatement(INSERT_STUDENT);
            deleteStudent = connection.prepareStatement(DELETE_STUDENT);
        } catch (SQLException e) {
            System.out.println("Couldn't create prepared statement for StudentDao");
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (queryAllStudents != null) {
                queryAllStudents.close();
            }
            if (queryStudentsBySchoolStage != null) {
                queryStudentsBySchoolStage.close();
            }
            if (queryStudentsByClass != null) {
                queryStudentsByClass.close();
            }
            if (queryStudentByVs != null) {
                queryStudentByVs.close();
            }
            if (insertStudent != null) {
                insertStudent.close();
            }
            if (deleteStudent != null) {
                deleteStudent.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close prepared statement in StudentDao: " + e.getMessage());
            throw e;
        }
    }

    public List<Student> queryAllStudents() {
        try {
            ResultSet results = queryAllStudents.executeQuery();
            if (results == null) {
                return Collections.emptyList();
            } else {
                return setStudents(results);
            }
        } catch (SQLException e) {
            System.out.println("Query all students failed: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<Student> setStudents(ResultSet results) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (results.next()) {
            Student student = setStudent(results);
            students.add(student);
        }
        return students;
    }

    private Student setStudent(ResultSet results) throws SQLException {
        Student student = new Student();
        student.setClassName(results.getString(INDEX_STUDENT_LIST_CLASS_NAME));
        student.setLastName(results.getString(INDEX_STUDENT_LIST_LAST_NAME));
        student.setFirstName(results.getString(INDEX_STUDENT_LIST_FIRST_NAME));
        student.setFees(results.getDouble(INDEX_STUDENT_LIST_FEES));
        student.setVS(results.getInt(INDEX_STUDENT_LIST_VS));
        student.setMotherPhone(results.getString(INDEX_STUDENT_LIST_MOTHER_PHONE));
        student.setFatherPhone(results.getString(INDEX_STUDENT_LIST_FATHER_PHONE));
        student.setMotherEmail(results.getString(INDEX_STUDENT_LIST_MOTHER_EMAIL));
        student.setFatherEmail(results.getString(INDEX_STUDENT_LIST_FATHER_EMAIL));
        student.setNotes(results.getString(INDEX_STUDENT_LIST_NOTES));
        student.setSchoolStage(results.getString(INDEX_STUDENT_LIST_STAGE));
        student.setPaymentNotes(results.getString(INDEX_STUDENT_LIST_PAYMENT_NOTES));
        student.setShouldPay(results.getDouble(INDEX_STUDENT_LIST_SHOULD_PAY));
        student.setPayed(results.getDouble(INDEX_STUDENT_LIST_PAYED));
        student.setSummaryLastYear(results.getDouble(INDEX_STUDENT_LIST_SUMMARY_LAST_YEAR));
        return student;
    }

    public List<Student> queryStudentsBySchoolStage(String schoolStage) {
        try {
            queryStudentsBySchoolStage.setString(1, schoolStage);
            ResultSet results = queryStudentsBySchoolStage.executeQuery();
            if (results == null) {
                return Collections.emptyList();
            } else {
                return setStudents(results);
            }
        } catch (SQLException e) {
            System.out.println("Query students by school stage failed: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Student> queryStudentsByClass(String className) {
        try {
            queryStudentsByClass.setString(1, className);
            ResultSet results = queryStudentsByClass.executeQuery();
            if (results == null) {
                return Collections.emptyList();
            } else {
                return setStudents(results);
            }
        } catch (SQLException e) {
            System.out.println("Query students by class failed: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void insertStudent(Student student, int classId) throws Exception {
        setValuesForInsertingStudent(student, classId);

        int affectedRecords = insertStudent.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Inserting student failed");
        }
    }

    private void setValuesForInsertingStudent(Student student, int classId) throws SQLException {
        insertStudent.setInt(INDEX_STUDENT_VS, student.getVS());
        insertStudent.setString(INDEX_STUDENT_LAST_NAME, student.getLastName());
        insertStudent.setString(INDEX_STUDENT_FIRST_NAME, student.getFirstName());
        insertStudent.setInt(INDEX_STUDENT_CLASS, classId);
        insertStudent.setDouble(INDEX_STUDENT_FEES, student.getFees());
        insertStudent.setString(INDEX_STUDENT_MOTHER_PHONE, student.getMotherPhone());
        insertStudent.setString(INDEX_STUDENT_FATHER_PHONE, student.getFatherPhone());
        insertStudent.setString(INDEX_STUDENT_MOTHER_EMAIL, student.getMotherEmail());
        insertStudent.setString(INDEX_STUDENT_FATHER_EMAIL, student.getFatherEmail());
        insertStudent.setString(INDEX_STUDENT_NOTES, student.getNotes());
        insertStudent.setString(INDEX_STUDENT_PAYMENT_NOTES, student.getPaymentNotes());
        insertStudent.setDouble(INDEX_STUDENT_SHOULD_PAY, student.getShouldPay());
        insertStudent.setDouble(INDEX_STUDENT_PAYED, student.getPayed());
    }

    public void deleteStudent(int VS) throws Exception {
        deleteStudent.setInt(1, VS);
        int affectedRecords = deleteStudent.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Deleting student failed");
        }
    }
}
