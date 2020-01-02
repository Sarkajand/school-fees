package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private FeesHistoryDao feesHistoryDao;

    private DaoManager() {

    }

    public static DaoManager getInstance() {
        return instance;
    }

    public void open() throws Exception {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            this.studentDao = new StudentDao(connection);
            this.classesDao = new ClassesDao(connection);
            this.bankStatementDao = new BankStatementDao(connection);
            this.transactionDao = new TransactionDao(connection);
            this.feesHistoryDao = new FeesHistoryDao(connection);
        } catch (SQLException e) {
            logger.error("Couldn't connect to database: ", e);
            throw e;
        }
    }

    public void close() {
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
            if (feesHistoryDao != null) {
                feesHistoryDao.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close connection: ", e);
        }
    }

    public List<Student> listAllStudents() {
        return studentDao.queryAllStudents();
    }

    public List<Student> listStudentsBySchoolStage(String schoolStage) {
        return studentDao.queryStudentsBySchoolStage(schoolStage);
    }

    public List<Student> listStudentsByClass(String className) {
        return studentDao.queryStudentsByClass(className);
    }

    public List<String> listClassesNames() {
        return classesDao.queryClassesNames();
    }

    public List<Classes> listAllClasses() {
        return classesDao.queryAllClasses();
    }

    public int queryClassIdByClassName(String className) {
        return classesDao.queryClassIdByClassName(className);
    }

    public Student queryStudentByVs(int vs) {
        return studentDao.queryStudentByVs(vs);
    }

    public void insertStudent(Student student) throws Exception {
        studentDao.insertStudent(student);
        createFeesHistoryForNewStudent(student.getVS());
    }

    private void createFeesHistoryForNewStudent(int vs) throws Exception {
        feesHistoryDao.insertFeesHistory(vs);
    }

    public void editStudent(int vs, Student editedStudent) throws Exception {
        try {
            connection.setAutoCommit(false);
            studentDao.deleteStudent(vs);
            studentDao.insertStudent(editedStudent);
            if (vs != editedStudent.getVS()) {
                feesHistoryDao.updateVs(vs, editedStudent.getVS());
            }
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void deleteStudent(int vs) throws Exception {
        studentDao.deleteStudent(vs);
        feesHistoryDao.deleteFeesHistory(vs);
    }

    public void insertClass(Classes newClass) throws Exception {
        classesDao.insertClass(newClass);
    }

    public void editClass(Classes classToEdit) throws Exception {
        classesDao.editClass(classToEdit);
    }

    public void deleteClassWithAllStudents(Classes classToDelete) throws Exception {
        List<Student> studentsFromClass = studentDao.queryStudentsByClass(classToDelete.getClassName());
        for (Student student : studentsFromClass) {
            studentDao.deleteStudent(student.getVS());
        }
        classesDao.deleteClass(classToDelete);
    }

    public void updateFeesHistoryByUser(FeesHistory feesHistory) throws Exception {
        feesHistoryDao.updateAllMonthsByUser(feesHistory);
        updateStudentShouldPay(feesHistory.getStudentVs());
    }

    private void updateStudentShouldPay(int vs) throws Exception {
        FeesHistory feesHistory = queryFeesHistoryByStudentVs(vs);
        int shouldPay = feesHistory.countShouldPay();
        studentDao.updateShouldPay(vs, shouldPay);
    }

    public FeesHistory queryFeesHistoryByStudentVs(int vs) {
        return feesHistoryDao.queryFeesHistoryByStudentVs(vs);
    }

    public void automaticUpdateShouldPayAndFeesHistory() {
        Calendar calendar = Calendar.getInstance();
        int actualMonth = calendar.get(Calendar.MONTH) + 1;
        int actualDayInMonth = calendar.get(Calendar.DAY_OF_MONTH);
        List<FeesHistory> feesHistories;
        if (actualDayInMonth < 15) {
            feesHistories = feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(actualMonth - 1);
        } else {
            feesHistories = feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(actualMonth);
        }
        if (feesHistories.size() > 0) {
            try {
                updateShouldPayAndFeesHistory(actualMonth, feesHistories);
            } catch (Exception e) {
                logger.error("Monthly updating fees history and should pay failed: ", e);
            }
        }
    }

    private void updateShouldPayAndFeesHistory(int actualMonth, List<FeesHistory> feesHistories) throws Exception {
        for (FeesHistory feesHistory : feesHistories) {
            Student student = queryStudentByVs(feesHistory.getStudentVs());
            feesHistoryDao.updateActualMonth(actualMonth, student);
            updateStudentShouldPay(feesHistory.getStudentVs());
        }
    }

    public List<BankStatement> listBankStatements() {
        return bankStatementDao.queryBankStatements();
    }

    public List<Transaction> listTransactionsFromExistingStudents() {
        return transactionDao.queryAllTransactionsFromExistingStudent();
    }

    public void insertBankStatementWithAllTransactions(BankStatement bankStatement) throws Exception {
        List<Transaction> transactions = bankStatement.getTransactions();
        try {
            connection.setAutoCommit(false);
            for (Transaction transaction : transactions) {
                insertTransaction(transaction);
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

    public void insertTransaction(Transaction transaction) throws Exception {
        transactionDao.insertTransaction(transaction);
        updateStudentPayed(transaction);
    }

    private void updateStudentPayed(Transaction transaction) throws Exception{
        int vs = transaction.getVs();
        int payed = transactionDao.countStudentPayed(vs);
        studentDao.updatePayed(vs, payed);
    }

    public void editTransaction(Transaction transaction) throws Exception {
        transactionDao.editTransaction(transaction);
        updateStudentPayed(transaction);
    }

    public void deleteTransaction(Transaction transaction) throws Exception {
        transactionDao.deleteTransaction(transaction);
        updateStudentPayed(transaction);
    }

    public List<Transaction> listTransactionByVs(int vs) {
        return transactionDao.queryTransactionByVsFromExistingStudent(vs);
    }

    public void backupDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            String sql = "backup to src\\main\\resources\\database\\backup\\zaloha-" +
                    formatter.format(date) + ".db";
            connection.createStatement().executeUpdate(sql);
        }
    }

    public void automaticDatabaseBackup() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 5) {
            try {
            backupDatabase();
            } catch (SQLException e) {
                logger.error("Automatic database backup failed: ", e);
            }
        }
    }
}
