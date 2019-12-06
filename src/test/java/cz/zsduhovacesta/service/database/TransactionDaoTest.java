package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.Transaction;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDaoTest {

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
        List<Transaction> transactions = transactionDao.queryAllTransactions();
        int size = transactions.size();
        assertEquals(3, size);
    }

    @Test
    void checkFirstTransactionFromQueryAllTransaction () {
        List<Transaction> transactions = transactionDao.queryAllTransactions();
        assertEquals(firstTransaction(), transactions.get(0));
    }

    private Transaction firstTransaction () {
        Transaction transaction = new Transaction();
        transaction.setDate("1.12.2019");
        transaction.setClassName("1.B");
        transaction.setVS(268325);
        transaction.setLastName("Novotná");
        transaction.setFirstName("Anna");
        transaction.setAmount(1300);
        transaction.setPaymentMethod("BANK");
        transaction.setBankStatement(1);
        return transaction;
    }

}