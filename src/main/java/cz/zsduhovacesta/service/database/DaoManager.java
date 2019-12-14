package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.BankStatement;
import cz.zsduhovacesta.model.Classes;
import cz.zsduhovacesta.model.Student;
import cz.zsduhovacesta.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DaoManager {

    final Logger logger = LoggerFactory.getLogger(DaoManager.class);

    public static final String DB_NAME = "schoolFees.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:src\\main\\resources\\database\\" + DB_NAME;

    private static DaoManager instance = new DaoManager();

    private Connection connection;

    private StudentDao studentDao;
    private ClassesDao classesDao;
    private BankStatementDao bankStatementDao;
    private TransactionDao transactionDao;

    private DaoManager () {

    }

    public static DaoManager getInstance() {
        return instance;
    }

    public void open () throws Exception {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            this.studentDao = new StudentDao(connection);
            this.classesDao = new ClassesDao(connection);
            this.bankStatementDao = new BankStatementDao(connection);
            this.transactionDao = new TransactionDao(connection);
        } catch (SQLException e) {
            logger.error("Couldn't connect to database: ", e);
            throw e;
        }
    }

    public void close () {
        try {
            if (studentDao != null) {
                studentDao.close();
            }
            if (bankStatementDao != null) {
                bankStatementDao.close();
            }
            if (transactionDao != null) {
                transactionDao.close();
            }
            if (classesDao != null) {
                classesDao.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close connection: ", e);
        }
    }

    public List<Student> listAllStudents () {
        return studentDao.queryAllStudents();
    }

    public List<Student> listStudentsBySchoolStage (String schoolStage) {
        return studentDao.queryStudentsBySchoolStage(schoolStage);
    }

    public List<Student> listStudentsByClass (String className) {
        return studentDao.queryStudentsByClass(className);
    }

    public List<String> listClassesNames () {
        return classesDao.queryClassesNames();
    }

    public List<Classes> listAllClasses () {
        return classesDao.queryAllClasses();
    }

    public int queryClassIdByClassName (String className) {
        return classesDao.queryClassIdByClassName(className);
    }

    public void insertStudent (Student student) throws Exception {
        studentDao.insertStudent(student);
    }

    public void editStudent (Student studentToEdit, Student editedStudent, int classId) {
//        todo
    }

    public void deleteStudent (int vs) throws Exception {
        studentDao.deleteStudent(vs);
    }

    public void insertClass (Classes newClass) throws Exception {
        classesDao.insertClass(newClass);
    }

    public void editClass (Classes classToEdit) throws Exception{
        classesDao.editClass(classToEdit);
    }

    public void deleteClassWithAllStudents (Classes classToDelete) throws Exception{
        List<Student> studentsFromClass = studentDao.queryStudentsByClass(classToDelete.getClassName());
        for (Student student : studentsFromClass) {
            studentDao.deleteStudent(student.getVS());
        }
        classesDao.deleteClass(classToDelete);
    }

    public List<BankStatement> listBankStatements () {
        return bankStatementDao.queryBankStatements();
    }

    public List<Transaction> listTransactions () {
        return transactionDao.queryAllTransactions();
    }

    public void insertBankStatementWithAllTransactions(BankStatement bankStatement) throws Exception{
        List<Transaction> transactions = bankStatement.getTransactions();
        try {
            connection.setAutoCommit(false);
            for (Transaction transaction : transactions) {
                transactionDao.insertTransaction(transaction);
            }
            bankStatementDao.insertBankStatement(bankStatement);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            logger.error("Inserting bank statement with all transactions failed");
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
