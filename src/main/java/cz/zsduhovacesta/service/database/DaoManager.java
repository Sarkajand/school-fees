package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
            this.feesHistoryDao = new FeesHistoryDao(connection);
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

    public Student queryStudentByVs (int vs) {
        return studentDao.queryStudentByVs(vs);
    }

    public void insertStudent (Student student) throws Exception {
        studentDao.insertStudent(student);
        createFeesHistoryForNewStudent(student.getVS());
    }

    private void createFeesHistoryForNewStudent (int vs) throws Exception {
        feesHistoryDao.insertFeesHistory(vs);
    }

    public void editStudent (int vs, Student editedStudent) throws Exception{
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

    public void deleteStudent (int vs) throws Exception {
        studentDao.deleteStudent(vs);
        feesHistoryDao.deleteFeesHistory(vs);
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

    public void updateFeesHistoryByUser (FeesHistory feesHistory) throws Exception{
        feesHistoryDao.updateAllMonthsByUser(feesHistory);
        updateStudentShouldPay(feesHistory.getStudentVs());
    }

    public FeesHistory queryFeesHistoryByStudentVs (int vs) {
        return feesHistoryDao.queryFeesHistoryByStudentVs(vs);
    }

    public void checkFeesHistoryLastUpdate () {
        Calendar calendar = Calendar.getInstance();
        int actualMonth = calendar.get(Calendar.MONTH) +1;
        int actualDayInMonth = calendar.get(Calendar.DAY_OF_MONTH);
        List<FeesHistory> feesHistories= new ArrayList<>();
        if (actualDayInMonth < 15) {
            feesHistories = queryFeesHistoriesWithWrongLastUpdate(actualMonth-1);
        } else {

            feesHistories = queryFeesHistoriesWithWrongLastUpdate(actualMonth);
        }
        if (feesHistories.size() > 0) {
            try {
                updateShouldPayAndFeesHistory(actualMonth, feesHistories);
            } catch (Exception e) {
                logger.error("Monthly updating fees history and should pay failed: ", e);
            }
        }
    }

    public List<FeesHistory> queryFeesHistoriesWithWrongLastUpdate (int lastUpdate) {
        return feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(lastUpdate);
    }

    public void updateShouldPayAndFeesHistory (int actualMonth, List<FeesHistory> feesHistories) throws Exception {
        for (FeesHistory feesHistory : feesHistories) {
            Student student = queryStudentByVs(feesHistory.getStudentVs());
            feesHistoryDao.updateActualMonth(actualMonth, student);
            updateStudentShouldPay(feesHistory.getStudentVs());
        }
    }

    private void updateStudentShouldPay (int vs) throws Exception{
        FeesHistory feesHistory = queryFeesHistoryByStudentVs(vs);
        int shouldPay = feesHistory.countShouldPay();
        studentDao.updateShouldPay(vs, shouldPay);
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

    public void insertTransaction (Transaction transaction) throws Exception{
        transactionDao.insertTransaction(transaction);
    }

    public void editTransaction (Transaction transaction) throws Exception {
        transactionDao.editTransaction(transaction);
    }

    public void deleteTransaction (Transaction transaction) throws Exception {
        transactionDao.deleteTransaction(transaction);
    }

    public List<Transaction> listTransactionByVs (int vs) throws Exception {
        return transactionDao.queryTransactionByVs(vs);
    }

    public void backupDatabase () throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        StringBuilder sb = new StringBuilder("backup to src\\main\\resources\\database\\backup\\zaloha-");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
        sb.append(formatter.format(date));
        sb.append(".db");
        connection.createStatement().executeUpdate(sb.toString());
        connection.close();
    }

}
