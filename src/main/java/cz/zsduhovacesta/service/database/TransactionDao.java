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

    final Logger logger = LoggerFactory.getLogger(TransactionDao.class);

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

    private PreparedStatement queryAllTransactions;

    TransactionDao(Connection connection) throws SQLException {
        try {
            queryAllTransactions = connection.prepareStatement(QUERY_ALL_TRANSACTIONS);
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
        } catch (SQLException e) {
            logger.error("Couldn't close prepared statement in TransactionDao: ", e);
            throw e;
        }
    }

    public List<Transaction> queryAllTransactions() {
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
        transaction.setDate(results.getString(INDEX_TRANSACTIONS_LIST_DATE));
        transaction.setClassName(results.getString(INDEX_TRANSACTIONS_LIST_CLASS_NAME));
        transaction.setVS(results.getInt(INDEX_TRANSACTIONS_LIST_VS));
        transaction.setLastName(results.getString(INDEX_TRANSACTIONS_LIST_LAST_NAME));
        transaction.setFirstName(results.getString(INDEX_TRANSACTIONS_LIST_FIRST_NAME));
        transaction.setAmount(results.getDouble(INDEX_TRANSACTIONS_LIST_AMOUNT));
        transaction.setPaymentMethod(results.getString(INDEX_TRANSACTIONS_LIST_PAYMENT_METHOD));
        transaction.setTransactionNotes(results.getString(INDEX_TRANSACTIONS_LIST_PAYMENT_NOTES));
        transaction.setBankStatement(results.getInt(INDEX_TRANSACTIONS_LIST_BANK_STATEMENT));
        return transaction;
    }

}
