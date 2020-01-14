package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.exceptions.EditRecordException;
import cz.zsduhovacesta.model.BankStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankStatementDao {

    final Logger logger = LoggerFactory.getLogger(BankStatementDao.class);

    public static final String TABLE_BANK_STATEMENT = "bank_statement";

    public static final String QUERY_BANK_STATEMENTS =
            "SELECT * FROM " + TABLE_BANK_STATEMENT;
    public static final String INSERT_BANK_STATEMENT =
            "INSERT INTO " + TABLE_BANK_STATEMENT + " VALUES (?, ?)";

    private PreparedStatement queryBankStatements;
    private PreparedStatement insertBankStatement;

    BankStatementDao(Connection connection) throws SQLException {
        try {
            queryBankStatements = connection.prepareStatement(QUERY_BANK_STATEMENTS);
            insertBankStatement = connection.prepareStatement(INSERT_BANK_STATEMENT);
        } catch (SQLException e) {
            logger.error("Couldn't create prepared statement for StudentDao", e);
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (queryBankStatements != null) {
                queryBankStatements.close();
            }
            if (insertBankStatement != null) {
                insertBankStatement.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close prepared statement in StudentDao: ", e);
            throw e;
        }
    }

    public List<BankStatement> queryBankStatements() {
        try {
            ResultSet results = queryBankStatements.executeQuery();
            return setBankStatements(results);
        } catch (SQLException e) {
            logger.warn("Query bank statements failed: ", e);
            return Collections.emptyList();
        }
    }

    private List<BankStatement> setBankStatements(ResultSet results) throws SQLException {
        List<BankStatement> bankStatements = new ArrayList<>();
        if (results.next()) {
            do {
                BankStatement bankStatement = new BankStatement();
                bankStatement.setId(results.getInt(1));
                bankStatement.setDate(results.getString(2));
                bankStatements.add(bankStatement);
            } while (results.next());
        }
        return bankStatements;
    }

    public void insertBankStatement(BankStatement bankStatement) throws EditRecordException, SQLException {
        insertBankStatement.setInt(1, bankStatement.getId());
        insertBankStatement.setString(2, bankStatement.getStringDate());
        insertBankStatement.executeUpdate();
        int affectedRecords = insertBankStatement.getUpdateCount();
        if (affectedRecords != 1) {
            throw new EditRecordException("Inserting bank statement failed");
        }
    }
}
