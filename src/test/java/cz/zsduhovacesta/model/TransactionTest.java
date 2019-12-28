package cz.zsduhovacesta.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TransactionTest {

    private static Transaction transaction;
    private static Transaction sameTransaction;
    private static Transaction newTransactionWithSameValues;
    private static Transaction differentTransaction;
    private static Transaction nullTransaction;

    @BeforeAll
    public static void setup() {
        transaction = new Transaction();
        sameTransaction = transaction;
        newTransactionWithSameValues = new Transaction();
        differentTransaction = new Transaction();
        differentTransaction.setAmount(111);
        nullTransaction = null;
    }

    @Test
    void testEqualsReflexive() {
        assertEquals(transaction, transaction);
        assertEquals(transaction, sameTransaction);
    }

    @Test
    void testEqualsSymmetric() {
        assertEquals(transaction, sameTransaction);
        assertEquals(sameTransaction, transaction);
        assertEquals(transaction, newTransactionWithSameValues);
        assertEquals(newTransactionWithSameValues, transaction);
        assertNotEquals(transaction, differentTransaction);
        assertNotEquals(differentTransaction, transaction);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(transaction, nullTransaction);
        assertNotEquals(nullTransaction, differentTransaction);
    }

    @Test
    void testEqualsTransitive() {
        assertEquals(transaction, sameTransaction);
        assertEquals(sameTransaction, newTransactionWithSameValues);
        assertEquals(transaction, newTransactionWithSameValues);
    }

    @Test
    void testHashCode() {
        assertEquals(transaction.hashCode(), sameTransaction.hashCode());
        assertEquals(transaction.hashCode(), newTransactionWithSameValues.hashCode());
        assertNotEquals(transaction.hashCode(), differentTransaction.hashCode());
    }

    @Test
    void setDate() {
        Transaction transaction = new Transaction();
        transaction.setDate("25.08.2019");
        CustomDate transactionDate = transaction.getDate();
        assertEquals(1566684000000L, transactionDate.getTime());
    }

    @Test
    void getStringDate() {
        Transaction transaction = new Transaction();
        transaction.setDate("08.10.2001");
        assertEquals("08.10.2001", transaction.getStringDate());
    }
}