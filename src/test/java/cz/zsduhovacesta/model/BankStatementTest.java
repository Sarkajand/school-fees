package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BankStatementTest {

    private static BankStatement bankStatement = new BankStatement();
    private static BankStatement sameBankStatement = bankStatement;
    private static BankStatement newBankStatementWithSameValues = new BankStatement();
    private static BankStatement differentBankStatement = createDifferentBankStatement();
    private static BankStatement nullBankStatement = null;

    private static BankStatement createDifferentBankStatement() {
        BankStatement bankStatement = new BankStatement();
        bankStatement.setId(111);
        return bankStatement;
    }

    @Test
    public void testEqualsReflexive() {
        assertEquals(bankStatement, bankStatement);
        assertEquals(bankStatement, sameBankStatement);
    }

    @Test
    public void testEqualsSymmetric() {
        assertEquals(bankStatement, sameBankStatement);
        assertEquals(sameBankStatement, bankStatement);
        assertEquals(bankStatement, newBankStatementWithSameValues);
        assertEquals(newBankStatementWithSameValues, bankStatement);
        assertNotEquals(bankStatement, differentBankStatement);
        assertNotEquals(differentBankStatement, bankStatement);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(bankStatement, nullBankStatement);
        assertNotEquals(nullBankStatement, differentBankStatement);
    }

    @Test
    public void testEqualsTransitive() {
        assertEquals(bankStatement, sameBankStatement);
        assertEquals(sameBankStatement, newBankStatementWithSameValues);
        assertEquals(bankStatement, newBankStatementWithSameValues);
    }

    @Test
    public void testHashCode() {
        assertEquals(bankStatement.hashCode(), sameBankStatement.hashCode());
        assertEquals(bankStatement.hashCode(), newBankStatementWithSameValues.hashCode());
        assertNotEquals(bankStatement.hashCode(), differentBankStatement.hashCode());
    }
}