package cz.zsduhovacesta.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankStatementTest {

    private static BankStatement bankStatement;
    private static BankStatement sameBankStatement;
    private static BankStatement newBankStatementWithSameValues;
    private static BankStatement differentBankStatement;
    private static BankStatement nullBankStatement;

    @BeforeAll
    public static void setup() {
        bankStatement = new BankStatement();
        sameBankStatement = bankStatement;
        newBankStatementWithSameValues = new BankStatement();
        differentBankStatement = new BankStatement();
        differentBankStatement.setId(111);
        nullBankStatement = null;
    }

    @Test
    void testEqualsReflexive() {
        assertEquals(bankStatement, bankStatement);
        assertEquals(bankStatement, sameBankStatement);
    }

    @Test
    void testEqualsSymmetric() {
        assertEquals(bankStatement, sameBankStatement);
        assertEquals(sameBankStatement, bankStatement);
        assertEquals(bankStatement, newBankStatementWithSameValues);
        assertEquals(newBankStatementWithSameValues, bankStatement);
        assertNotEquals(bankStatement, differentBankStatement);
        assertNotEquals(differentBankStatement, bankStatement);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(bankStatement, nullBankStatement);
        assertNotEquals(nullBankStatement, differentBankStatement);
    }

    @Test
    void testEqualsTransitive() {
        assertEquals(bankStatement, sameBankStatement);
        assertEquals(sameBankStatement, newBankStatementWithSameValues);
        assertEquals(bankStatement, newBankStatementWithSameValues);
    }

    @Test
    void testHashCode() {
        assertEquals(bankStatement.hashCode(), sameBankStatement.hashCode());
        assertEquals(bankStatement.hashCode(), newBankStatementWithSameValues.hashCode());
        assertNotEquals(bankStatement.hashCode(), differentBankStatement.hashCode());
    }
}