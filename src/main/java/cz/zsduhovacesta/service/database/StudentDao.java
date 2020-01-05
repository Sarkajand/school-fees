package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentDao {

    final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_STUDENTS_VS = "VS";
    public static final String COLUMN_STUDENTS_SHOULD_PAY = "should_pay";
    public static final String COLUMN_STUDENTS_PAYED = "payed";


    public static final String VIEW_STUDENT_LIST = "students_list";
    public static final String COLUMN_STUDENT_LIST_STAGE = "stage";
    public static final String COLUMN_STUDENTS_LIST_CLASS = "class_name";

    public static final String QUERY_ALL_STUDENTS =
            "SELECT * FROM " + VIEW_STUDENT_LIST;
    public static final String QUERY_STUDENTS_BY_SCHOOL_STAGE =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + COLUMN_STUDENT_LIST_STAGE + " = ?";
    public static final String QUERY_STUDENTS_BY_CLASS =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + COLUMN_STUDENTS_LIST_CLASS + " = ?";
    public static final String QUERY_STUDENT_BY_VS =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + COLUMN_STUDENTS_VS + " = ?";
    public static final String INSERT_STUDENT =
            "INSERT INTO " + TABLE_STUDENTS + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_STUDENT =
            "DELETE FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENTS_VS + " = ?";
    public static final String UPDATE_SHOULD_PAY =
            "UPDATE " + TABLE_STUDENTS + " SET " + COLUMN_STUDENTS_SHOULD_PAY + " = ? WHERE " + COLUMN_STUDENTS_VS + " = ?";
    public static final String UPDATE_PAYED =
            "UPDATE " + TABLE_STUDENTS + " SET " + COLUMN_STUDENTS_PAYED + " = ? WHERE " + COLUMN_STUDENTS_VS + " = ?";


    private PreparedStatement queryAllStudents;
    private PreparedStatement queryStudentsBySchoolStage;
    private PreparedStatement queryStudentsByClass;
    private PreparedStatement queryStudentByVs;
    private PreparedStatement insertStudent;
    private PreparedStatement deleteStudent;
    private PreparedStatement updateShouldPay;
    private PreparedStatement updatePayed;


    StudentDao(Connection connection) throws SQLException {
        try {
            queryAllStudents = connection.prepareStatement(QUERY_ALL_STUDENTS);
            queryStudentsBySchoolStage = connection.prepareStatement(QUERY_STUDENTS_BY_SCHOOL_STAGE);
            queryStudentsByClass = connection.prepareStatement(QUERY_STUDENTS_BY_CLASS);
            queryStudentByVs = connection.prepareStatement(QUERY_STUDENT_BY_VS);
            insertStudent = connection.prepareStatement(INSERT_STUDENT);
            deleteStudent = connection.prepareStatement(DELETE_STUDENT);
            updateShouldPay = connection.prepareStatement(UPDATE_SHOULD_PAY);
            updatePayed = connection.prepareStatement(UPDATE_PAYED);
        } catch (SQLException e) {
            logger.error("Couldn't create prepared statements for StudentDao", e);
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
            if (updateShouldPay != null) {
                updateShouldPay.close();
            }
            if (updatePayed != null) {
                updatePayed.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close prepared statement in StudentDao: ", e);
            throw e;
        }
    }

    public List<Student> queryAllStudents() {
        try {
            ResultSet results = queryAllStudents.executeQuery();
            return setStudents(results);
        } catch (SQLException e) {
            logger.warn("Query all students failed: " + e.getMessage());
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
        student.setClassName(results.getString(1));
        student.setClassId(results.getInt(2));
        student.setLastName(results.getString(3));
        student.setFirstName(results.getString(4));
        student.setFees(results.getInt(5));
        student.setVS(results.getInt(6));
        student.setMotherPhone(results.getString(7));
        student.setFatherPhone(results.getString(8));
        student.setMotherEmail(results.getString(9));
        student.setFatherEmail(results.getString(10));
        student.setNotes(results.getString(11));
        student.setSchoolStage(results.getString(12));
        student.setPaymentNotes(results.getString(13));
        student.setShouldPay(results.getInt(14));
        student.setPayed(results.getInt(15));
        student.setSummaryLastYear(results.getInt(16));
        student.countOverPayment();
        student.countUnderPayment();
        return student;
    }

    public List<Student> queryStudentsBySchoolStage(String schoolStage) {
        try {
            queryStudentsBySchoolStage.setString(1, schoolStage);
            ResultSet results = queryStudentsBySchoolStage.executeQuery();
            return setStudents(results);
        } catch (SQLException e) {
            logger.warn("Query students by school stage failed: ", e);
            return Collections.emptyList();
        }
    }

    public List<Student> queryStudentsByClass(String className) {
        try {
            queryStudentsByClass.setString(1, className);
            ResultSet results = queryStudentsByClass.executeQuery();
            return setStudents(results);
        } catch (SQLException e) {
            logger.warn("Query students by class failed: ", e);
            return Collections.emptyList();
        }
    }

    public Student queryStudentByVs(int vs) {
        try {
            queryStudentByVs.setInt(1, vs);
            ResultSet results = queryStudentByVs.executeQuery();
            return setStudent(results);
        } catch (SQLException e) {
            logger.warn("query student by vs failed:", e);
            return null;
        }
    }

    public void insertStudent(Student student) throws Exception {
        setValuesForInsertingStudent(student);

        int affectedRecords = insertStudent.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Inserting student failed");
        }
    }

    private void setValuesForInsertingStudent(Student student) throws SQLException {
        insertStudent.setInt(1, student.getVS());
        insertStudent.setString(2, student.getLastName());
        insertStudent.setString(3, student.getFirstName());
        insertStudent.setInt(4, student.getClassId());
        insertStudent.setDouble(5, student.getFees());
        insertStudent.setString(6, student.getMotherPhone());
        insertStudent.setString(7, student.getFatherPhone());
        insertStudent.setString(8, student.getMotherEmail());
        insertStudent.setString(9, student.getFatherEmail());
        insertStudent.setString(10, student.getNotes());
        insertStudent.setString(11, student.getPaymentNotes());
        insertStudent.setDouble(12, student.getShouldPay());
        insertStudent.setDouble(13, student.getPayed());
    }

    public void deleteStudent(int VS) throws Exception {
        deleteStudent.setInt(1, VS);
        int affectedRecords = deleteStudent.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Deleting student failed");
        }
    }

    public void updateShouldPay(int vs, int shouldPay) throws Exception {
        updateShouldPay.setInt(1, shouldPay);
        updateShouldPay.setInt(2, vs);
        int affectedRecords = updateShouldPay.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Updating should pay failed");
        }
    }

    public void updatePayed(int vs, int payed) throws Exception {
        updatePayed.setInt(1, payed);
        updatePayed.setInt(2, vs);
        int affectedRecords = updatePayed.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Updating payed failed");
        }
    }
}
