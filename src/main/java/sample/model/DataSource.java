package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "schoolFees.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Java\\MyProjects2\\" + DB_NAME;


    public static final String TABLE_CLASSES = "classes";
    public static final String COLUMN_CLASSES_ID = "_id";
    public static final String COLUMN_CLASSES_CLASS_NAME = "class_name";
    public static final String COLUMN_CLASSES_STAGE = "stage";
    public static final String COLUMN_CLASSES_STARTING_YEAR = "starting_year";
    public static final int INDEX_CLASSES_ID = 1;
    public static final int INDEX_CLASSES_NAME = 4;
    public static final int INDEX_CLASSES_STAGE = 3;
    public static final int INDEX_CLASSES_STARTING_YEAR = 2;

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

    public static final String TABLE_BANK_STATEMENT = "bank_statement";
    public static final String COLUMN_BANK_ID = "_id";
    public static final String COLUMN_BANK_DATE = "date";
    public static final String COLUMN_BANK_NAME = "name";
    public static final int INDEX_BANK_ID = 1;
    public static final int INDEX_BANK_DATE = 2;
    public static final int INDEX_BANK_NAME = 3;

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTIONS_ID = "_id";
    public static final String COLUMN_TRANSACTIONS_BANK_STATEMENT = "bank_statement";
    public static final String COLUMN_TRANSACTIONS_VS = "VS";
    public static final String COLUMN_TRANSACTIONS_AMOUNT = "amount";
    public static final String COLUMN_TRANSACTIONS_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_TRANSACTIONS_NOTES = "notes";
    public static final int INDEX_TRANSACTION_ID = 1;
    public static final int INDEX_TRANSACTION_BANK_STATEMENT = 2;
    public static final int INDEX_TRANSACTION_VS = 3;
    public static final int INDEX_TRANSACTION_AMOUNT = 4;
    public static final int INDEX_TRANSACTION_PAYMENT_METHOD = 5;
    public static final int INDEX_TRANSACTION_NOTES = 6;

    public static final String VIEW_STUDENT_LIST = "students_list";
    public static final String VIEW_STUDENT_LIST_STAGE = "stage";
    public static final String VIEW_STUDENTS_LIST_CLASS = "class_name";
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

    public static final String QUERY_STUDENTS =
            "SELECT * FROM " + VIEW_STUDENT_LIST;
    public static final String QUERY_STUDENTS_BY_SCHOOL_STAGE =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + VIEW_STUDENT_LIST_STAGE + " = ?";
    public static final String QUERY_STUDENTS_BY_CLASS =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + VIEW_STUDENTS_LIST_CLASS + " = ?";
    public static final String QUERY_CLASSES =
            "SELECT " + COLUMN_CLASSES_CLASS_NAME + " FROM " + TABLE_CLASSES;
    public static final String FIND_CLASS_ID_BY_CLASS_NAME =
            "SELECT " + COLUMN_CLASSES_ID + " FROM " + TABLE_CLASSES + " WHERE " + COLUMN_CLASSES_CLASS_NAME + " = ?";
    public static final String INSERT_STUDENT =
            "INSERT INTO " + TABLE_STUDENTS + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_STUDENT =
            "DELETE FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENTS_VS + " = ?";


    private static DataSource instance = new DataSource();
    private Connection conn;
    private PreparedStatement queryStudents;
    private PreparedStatement queryStudentsBySchoolStage;
    private PreparedStatement queryStudentsByClass;
    private PreparedStatement queryClasses;
    private PreparedStatement findClassIdByClassName;
    private PreparedStatement insertStudent;
    private PreparedStatement deleteStudent;

    private DataSource() {

    }

    public static DataSource getInstance() {
        return instance;
    }


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryStudents = conn.prepareStatement(QUERY_STUDENTS);
            queryStudentsBySchoolStage = conn.prepareStatement(QUERY_STUDENTS_BY_SCHOOL_STAGE);
            queryStudentsByClass = conn.prepareStatement(QUERY_STUDENTS_BY_CLASS);
            queryClasses = conn.prepareStatement(QUERY_CLASSES);
            findClassIdByClassName = conn.prepareStatement(FIND_CLASS_ID_BY_CLASS_NAME);
            insertStudent = conn.prepareStatement(INSERT_STUDENT);
            deleteStudent = conn.prepareStatement(DELETE_STUDENT);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (queryStudents != null) {
                queryStudents.close();
            }
            if (queryStudentsBySchoolStage != null) {
                queryStudentsBySchoolStage.close();
            }
            if (queryStudentsByClass != null) {
                queryStudentsByClass.close();
            }
            if (findClassIdByClassName != null) {
                findClassIdByClassName.close();
            }
            if (insertStudent != null) {
                insertStudent.close();
            }
            if (deleteStudent != null) {
                deleteStudent.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    private List<Student> setStudents(ResultSet results) {
        if (results == null) {
            System.out.println("no data for students");
            return null;
        } else {
            try {
                List<Student> students = new ArrayList<>();
                while (results.next()) {
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

                    students.add(student);
                }
                return students;
            } catch (SQLException e) {
                System.out.println("Setting data for students failed: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<Student> queryStudent() {
        try {
            ResultSet results = queryStudents.executeQuery();
            return setStudents(results);
        } catch (SQLException e) {
            System.out.println("Query students failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> queryStudentsBySchoolStage(String schoolStage) {
        try {
            queryStudentsBySchoolStage.setString(1, schoolStage);
            ResultSet results = queryStudentsBySchoolStage.executeQuery();
            return setStudents(results);

        } catch (SQLException e) {
            System.out.println("Query students by school stage failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> queryStudentsByClass(String className) {
        try {
            queryStudentsByClass.setString(1, className);
            ResultSet results = queryStudentsByClass.executeQuery();
            return setStudents(results);
        } catch (SQLException e) {
            System.out.println("Query students by class failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<String> listClasses() {
        try {
            ResultSet results = queryClasses.executeQuery();
            List<String> classes = new ArrayList<>();
            while (results.next()) {
                classes.add(results.getString(1));
            }
            return classes;
        } catch (SQLException e) {
            System.out.println("Query classes failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public int findClassIdByClassName(String className) {
        try {
            findClassIdByClassName.setString(1, className);
            ResultSet results = findClassIdByClassName.executeQuery();
            return results.getInt(1);
        } catch (SQLException e) {
            System.out.println("Finding class failed: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public boolean insertStudent(Student student, int classId) {
        try {
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

            int affectedRecords = insertStudent.executeUpdate();
            if (affectedRecords == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Inserting student failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int VS) {
        try {
            deleteStudent.setInt(1, VS);
            int affectedRecords = deleteStudent.executeUpdate();
            if (affectedRecords == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Deleting student failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

}

