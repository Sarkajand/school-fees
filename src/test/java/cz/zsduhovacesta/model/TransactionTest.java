package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TransactionTest {

    private Transaction transaction = new Transaction();
    private Transaction sameTransaction = transaction;
    private Transaction newTransactionWithSameValues = new Transaction();
    private Transaction differentTransaction = createDifferentTransaction();
    private Transaction nullTransaction = null;

    private static Transaction createDifferentTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(111);
        return transaction;
    }

    @Test
    public void testEqualsReflexive() {
        assertEquals(transaction, transaction);
        assertEquals(transaction, sameTransaction);
    }

    @Test
    public void testEqualsSymmetric() {
        assertEquals(transaction, sameTransaction);
        assertEquals(sameTransaction, transaction);
        assertEquals(transaction, newTransactionWithSameValues);
        assertEquals(newTransactionWithSameValues, transaction);
        assertNotEquals(transaction, differentTransaction);
        assertNotEquals(differentTransaction, transaction);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(transaction, nullTransaction);
        assertNotEquals(nullTransaction, differentTransaction);
    }

    @Test
    public void testEqualsTransitive() {
        assertEquals(transaction, sameTransaction);
        assertEquals(sameTransaction, newTransactionWithSameValues);
        assertEquals(transaction, newTransactionWithSameValues);
    }

    @Test
    public void testHashCode() {
        assertEquals(transaction.hashCode(), sameTransaction.hashCode());
        assertEquals(transaction.hashCode(), newTransactionWithSameValues.hashCode());
        assertNotEquals(transaction.hashCode(), differentTransaction.hashCode());
    }

    @Test
    public void testSetDate() {
        Transaction transaction = new Transaction();
        transaction.setDate("25.08.2019");
        CustomDate transactionDate = transaction.getDate();
        assertEquals(1566684000000L, transactionDate.getTime());
    }

    @Test
    public void testGetStringDate() {
        Transaction transaction = new Transaction();
        transaction.setDate("08.10.2001");
        assertEquals("08.10.2001", transaction.getStringDate());
    }
}