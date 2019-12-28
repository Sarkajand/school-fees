package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.FeesHistory;
import cz.zsduhovacesta.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeesHistoryDao {
    final Logger logger = LoggerFactory.getLogger(FeesHistoryDao.class);

    public static final String TABLE_FEES_HISTORY = "fees_history";
    public static final String COLUMN_STUDENT_VS = "student";
    public static final String COLUMN_JANUARY = "january";
    public static final String COLUMN_FEBRUARY = "february";
    public static final String COLUMN_MARCH = "march";
    public static final String COLUMN_APRIL = "april";
    public static final String COLUMN_MAY = "may";
    public static final String COLUMN_JUNE = "june";
    public static final String COLUMN_JULY = "july";
    public static final String COLUMN_AUGUST = "august";
    public static final String COLUMN_SEPTEMBER = "september";
    public static final String COLUMN_OCTOBER = "october";
    public static final String COLUMN_NOVEMBER = "november";
    public static final String COLUMN_DECEMBER = "december";
    public static final String COLUMN_LAST_UPDATE = "last_update";

    public static final String INSERT_FEES_HISTORY =
            "INSERT INTO " + TABLE_FEES_HISTORY + "(" + COLUMN_STUDENT_VS + ", "+ COLUMN_LAST_UPDATE
                    + ") VALUES (?, 8)";
    public static final String UPDATE_ALL_MONTHS_BY_USER =
            "UPDATE " + TABLE_FEES_HISTORY + " SET " + COLUMN_JANUARY + " = ?, " + COLUMN_FEBRUARY
            + " = ?, " + COLUMN_MARCH + " = ?, " + COLUMN_APRIL + " = ?, " + COLUMN_MAY + " = ?, "
            + COLUMN_JUNE + " = ?, " + COLUMN_JULY + " = ?, " + COLUMN_AUGUST + " = ?, " + COLUMN_SEPTEMBER
            + " = ?, " + COLUMN_OCTOBER + " = ?, " + COLUMN_NOVEMBER + " = ?, " + COLUMN_DECEMBER + " = ? WHERE "
            + COLUMN_STUDENT_VS + " = ?";
//    public static final String UPDATE_ACTUAL_MONTH =
//            "UPDATE " + TABLE_FEES_HISTORY + " SET ? = ?, " + COLUMN_LAST_UPDATE + " = ? WHERE "
//                    + COLUMN_STUDENT_VS + " = ?";
    public static final String UPDATE_VS =
            "UPDATE " + TABLE_FEES_HISTORY + " SET " + COLUMN_STUDENT_VS + " = ? WHERE " + COLUMN_STUDENT_VS + " = ?";
    public static final String DELETE_FEES_HISTORY =
            "DELETE FROM " + TABLE_FEES_HISTORY + " WHERE " + COLUMN_STUDENT_VS + " = ?";
    public static final String QUERY_FEES_HISTORY_BY_STUDENT_VS =
            "SELECT * FROM " + TABLE_FEES_HISTORY + " WHERE " + COLUMN_STUDENT_VS + " = ?";
    public static final String QUERY_FEES_HISTORY_WITH_WRONG_LAST_UPDATE =
            "SELECT * FROM " + TABLE_FEES_HISTORY + " WHERE " + COLUMN_LAST_UPDATE + " != ?";

    private Connection connection;

    private PreparedStatement insertFeesHistory;
    private PreparedStatement updateAllMonthsByUser;
//    private PreparedStatement updateActualMonth;
    private PreparedStatement updateVs;
    private PreparedStatement deleteFeesHistory;
    private PreparedStatement queryFeesHistoryByStudentsVs;
    private PreparedStatement queryFeesHistoryWithWrongLastUpdate;

    FeesHistoryDao (Connection connection) throws SQLException {
        try {
            this.connection = connection;
            insertFeesHistory = connection.prepareStatement(INSERT_FEES_HISTORY);
            updateAllMonthsByUser = connection.prepareStatement(UPDATE_ALL_MONTHS_BY_USER);
//            updateActualMonth = connection.prepareStatement(UPDATE_ACTUAL_MONTH);
            updateVs = connection.prepareStatement(UPDATE_VS);
            deleteFeesHistory = connection.prepareStatement(DELETE_FEES_HISTORY);
            queryFeesHistoryByStudentsVs = connection.prepareStatement(QUERY_FEES_HISTORY_BY_STUDENT_VS);
            queryFeesHistoryWithWrongLastUpdate = connection.prepareStatement(QUERY_FEES_HISTORY_WITH_WRONG_LAST_UPDATE);
        } catch (SQLException e) {
            logger.error("Couldn't create prepared statements for FeesHistoryDao", e);
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (insertFeesHistory != null) {
                insertFeesHistory.close();
            }
            if (updateAllMonthsByUser != null) {
                updateAllMonthsByUser.close();
            }
//            if (updateActualMonth != null) {
//                updateActualMonth.close();
//            }
            if (updateVs != null) {
                updateVs.close();
            }
            if (deleteFeesHistory != null) {
                deleteFeesHistory.close();
            }
            if (queryFeesHistoryByStudentsVs != null) {
                queryFeesHistoryByStudentsVs.close();
            }
            if (queryFeesHistoryWithWrongLastUpdate != null) {
                queryFeesHistoryWithWrongLastUpdate.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close prepared statements for FeesHistoryDao", e);
            throw e;
        }
    }

    public void insertFeesHistory(int vs) throws Exception {
        insertFeesHistory.setInt(1,vs);
        int affectedRecords = insertFeesHistory.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("inserting fees history failed");
        }
    }

    public void updateAllMonthsByUser(FeesHistory feesHistory) throws Exception {
        setAllMonthsToUpdate(feesHistory);
        int affectedRecords = updateAllMonthsByUser.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Updating fees history failed");
        }
    }

    private void setAllMonthsToUpdate (FeesHistory feesHistory) throws SQLException {
        updateAllMonthsByUser.setInt(1, feesHistory.getJanuary());
        updateAllMonthsByUser.setInt(2, feesHistory.getFebruary());
        updateAllMonthsByUser.setInt(3, feesHistory.getMarch());
        updateAllMonthsByUser.setInt(4, feesHistory.getApril());
        updateAllMonthsByUser.setInt(5, feesHistory.getMay());
        updateAllMonthsByUser.setInt(6, feesHistory.getJune());
        updateAllMonthsByUser.setInt(7, feesHistory.getJuly());
        updateAllMonthsByUser.setInt(8, feesHistory.getAugust());
        updateAllMonthsByUser.setInt(9, feesHistory.getSeptember());
        updateAllMonthsByUser.setInt(10, feesHistory.getOctober());
        updateAllMonthsByUser.setInt(11, feesHistory.getNovember());
        updateAllMonthsByUser.setInt(12, feesHistory.getDecember());
        updateAllMonthsByUser.setInt(13, feesHistory.getStudentVs());
    }

