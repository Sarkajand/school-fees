package sample.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "schoolFees.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:src\\main\\resources\\database\\" + DB_NAME;

    public static final String TABLE_CLASSES = "classes";
    public static final String COLUMN_CLASSES_ID = "_id";
    public static final String COLUMN_CLASSES_STAGE = "stage";
    public static final String COLUMN_CLASSES_CLASS_NAME = "class_name";
    public static final int INDEX_CLASSES_ID = 1;
    public static final int INDEX_CLASSES_STAGE = 2;
    public static final int INDEX_CLASSES_NAME = 3;


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
    public static final int INDEX_STUDENT_PAYMENT_NOTES = 11;
    public static final int INDEX_STUDENT_SHOULD_PAY = 12;
    public static final int INDEX_STUDENT_PAYED = 13;

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
    public static final int INDEX_STUDENT_LIST_PAYMENT_NOTES = 12;
    public static final int INDEX_STUDENT_LIST_SHOULD_PAY = 13;
    public static final int INDEX_STUDENT_LIST_PAYED = 14;

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
            "INSERT INTO " + TABLE_STUDENTS + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_STUDENT =
            "DELETE FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENTS_VS + " = ?";
    public static final String LIST_CLASSES_WITH_STAGE =
            "SELECT " + COLUMN_CLASSES_CLASS_NAME + ", " + COLUMN_CLASSES_STAGE + " FROM " + TABLE_CLASSES;
    public static final String INSERT_CLASS =
            "INSERT INTO " + TABLE_CLASSES + " (" + COLUMN_CLASSES_STAGE + ", " + COLUMN_CLASSES_CLASS_NAME + ") VALUES (?, ?)";
    public static final String EDIT_CLASS =
            "UPDATE " + TABLE_CLASSES + " SET " + COLUMN_CLASSES_STAGE + " = ?,  " + COLUMN_CLASSES_CLASS_NAME + " = ? WHERE " +
                    COLUMN_CLASSES_ID + " = ?";
    public static final String QUERY_STUDENTS_BY_CLASS_ID =
            "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENTS_CLASS + " = ?";
    public static final String DELETE_CLASS =
            "DELETE FROM " + TABLE_CLASSES + " WHERE " + COLUMN_CLASSES_ID + " = ?";
    public static final String INSERT_BANK_STATEMENT =
            "INSERT INTO " + TABLE_BANK_STATEMENT + " VALUES (?, ?)";
    public static final String FIND_STUDENT_BY_VS =
            "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENTS_VS + " = ?";


    private static DataSource instance = new DataSource();
    private Connection conn;
    private PreparedStatement queryStudents;
    private PreparedStatement queryStudentsBySchoolStage;
    private PreparedStatement queryStudentsByClass;
    private PreparedStatement queryClasses;
    private PreparedStatement findClassIdByClassName;
    private PreparedStatement insertStudent;
    private PreparedStatement deleteStudent;
    private PreparedStatement listClassesWithStage;
    private PreparedStatement insertClass;
    private PreparedStatement editClass;
    private PreparedStatement queryStudentsByClassId;
    private PreparedStatement deleteClass;
    private PreparedStatement insertBankStatement;
    private PreparedStatement findStudentByVS;

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
            listClassesWithStage = conn.prepareStatement(LIST_CLASSES_WITH_STAGE);
            insertClass = conn.prepareStatement(INSERT_CLASS);
            editClass = conn.prepareStatement(EDIT_CLASS);
            queryStudentsByClassId = conn.prepareStatement(QUERY_STUDENTS_BY_CLASS_ID);
            deleteClass = conn.prepareStatement(DELETE_CLASS);
            insertBankStatement = conn.prepareStatement(INSERT_BANK_STATEMENT);
            findStudentByVS = conn.prepareStatement(FIND_STUDENT_BY_VS);
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
            if (listClassesWithStage != null) {
                listClassesWithStage.close();
            }
            if (insertClass != null) {
                insertClass.close();
            }
            if (editClass != null) {
                editClass.close();
            }
            if (queryStudentsByClassId != null) {
                queryStudentsByClassId.close();
            }
            if (deleteClass != null) {
                deleteClass.close();
            }
            if (insertBankStatement != null) {
                insertBankStatement.close();
            }
            if (findStudentByVS != null) {
                findStudentByVS.close();
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
                    student.setPaymentNotes(results.getString(INDEX_STUDENT_LIST_PAYMENT_NOTES));
                    student.setShouldPay(results.getDouble(INDEX_STUDENT_LIST_SHOULD_PAY));
                    student.setPayed(results.getDouble(INDEX_STUDENT_LIST_PAYED));

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

    public List<Classes> listClassesWithStage() {
        try {
            ResultSet results = listClassesWithStage.executeQuery();
            List<Classes> classes = new ArrayList<>();
            while (results.next()) {
                Classes newClass = new Classes();
                newClass.setClassName(results.getString(1));
                newClass.setStage(results.getString(2));
                classes.add(newClass);
            }
            return classes;
        } catch (SQLException e) {
            System.out.println("Query classes with stage failed: " + e.getMessage());
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
            insertStudent.setString(INDEX_STUDENT_PAYMENT_NOTES, student.getPaymentNotes());
            insertStudent.setDouble(INDEX_STUDENT_SHOULD_PAY, student.getShouldPay());
            insertStudent.setDouble(INDEX_STUDENT_PAYED, student.getPayed());

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

    public boolean insertClass(Classes classes) {
        try {
            insertClass.setString(1, classes.getStage());
            insertClass.setString(2, classes.getClassName());

            int affectedRecords = insertClass.executeUpdate();

            return affectedRecords == 1;
        } catch (SQLException e) {
            System.out.println("Class inserting failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean editClass(int classId, Classes editedClass) {
        try {
            editClass.setString(1, editedClass.getStage());
            editClass.setString(2, editedClass.getClassName());
            editClass.setInt(3, classId);

            int affectedRecords = editClass.executeUpdate();

            return affectedRecords == 1;
        } catch (SQLException e) {
            System.out.println("Class edit failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClass(int classId) {
        try {
            queryStudentsByClassId.setInt(1, classId);
            ResultSet results = queryStudentsByClassId.executeQuery();
            while (results.next()) {
                deleteStudent.setInt(1, results.getInt(INDEX_STUDENT_VS));
            }
            deleteClass.setInt(1, classId);

            int affectedRecords = deleteClass.executeUpdate();
            return affectedRecords == 1;

        } catch (SQLException e) {
            System.out.println("Deleting class failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean backupDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            StringBuilder sb = new StringBuilder("backup to src\\main\\resources\\database\\backup\\zaloha-");
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            sb.append(formatter.format(date));
            sb.append(".db");
            connection.createStatement().executeUpdate(sb.toString());
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Database backup failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertBankStatement(BankStatement bankStatement) {
        try {
            insertBankStatement.setInt(1, bankStatement.getId());
            insertBankStatement.setString(2, bankStatement.getDate());
            List<Transaction> transactions = bankStatement.getTransactions();
            for (Transaction transaction : transactions) {
                int VS = transaction.getVS();
                findStudentByVS.setInt(1, VS);
                ResultSet results = findStudentByVS.executeQuery();
                if (results != null) {
//                    todo
//                    zavolat metodu vložení nové transakce
                }
            }


            int affectedRecords = insertBankStatement.executeUpdate();

            return affectedRecords == 1;
        } catch (SQLException e) {
            System.out.println("Inserting bank statemnet failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }



}

