package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionDao {

    private final Logger logger = LoggerFactory.getLogger(TransactionDao.class);

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTIONS_ID = "_id";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_TRANSACTIONS_BANK_STATEMENT = "bank_statement";
    public static final String COLUMN_TRANSACTIONS_VS = "VS";
    public static final String COLUMN_TRANSACTIONS_AMOUNT = "amount";
    public static final String COLUMN_TRANSACTIONS_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_TRANSACTIONS_NOTES = "notes";


    public static final String VIEW_TRANSACTIONS_LIST = "transactions_list";
    public static final int INDEX_TRANSACTIONS_LIST_ID = 1;
    public static final int INDEX_TRANSACTIONS_LIST_DATE = 2;
    public static final int INDEX_TRANSACTIONS_LIST_CLASS_NAME = 3;
    public static final int INDEX_TRANSACTIONS_LIST_VS = 4;
    public static final int INDEX_TRANSACTIONS_LIST_LAST_NAME = 5;
    public static final int INDEX_TRANSACTIONS_LIST_FIRST_NAME = 6;
    public static final int INDEX_TRANSACTIONS_LIST_AMOUNT = 7;
    public static final int INDEX_TRANSACTIONS_LIST_PAYMENT_METHOD = 8;
    public static final int INDEX_TRANSACTIONS_LIST_PAYMENT_NOTES = 9;
    public static final int INDEX_TRANSACTIONS_LIST_BANK_STATEMENT = 10;

    public static final String QUERY_ALL_TRANSACTIONS =
            "SELECT * FROM " + VIEW_TRANSACTIONS_LIST;
    public static final String QUERY_TRANSACTION_BY_VS =
            "SELECT * FROM " + VIEW_TRANSACTIONS_LIST + " WHERE " + COLUMN_TRANSACTIONS_VS + " = ?";
    public static final String INSERT_TRANSACTION =
            "INSERT INTO " + TABLE_TRANSACTIONS + "(" + COLUMN_DATE + ", " + COLUMN_TRANSACTIONS_BANK_STATEMENT + ", " +
                    COLUMN_TRANSACTIONS_VS + ", " + COLUMN_TRANSACTIONS_AMOUNT + ", " +
                    COLUMN_TRANSACTIONS_PAYMENT_METHOD + ", " + COLUMN_TRANSACTIONS_NOTES + ") VALUES (?, ?, ?, ?, ?, ?)";
    public static final String EDIT_TRANSACTION =
            "UPDATE " + TABLE_TRANSACTIONS + " SET " + COLUMN_DATE + " = ? , " + COLUMN_TRANSACTIONS_BANK_STATEMENT + " = ?, " +
                    COLUMN_TRANSACTIONS_VS + " = ?, " + COLUMN_TRANSACTIONS_AMOUNT + " = ?, " +
                    COLUMN_TRANSACTIONS_PAYMENT_METHOD + " = ?, " + COLUMN_TRANSACTIONS_NOTES + " = ?" +
                    " WHERE " + COLUMN_TRANSACTIONS_ID + " = ?";
    public static final String DELETE_TRANSACTION =
            "DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " + COLUMN_TRANSACTIONS_ID + " = ?";

    private PreparedStatement queryAllTransactions;
    private PreparedStatement queryTransactionByVs;
    private PreparedStatement insertTransaction;
    private PreparedStatement editTransaction;
    private PreparedStatement deleteTransaction;

    TransactionDao(Connection connection) throws SQLException {
        try {
            queryAllTransactions = connection.prepareStatement(QUERY_ALL_TRANSACTIONS);
            queryTransactionByVs = connection.prepareStatement(QUERY_TRANSACTION_BY_VS);
            insertTransaction = connection.prepareStatement(INSERT_TRANSACTION);
            editTransaction = connection.prepareStatement(EDIT_TRANSACTION);
            deleteTransaction = connection.prepareStatement(DELETE_TRANSACTION);
        } catch (SQLException e) {
            logger.error("Couldn't create prepared statement for StudentDao", e);
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (queryAllTransactions != null) {
                queryAllTransactions.close();
            }
            if (queryTransactionByVs != null) {
                queryTransactionByVs.close();
            }
            if (insertTransaction != null) {
                insertTransaction.close();
            }
            if (editTransaction != null) {
                editTransaction.close();
            }
            if (deleteTransaction != null) {
                deleteTransaction.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close prepared statement in TransactionDao: ", e);
            throw e;
        }
    }

    public List<Transaction> queryAllTransactionsFromExistingStudent() {
        try {
            ResultSet results = queryAllTransactions.executeQuery();
            if (results == null) {
                return Collections.emptyList();
            } else {
                return setTransactions(results);
            }
        } catch (SQLException e) {
            logger.warn("Query all transactions failed: ", e);
            return Collections.emptyList();
        }
    }

    private List<Transaction> setTransactions(ResultSet results) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        while (results.next()) {
            Transaction transaction = setTransaction(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    private Transaction setTransaction(ResultSet results) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(results.getInt(INDEX_TRANSACTIONS_LIST_ID));
        transaction.setDate(results.getString(INDEX_TRANSACTIONS_LIST_DATE));
        transaction.setClassName(results.getString(INDEX_TRANSACTIONS_LIST_CLASS_NAME));
        transaction.setVs(results.getInt(INDEX_TRANSACTIONS_LIST_VS));
        transaction.setLastName(results.getString(INDEX_TRANSACTIONS_LIST_LAST_NAME));
        transaction.setFirstName(results.getString(INDEX_TRANSACTIONS_LIST_FIRST_NAME));
        transaction.setAmount(results.getInt(INDEX_TRANSACTIONS_LIST_AMOUNT));
        transaction.setPaymentMethod(results.getString(INDEX_TRANSACTIONS_LIST_PAYMENT_METHOD));
        transaction.setTransactionNotes(results.getString(INDEX_TRANSACTIONS_LIST_PAYMENT_NOTES));
        transaction.setBankStatement(results.getInt(INDEX_TRANSACTIONS_LIST_BANK_STATEMENT));
        return transaction;
    }

    public List<Transaction> queryTransactionByVsFromExistingStudent(int vs) {
        try {
            queryTransactionByVs.setInt(1, vs);
            ResultSet results = queryTransactionByVs.executeQuery();
            return setTransactions(results);
        } catch (SQLException e) {
            logger.error("Query Transactions by vs from existing students failed: ", e);
            return Collections.emptyList();
        }
    }

    public void insertTransaction(Transaction transaction) throws Exception {
        insertTransaction.setString(1, transaction.getStringDate());
        insertTransaction.setInt(2, transaction.getBankStatement());
        insertTransaction.setInt(3, transaction.getVs());
        insertTransaction.setInt(4, transaction.getAmount());
        insertTransaction.setString(5, transaction.getPaymentMethod());
        insertTransaction.setString(6, transaction.getTransactionNotes());
        int affectedRecords = insertTransaction.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Inserting transaction failed");
        }
    }

    public void editTransaction(Transaction transaction) throws Exception {
        editTransaction.setString(1, transaction.getStringDate());
        editTransaction.setInt(2, transaction.getBankStatement());
        editTransaction.setInt(3, transaction.getVs());
        editTransaction.setInt(4, transaction.getAmount());
        editTransaction.setString(5, transaction.getPaymentMethod());
        editTransaction.setString(6, transaction.getTransactionNotes());
        editTransaction.setInt(7, transaction.getId());
        int affectedRecords = editTransaction.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Editing transaction failed");
        }
    }

    public void deleteTransaction(Transaction transaction) throws Exception {
        deleteTransaction.setInt(1, transaction.getId());
        int affectedRecords = deleteTransaction.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Deleting transaction failed");
        }
    }
}
