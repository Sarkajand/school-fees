package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.Classes;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassesDaoIT {

    private static final String DB_NAME = "schoolFees.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:src\\test\\resources\\database\\" + DB_NAME;
    private static Connection connection;
    private static ClassesDao classesDao;

    @BeforeAll
    public static void setup () {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("connection to database failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown () {
        try{
            connection.close();
        } catch (SQLException e) {
            System.out.println("Closing failed: " + e.getMessage());
        }
    }

    @BeforeEach
    public void startTransaction () {
        try {
            classesDao = new ClassesDao(connection);
            connection.beginRequest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void rollback () {
        try {
            connection.rollback();
            classesDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void listClassesNames() {
        List<String> classesNames = classesDao.listClassesNames();
        List<String> controlList = new ArrayList<>();
        controlList.add("POHÁDKA");
        controlList.add("PODMOŘSKÝ SVĚT");
        controlList.add("AFRIKA");
        controlList.add("1.A");
        controlList.add("1.B");
        controlList.add("2.A");
        assertEquals(classesNames, controlList);
    }

    @Test
    void listClasses() {
        List<Classes> classes = classesDao.listClasses();
        int size = classes.size();
        assertEquals(6, size);
        assertEquals("POHÁDKA", classes.get(0).getClassName());
        assertEquals("ZŠ", classes.get(5).getStage());
    }

    @Test
    void insertClass() {
        Classes classToInsert = new Classes();
        classToInsert.setClassName("Test");
        classToInsert.setStage("MŠ");
        try {
            classesDao.insertClass(classToInsert);
            List<Classes> classes = classesDao.listClasses();
            int size = classes.size();
            assertEquals(7, size);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void editClass() {
        Classes classToEdit = new Classes();
        classToEdit.setClassId(1);
        classToEdit.setClassName("newName");
        classToEdit.setStage("newStage");
        try {
            List<Classes> classes = classesDao.listClasses();
            assertNotEquals(classToEdit,classes.get(0));
            classesDao.editClass(classToEdit);
            classes = classesDao.listClasses();
            assertEquals(classToEdit, classes.get(0));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void EditNotExistingClass() {
        Classes classToEdit = new Classes();
        classToEdit.setClassId(999);
        classToEdit.setClassName("newName");
        classToEdit.setStage("newStage");
        try {
            classesDao.editClass(classToEdit);
            fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    void deleteClass() {
        Classes classToDelete = new Classes();
        classToDelete.setClassId(1);
        try {
            classesDao.deleteClass(classToDelete);
            List<Classes> classes = classesDao.listClasses();
            int size = classes.size();
            assertEquals(5, size);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void deleteNotExistingClass() {
        Classes classToDelete = new Classes();
        classToDelete.setClassId(999);
        try {
            classesDao.deleteClass(classToDelete);
            fail();
        } catch (Exception ignored) {

        }
    }
}