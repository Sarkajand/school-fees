package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.Transaction;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TransactionDaoIT {

    private static final String DB_NAME = "schoolFees.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:src\\test\\resources\\database\\" + DB_NAME;
    private static Connection connection;
    private static TransactionDao transactionDao;

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
            transactionDao = new TransactionDao(connection);
            connection.beginRequest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void rollback() {
        try {
            connection.rollback();
            transactionDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkSizeOfListFromQueryAllTransaction() {
        List<Transaction> transactions = transactionDao.queryAllTransactionsFromExistingStudent();
        int size = transactions.size();
        assertEquals(3, size);
    }

    @Test
    void checkFirstTransactionFromQueryAllTransaction() {
        List<Transaction> transactions = transactionDao.queryAllTransactionsFromExistingStudent();
        assertEquals(firstTransaction(), transactions.get(0));
    }

    private Transaction firstTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setDate("01.12.2019");
        transaction.setClassName("1.B");
        transaction.setVs(268325);
        transaction.setLastName("Novotná");
        transaction.setFirstName("Anna");
        transaction.setAmount(1300);
        transaction.setPaymentMethod("BANK");
        transaction.setBankStatement(1);
        transaction.setTransactionNotes("");
        return transaction;
    }

    @Test
    void insertTransaction() {
        try {
            Transaction transaction = firstTransaction();
            transactionDao.insertTransaction(transaction);
            List<Transaction> transactions = transactionDao.queryAllTransactionsFromExistingStudent();
            assertEquals(4, transactions.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void editTransaction() {
        Transaction transaction = firstTransaction();
        transaction.setBankStatement(1);
        transaction.setAmount(555);
        transaction.setId(2);
        transaction.setDate("26.05.2011");
        try {
            transactionDao.editTransaction(transaction);
        } catch (Exception e) {
            fail();
        }
        List<Transaction> transactions = transactionDao.queryAllTransactionsFromExistingStudent();
        assertEquals(transaction, transactions.get(1));
    }

    @Test
    void editTransaction2() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setDate("28.03.2015");
        transaction.setAmount(150);
        transaction.setPaymentMethod("BANK");
        transaction.setBankStatement(2);
        transaction.setTransactionNotes("");
        transaction.setVs(268325);
        transaction.setLastName("Novotná");
        transaction.setFirstName("Anna");
        transaction.setClassName("1.B");
        try {
            transactionDao.editTransaction(transaction);
        } catch (Exception e) {
            fail();
        }
        List<Transaction> transactions = transactionDao.queryAllTransactionsFromExistingStudent();
        assertEquals(transaction, transactions.get(0));
    }

    @Test
    void deleteTransaction() {
        List<Transaction> transactions = transactionDao.queryAllTransactionsFromExistingStudent();
        assertEquals(3, transactions.size());
        try {
            transactionDao.deleteTransaction(firstTransaction());
        } catch (Exception e) {
            fail();
        }
        transactions = transactionDao.queryAllTransactionsFromExistingStudent();
        assertEquals(2, transactions.size());
    }

    @Test
    void transactionWithoutExistingStudentVsIsNotInList() {
        List<Transaction> transactions = transactionDao.queryAllTransactionsFromExistingStudent();
        assertEquals(3, transactions.size());
        Transaction transaction = firstTransaction();
        transaction.setVs(25687);
        try {
            transactionDao.editTransaction(transaction);
            transactions = transactionDao.queryAllTransactionsFromExistingStudent();
            assertEquals(2, transactions.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testCountStudentPayedForExistingStudentWithOneTransaction() {
        assertEquals(1500, transactionDao.countStudentPayed(568325));
    }

    @Test
    void testCountStudentPayedForExistingStudentWithTransactions() {
        assertEquals(2600, transactionDao.countStudentPayed(268325));
    }

    @Test
    void testCountStudentPayedForExistingStudentWithoutTransactions() {
        assertEquals(0, transactionDao.countStudentPayed(254387));
    }

    @Test
    void testCountStudentPayedForNotExistingStudentReturnZero() {
        assertEquals(0, transactionDao.countStudentPayed(111));
    }
}