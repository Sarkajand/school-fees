package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.exceptions.EditRecordException;
import cz.zsduhovacesta.model.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassesDao {

    final Logger logger = LoggerFactory.getLogger(ClassesDao.class);

    public static final String TABLE_CLASSES = "classes";
    public static final String COLUMN_CLASSES_ID = "_id";
    public static final String COLUMN_CLASSES_STAGE = "stage";
    public static final String COLUMN_CLASSES_CLASS_NAME = "class_name";

    public static final String QUERY_CLASSES =
            "SELECT * FROM " + TABLE_CLASSES;
    public static final String QUERY_CLASS_ID_BY_CLASS_NAME =
            "SELECT " + COLUMN_CLASSES_ID + " FROM " + TABLE_CLASSES + " WHERE " + COLUMN_CLASSES_CLASS_NAME + " = ?";
    public static final String INSERT_CLASS =
            "INSERT INTO " + TABLE_CLASSES + " (" + COLUMN_CLASSES_STAGE + ", " + COLUMN_CLASSES_CLASS_NAME + ") VALUES (?, ?)";
    public static final String EDIT_CLASS =
            "UPDATE " + TABLE_CLASSES + " SET " + COLUMN_CLASSES_STAGE + " = ?,  " + COLUMN_CLASSES_CLASS_NAME + " = ? WHERE " +
                    COLUMN_CLASSES_ID + " = ?";
    public static final String DELETE_CLASS =
            "DELETE FROM " + TABLE_CLASSES + " WHERE " + COLUMN_CLASSES_ID + " = ?";

    private PreparedStatement queryClasses;
    private PreparedStatement queryClassIdByClassName;
    private PreparedStatement insertClass;
    private PreparedStatement editClass;
    private PreparedStatement deleteClass;

    ClassesDao(Connection connection) throws SQLException {
        try {
            queryClasses = connection.prepareStatement(QUERY_CLASSES);
            queryClassIdByClassName = connection.prepareStatement(QUERY_CLASS_ID_BY_CLASS_NAME);
            insertClass = connection.prepareStatement(INSERT_CLASS);
            editClass = connection.prepareStatement(EDIT_CLASS);
            deleteClass = connection.prepareStatement(DELETE_CLASS);
        } catch (SQLException e) {
            logger.error("Couldn't create prepared statement for ClassesDao", e);
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (queryClasses != null) {
                queryClasses.close();
            }
            if (queryClassIdByClassName != null) {
                queryClassIdByClassName.close();
            }
            if (insertClass != null) {
                insertClass.close();
            }
            if (editClass != null) {
                editClass.close();
            }
            if (deleteClass != null) {
                deleteClass.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close prepared statement in StudentDao: ", e);
            throw e;
        }
    }

    public List<String> queryClassesNames() {
        try {
            ResultSet results = queryClasses.executeQuery();
            List<String> classesNames = new ArrayList<>();
            if (results.next()) {
                do {
                    classesNames.add(results.getString(3));
                } while (results.next());
            }
            return classesNames;
        } catch (SQLException e) {
            logger.warn("Query classes failed: ", e);
            return Collections.emptyList();
        }
    }

    public List<Classes> queryAllClasses() {
        try {
            ResultSet results = queryClasses.executeQuery();
            return setClasses(results);
        } catch (SQLException e) {
            logger.warn("Query classes with stage failed: ", e);
            return Collections.emptyList();
        }
    }

    private List<Classes> setClasses(ResultSet results) throws SQLException {
        List<Classes> classes = new ArrayList<>();
        if (results.next()) {
            do {
                Classes newClass = setClass(results);
                classes.add(newClass);
            } while (results.next());
        }
        return classes;
    }

    private Classes setClass(ResultSet results) throws SQLException {
        Classes newClass = new Classes();
        newClass.setClassId(results.getInt(1));
        newClass.setClassName(results.getString(3));
        newClass.setStage(results.getString(2));
        return newClass;
    }

    public int queryClassIdByClassName(String className) {
        try {
            queryClassIdByClassName.setString(1, className);
            ResultSet results = queryClassIdByClassName.executeQuery();
            if (!results.next()) {
                throw new NullPointerException("No results from query class id by name for className " + className);
            } else {
                return results.getInt(1);
            }
        } catch (SQLException e) {
            logger.warn("Query class id failed: ", e);
            throw new NullPointerException("Query class id by name failed");
        }
    }

    public void insertClass(Classes classes) throws EditRecordException, SQLException {
        insertClass.setString(1, classes.getStage());
        insertClass.setString(2, classes.getClassName());

        int affectedRecords = insertClass.executeUpdate();
        if (affectedRecords != 1) {
            throw new EditRecordException("Inserting Class failed");
        }
    }

    public void editClass(Classes classToEdit) throws EditRecordException, SQLException {
        editClass.setString(1, classToEdit.getStage());
        editClass.setString(2, classToEdit.getClassName());
        editClass.setInt(3, classToEdit.getClassId());

        int affectedRecords = editClass.executeUpdate();
        if (affectedRecords != 1) {
            throw new EditRecordException("Editing class failed");
        }
    }

    public void deleteClass(Classes classToDelete) throws EditRecordException, SQLException {
        deleteClass.setInt(1, classToDelete.getClassId());

        int affectedRecords = deleteClass.executeUpdate();
        if (affectedRecords != 1) {
            throw new EditRecordException("Deleting class failed");
        }
    }
}