//    public void updateActualMonth (int actualMonth, Student student) throws Exception {
//        setActualMonthToUpdate(actualMonth, student);
//        int affectedRecords = updateActualMonth.executeUpdate();
//        if (affectedRecords != 1) {
//            throw new Exception("Updating fees history failed");
//        }
//    }
    public void updateActualMonth (int actualMonth, Student student) throws Exception {
        String updateActualMonth = setStringForUpdateActualMonth(actualMonth, student);
        int affectedRecords = connection.createStatement().executeUpdate(updateActualMonth);
        if (affectedRecords != 1) {
            throw new Exception("Updating fees history failed");
        }
    }

    private String setStringForUpdateActualMonth (int actualMonth, Student student) {
        StringBuilder updateActualMonth = new StringBuilder("UPDATE " + TABLE_FEES_HISTORY + " SET ");
        switch (actualMonth) {
            case 1:
                updateActualMonth.append(COLUMN_JANUARY);
                break;
            case 2:
                updateActualMonth.append(COLUMN_FEBRUARY);
                break;
            case 3:
                updateActualMonth.append(COLUMN_MARCH);
                break;
            case 4:
                updateActualMonth.append(COLUMN_APRIL);
                break;
            case 5:
                updateActualMonth.append(COLUMN_MAY);
                break;
            case 6:
                updateActualMonth.append(COLUMN_JUNE);
                break;
            case 7:
                updateActualMonth.append(COLUMN_JULY);
                break;
            case 8:
                updateActualMonth.append(COLUMN_AUGUST);
                break;
            case 9:
                updateActualMonth.append(COLUMN_SEPTEMBER);
                break;
            case 10:
                updateActualMonth.append(COLUMN_OCTOBER);
                break;
            case 11:
                updateActualMonth.append(COLUMN_NOVEMBER);
                break;
            case 12:
                updateActualMonth.append(COLUMN_DECEMBER);
                break;
        }
        updateActualMonth.append(" = ");
        updateActualMonth.append(student.getFees());
        updateActualMonth.append(", " + COLUMN_LAST_UPDATE + " = ");
        updateActualMonth.append(actualMonth);
        updateActualMonth.append(" WHERE " + COLUMN_STUDENT_VS + " = ");
        updateActualMonth.append(student.getVS());
        return updateActualMonth.toString();
    }

