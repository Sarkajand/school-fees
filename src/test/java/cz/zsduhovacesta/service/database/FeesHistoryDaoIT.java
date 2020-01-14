package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.FeesHistory;
import cz.zsduhovacesta.model.Student;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeesHistoryDaoIT {

    private static final String DB_NAME = "schoolFees.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:src\\test\\resources\\database\\" + DB_NAME;
    private static Connection connection;
    private static FeesHistoryDao feesHistoryDao;

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
            feesHistoryDao = new FeesHistoryDao(connection);
            connection.beginRequest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void rollback() {
        try {
            connection.rollback();
            feesHistoryDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void queryFeesHistoriesWithWrongLastUpdate() {
        try {
            List<FeesHistory> feesHistories = feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(99);
            assertEquals(4, feesHistories.size());
        } catch (Exception e) {
            fail();
        }
    }

//    @Test
//    void queryFeesHistoriesWithWrongLastUpdateNoRecords() {
//        List<FeesHistory> feesHistories = feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate()
//    }

    @Test
    void queryFeesHistoryByVs() {
        FeesHistory feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(254325);
        assertEquals(254325, feesHistory.getStudentVs());
        assertEquals(0, feesHistory.getJanuary());
    }

    @Test
    void insertFeesHistory() {
        FeesHistory feesHistoryToInsert = new FeesHistory();
        feesHistoryToInsert.setStudentVs(111);
        try {
            feesHistoryDao.insertFeesHistory(111);
            List<FeesHistory> feesHistories = feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(99);
            assertEquals(5, feesHistories.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void updateAllMonthsByUser() {
        FeesHistory feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(254325);
        assertNotEquals(150, feesHistory.getJanuary());
        feesHistory.setJanuary(150);
        try {
            feesHistoryDao.updateAllMonthsByUser(feesHistory);
        } catch (Exception e) {
            fail();
        }
        feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(254325);
        assertEquals(150, feesHistory.getJanuary());
    }

    @Test
    void updateNotExistingFeesHistory() {
        FeesHistory feesHistory = new FeesHistory();
        try {
            feesHistoryDao.updateAllMonthsByUser(feesHistory);
            fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    void updateActualMonth() {
        int actualMonth = 1;
        Student student = new Student();
        student.setVS(254325);
        student.setFees(500);
        try {
            FeesHistory feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(254325);
            assertNotEquals(500, feesHistory.getJanuary());
            feesHistoryDao.updateActualMonth(actualMonth, student);
            feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(254325);
            assertEquals(500, feesHistory.getJanuary());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void updateVs() {
        FeesHistory feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(254325);
        assertNotNull(feesHistory);
        try {
            feesHistoryDao.updateVs(254325, 111);
        } catch (Exception e) {
            fail();
        }
        feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(254325);
        assertNull(feesHistory);
        feesHistory = feesHistoryDao.queryFeesHistoryByStudentVs(111);
        assertNotNull(feesHistory);
    }

    @Test
    void deleteFeesHistory() {
        List<FeesHistory> feesHistories = feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(99);
        assertEquals(4, feesHistories.size());
        try {
            feesHistoryDao.deleteFeesHistory(254325);
        } catch (Exception e) {
            fail();
        }
        feesHistories = feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(99);
        assertEquals(3, feesHistories.size());
    }

    @Test
    void deleteNotExistingFeesHistory() {
        try {
            feesHistoryDao.deleteFeesHistory(111);
            fail();
        } catch (Exception ignored) {

        }
    }
}