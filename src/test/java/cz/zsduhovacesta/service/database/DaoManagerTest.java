package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DaoManagerTest {

    @Mock
    private StudentDao studentDao;
    @Mock
    private ClassesDao classesDao;
    @Mock
    private BankStatementDao bankStatementDao;
    @Mock
    private TransactionDao transactionDao;
    @Mock
    private FeesHistoryDao feesHistoryDao;
    @Mock
    private Connection connection;


    @InjectMocks
    private DaoManager daoManager;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testClose() {
        try {
            daoManager.close();
            verify(studentDao, times(1)).close();
            verify(classesDao, times(1)).close();
            verify(bankStatementDao, times(1)).close();
            verify(transactionDao, times(1)).close();
            verify(feesHistoryDao, times(1)).close();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testHandlingExceptionFromClose() {
        try {
            doThrow(SQLException.class).when(studentDao).close();
            daoManager.close();
        } catch (SQLException ignored) {
            fail("DaoManager should catch exception and write to log");
        }
    }


    @Test
    public void testListAllStudents() {
        List<Student> students = new ArrayList<>();
        when(studentDao.queryAllStudents()).thenReturn(students);
        assertEquals(students, daoManager.listAllStudents());
    }

    @Test
    public void testListStudentsBySchoolStage() {
        List<Student> students = new ArrayList<>();
        when(studentDao.queryStudentsBySchoolStage("ZŠ")).thenReturn(students);
        assertEquals(students, daoManager.listStudentsBySchoolStage("ZŠ"));
    }

    @Test
    public void testListStudentsByClass() {
        List<Student> students = new ArrayList<>();
        when(studentDao.queryStudentsByClass("class")).thenReturn(students);
        assertEquals(students, daoManager.listStudentsByClass("class"));
    }

    @Test
    public void testListClassesNames() {
        List<String> classNames = new ArrayList<>();
        when(classesDao.queryClassesNames()).thenReturn(classNames);
        assertEquals(classNames, daoManager.listClassesNames());
    }

    @Test
    public void testListAllClasses() {
        List<Classes> classes = new ArrayList<>();
        when(classesDao.queryAllClasses()).thenReturn(classes);
        assertEquals(classes, daoManager.listAllClasses());
    }

    @Test
    public void testQueryClassIdByClassName() {
        when(classesDao.queryClassIdByClassName("class")).thenReturn(1);
        assertEquals(1, daoManager.queryClassIdByClassName("class"));
        assertNotEquals(1, daoManager.queryClassIdByClassName("anotherClass"));
    }

    @Test
    public void testQueryStudentByVs() {
        Student student = new Student();
        when(studentDao.queryStudentByVs(1)).thenReturn(student);
        assertEquals(student, daoManager.queryStudentByVs(1));
    }

    @Test
    public void testInsertStudent() {
        Student student = new Student();
        student.setVS(1);
        try {
            daoManager.insertStudent(student);
            verify(studentDao, times(1)).insertStudent(student);
            verify(feesHistoryDao, times(1)).insertFeesHistory(1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testInsertStudent_studentDaoException() {
        Student student = new Student();
        student.setVS(1);
        try {
            doThrow(Exception.class).when(studentDao).insertStudent(student);
            daoManager.insertStudent(student);
            fail("should throw exception");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testInsertStudent_FeesHistoryDaoException() {
        Student student = new Student();
        student.setVS(1);
        try {
            doThrow(Exception.class).when(feesHistoryDao).insertFeesHistory(1);
            daoManager.insertStudent(student);
            fail("should throw exception");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testEditStudent_vsIsSame() {
        Student student = new Student();
        student.setVS(1);
        try {
            daoManager.editStudent(1, student);
            verify(connection, times(1)).setAutoCommit(false);
            verify(studentDao, times(1)).deleteStudent(1);
            verify(studentDao, times(1)).insertStudent(student);
            verify(feesHistoryDao, times(0)).updateVs(anyInt(), anyInt());
            verify(connection, times(1)).setAutoCommit(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEditStudent_vsIsEdited() {
        Student editedStudent = new Student();
        editedStudent.setVS(1);
        try {
            daoManager.editStudent(2, editedStudent);
            verify(connection, times(1)).setAutoCommit(false);
            verify(studentDao, times(1)).deleteStudent(2);
            verify(studentDao, times(1)).insertStudent(editedStudent);
            verify(feesHistoryDao, times(1)).updateVs(2, 1);
            verify(connection, times(1)).setAutoCommit(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testInsertStudentException() {
        Student student = new Student();
        try {
            doThrow(Exception.class).when(studentDao).deleteStudent(1);
            daoManager.editStudent(1, student);
            fail("Should throw exception");
        } catch (Exception e) {
            try {
                verify(connection, times(1)).setAutoCommit(false);
                verify(connection, times(1)).rollback();
                verify(connection, times(1)).setAutoCommit(true);
            } catch (Exception e2) {
                fail();
            }
        }
    }

    @Test
    public void testDeleteStudent() {
        try {
            daoManager.deleteStudent(1);
            verify(studentDao, times(1)).deleteStudent(1);
            verify(feesHistoryDao, times(1)).deleteFeesHistory(1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testInsertClass() {
        Classes newClass = new Classes();
        try {
            daoManager.insertClass(newClass);
            verify(classesDao, times(1)).insertClass(newClass);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEditClass() {
        Classes editedClass = new Classes();
        try {
            daoManager.editClass(editedClass);
            verify(classesDao, times(1)).editClass(editedClass);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteClassWithAllStudents() {
        Classes classToDelete = new Classes();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            students.add(student);
        }
        try {
            when(studentDao.queryStudentsByClass(classToDelete.getClassName())).thenReturn(students);
            daoManager.deleteClassWithAllStudents(classToDelete);
            verify(studentDao, times(5)).deleteStudent(anyInt());
            verify(classesDao, times(1)).deleteClass(classToDelete);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteEmptyClass() {
        Classes classToDelete = new Classes();
        try {
            when(studentDao.queryStudentsByClass(classToDelete.getClassName())).thenReturn(new ArrayList<>());
            daoManager.deleteClassWithAllStudents(classToDelete);
            verify(studentDao, times(0)).deleteStudent(anyInt());
            verify(classesDao, times(1)).deleteClass(classToDelete);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testUpdateFeesHistoryByUser() {
        try {
            FeesHistory feesHistory = new FeesHistory();
            feesHistory.setStudentVs(1);
            when(feesHistoryDao.queryFeesHistoryByStudentVs(1)).thenReturn(feesHistory);
            daoManager.updateFeesHistoryByUser(feesHistory);
            verify(feesHistoryDao, times(1)).updateAllMonthsByUser(feesHistory);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testQueryFeesHistoryByStudentVs() {
        FeesHistory feesHistory = new FeesHistory();
        when(feesHistoryDao.queryFeesHistoryByStudentVs(1)).thenReturn(feesHistory);
        assertEquals(feesHistory, daoManager.queryFeesHistoryByStudentVs(1));
    }

    @Test
    public void testAutomaticUpdateShouldPayAndFeesHistory_updateIsActual() {
        try {
            when(feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(anyInt())).thenReturn(new ArrayList<>());
            daoManager.automaticUpdateShouldPayAndFeesHistory();
            verify(feesHistoryDao, times(0)).updateActualMonth(anyInt(), any());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAutomaticUpdateShouldPayAndFeesHistory_updateIsNotActual() {
        try {
            List<FeesHistory> feesHistories = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                FeesHistory feesHistory = new FeesHistory();
                feesHistories.add(feesHistory);
            }
            when(feesHistoryDao.queryFeesHistoriesWithWrongLastUpdate(anyInt())).thenReturn(feesHistories);
            when(studentDao.queryStudentByVs(anyInt())).thenReturn(new Student());
            when(feesHistoryDao.queryFeesHistoryByStudentVs(anyInt())).thenReturn(new FeesHistory());
            daoManager.automaticUpdateShouldPayAndFeesHistory();
            verify(feesHistoryDao, times(10)).updateActualMonth(anyInt(), any());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testListBankStatements() {
        List<BankStatement> bankStatements = new ArrayList<>();
        when(bankStatementDao.queryBankStatements()).thenReturn(bankStatements);
        assertEquals(bankStatements, daoManager.listBankStatements());
    }

    @Test
    public void testListTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionDao.queryAllTransactionsFromExistingStudent()).thenReturn(transactions);
        assertEquals(transactions, daoManager.listTransactionsFromExistingStudents());
    }

    @Test
    public void testInsertBankStatementWithAllTransaction() {
        BankStatement bankStatement = new BankStatement();
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Transaction transaction = new Transaction();
            transactions.add(transaction);
        }
        bankStatement.setTransactions(transactions);
        try {
            daoManager.insertBankStatementWithAllTransactions(bankStatement);
            verify(connection, times(1)).setAutoCommit(false);
            verify(transactionDao, times(8)).insertTransaction(any());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testInsertBankStatementExceptionFromTransactionDao() {
        BankStatement bankStatement = new BankStatement();
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Transaction transaction = new Transaction();
            transactions.add(transaction);
        }
        bankStatement.setTransactions(transactions);
        try {
            doThrow(Exception.class).when(transactionDao).insertTransaction(any());
            daoManager.insertBankStatementWithAllTransactions(bankStatement);
            fail();
        } catch (Exception e) {
            try {
                verify(connection, times(1)).rollback();
            } catch (SQLException e1) {
                fail();
            }
        }
    }

    @Test
    public void testInsertBankStatementExceptionFromBankStatementDao() {
        try {
            doThrow(Exception.class).when(bankStatementDao).insertBankStatement(any());
            daoManager.insertBankStatementWithAllTransactions(new BankStatement());
            fail();
        } catch (Exception e) {
            try {
                verify(connection, times(1)).rollback();
            } catch (SQLException e1) {
                fail();
            }
        }
    }

    @Test
    public void testInsertTransaction() {
        Transaction transaction = new Transaction();
        transaction.setVs(111);
        when(transactionDao.countStudentPayed(111)).thenReturn(500);
        try {
            daoManager.insertTransaction(transaction);
            verify(transactionDao, times(1)).insertTransaction(any());
            verify(studentDao, times(1)).updatePayed(111,500);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEditTransaction() {
        Transaction transaction = new Transaction();
        transaction.setVs(456);
        when(transactionDao.countStudentPayed(456)).thenReturn(159);
        try {
            daoManager.editTransaction(transaction);
            verify(transactionDao, times(1)).editTransaction(any());
            verify(studentDao, times(1)).updatePayed(456,159);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteTransaction() {
        Transaction transaction = new Transaction();
        transaction.setVs(753159);
        when(transactionDao.countStudentPayed(753159)).thenReturn(-1547);
        try {
            daoManager.deleteTransaction(transaction);
            verify(transactionDao, times(1)).deleteTransaction(any());
            verify(studentDao, times(1)).updatePayed(753159,-1547);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testListTransactionByVs() {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionDao.queryTransactionByVsFromExistingStudent(anyInt())).thenReturn(transactions);
        assertEquals(transactions, daoManager.listTransactionByVs(1));
    }
}