//    private void setActualMonthToUpdate (int actualMonth, Student student) throws SQLException {
//        switch (actualMonth) {
//            case 1:
//                updateActualMonth.setString(1, COLUMN_JANUARY);
//                break;
//            case 2:
//                updateActualMonth.setString(1, COLUMN_FEBRUARY);
//                break;
//            case 3:
//                updateActualMonth.setString(1, COLUMN_MARCH);
//                break;
//            case 4:
//                updateActualMonth.setString(1, COLUMN_APRIL);
//                break;
//            case 5:
//                updateActualMonth.setString(1, COLUMN_MAY);
//                break;
//            case 6:
//                updateActualMonth.setString(1, COLUMN_JUNE);
//                break;
//            case 7:
//                updateActualMonth.setString(1, COLUMN_JULY);
//                break;
//            case 8:
//                updateActualMonth.setString(1, COLUMN_AUGUST);
//                break;
//            case 9:
//                updateActualMonth.setString(1, COLUMN_SEPTEMBER);
//                break;
//            case 10:
//                updateActualMonth.setString(1, COLUMN_OCTOBER);
//                break;
//            case 11:
//                updateActualMonth.setString(1, COLUMN_NOVEMBER);
//                break;
//            case 12:
//                updateActualMonth.setString(1, COLUMN_DECEMBER);
//                break;
//        }
//        updateActualMonth.setInt(2, student.getFees());
//        updateActualMonth.setInt(3, actualMonth);
//        updateActualMonth.setInt(4, student.getVS());
//    }

    public void updateVs (int vs, int editedVs) throws Exception {
        updateVs.setInt(1, editedVs);
        updateVs.setInt(2, vs);
        int affectedRecords = updateVs.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Updating vs failed");
        }
    }

    public void deleteFeesHistory (int vs) throws Exception {
        deleteFeesHistory.setInt(1, vs);
        int affectedRecords = deleteFeesHistory.executeUpdate();
        if (affectedRecords != 1) {
            throw new Exception("Deleting fees history failed");
        }
    }

    public FeesHistory queryFeesHistoryByStudentVs (int vs) {
        try {
            queryFeesHistoryByStudentsVs.setInt(1, vs);
            ResultSet results = queryFeesHistoryByStudentsVs.executeQuery();
            return setFeesHistory(results);
        } catch (SQLException e) {
            logger.warn("Query fees history by vs failed: ", e);
            return new FeesHistory();
        }
    }

    private FeesHistory setFeesHistory (ResultSet results) throws SQLException {
        FeesHistory feesHistory = new FeesHistory();
        feesHistory.setStudentVs(results.getInt(1));
        feesHistory.setJanuary(results.getInt(2));
        feesHistory.setFebruary(results.getInt(3));
        feesHistory.setMarch(results.getInt(4));
        feesHistory.setApril(results.getInt(5));
        feesHistory.setMay(results.getInt(6));
        feesHistory.setJune(results.getInt(7));
        feesHistory.setJuly(results.getInt(8));
        feesHistory.setAugust(results.getInt(9));
        feesHistory.setSeptember(results.getInt(10));
        feesHistory.setOctober(results.getInt(11));
        feesHistory.setNovember(results.getInt(12));
        feesHistory.setDecember(results.getInt(13));
        feesHistory.setLastUpdate(results.getInt(14));
        return feesHistory;
    }

    public List<FeesHistory> queryFeesHistoriesWithWrongLastUpdate (int lastUpdate) {
        try {
            queryFeesHistoryWithWrongLastUpdate.setInt(1, lastUpdate);
            ResultSet results = queryFeesHistoryWithWrongLastUpdate.executeQuery();
            return setFeesHistories(results);
        } catch (SQLException e) {
            logger.error("Query fees history with wrong last update failed: ", e);
            return null;
        }
    }

    private List<FeesHistory> setFeesHistories(ResultSet results) throws SQLException {
        List<FeesHistory> feesHistories = new ArrayList<>();
        while (results.next()) {
            FeesHistory feesHistory = setFeesHistory(results);
            feesHistories.add(feesHistory);
        }
        return feesHistories;
    }

    








}
