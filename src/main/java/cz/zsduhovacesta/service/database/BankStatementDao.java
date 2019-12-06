package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.BankStatement;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankStatementDao {

    public static final String TABLE_BANK_STATEMENT = "bank_statement";
    public static final String COLUMN_BANK_ID = "_id";
    public static final String COLUMN_BANK_DATE = "date";
    public static final int INDEX_BANK_ID = 1;
    public static final int INDEX_BANK_DATE = 2;

    public static final String QUERY_BANK_STATEMENTS =
            "SELECT * FROM " + TABLE_BANK_STATEMENT;

    private PreparedStatement queryBankStatements;

    BankStatementDao(Connection connection) throws SQLException {
        try {
            queryBankStatements = connection.prepareStatement(QUERY_BANK_STATEMENTS);
        } catch (SQLException e) {
            System.out.println("Couldn't create prepared statement for StudentDao");
            throw e;
        }
    }

    public void close() throws SQLException{
        try {
            if (queryBankStatements != null) {
                queryBankStatements.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close prepared statement in StudentDao: " + e.getMessage());
            throw e;
        }
    }

    public List<BankStatement> queryBankStatements() {
        try {
            ResultSet results = queryBankStatements.executeQuery();
            return setBankStatements(results);
        } catch (SQLException e) {
            System.out.println("Query bank statements failed: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<BankStatement> setBankStatements(ResultSet results) throws SQLException {
        List <BankStatement> bankStatements = new ArrayList<>();
        while (results.next()) {
            BankStatement bankStatement = new BankStatement();
            bankStatement.setId(results.getInt(INDEX_BANK_ID));
            bankStatement.setDate(results.getString(INDEX_BANK_DATE));
            bankStatements.add(bankStatement);
        }
        return bankStatements;
    }


}
