package cz.zsduhovacesta.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoManager {

    public static final String DB_NAME = "schoolFees.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:src\\main\\resources\\database\\" + DB_NAME;

    private static DaoManager instance = new DaoManager();

    private Connection connection;

    private StudentDao studentDao;
    private ClassesDao classesDao;

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
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            throw e;
        }
    }

    public void close () {
        try {
            if (connection != null) {
                connection.close();
            }
            if (studentDao != null) {
                studentDao.close();
            }
            if (classesDao != null) {
                classesDao.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }



}
