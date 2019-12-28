package cz.zsduhovacesta.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassesTest {
    private static Classes classes;
    private static Classes sameClass;
    private static Classes newClassWithSameValues;
    private static Classes differentClass;
    private static Classes nullClass;

    @BeforeAll
    public static void setup() {
        classes = new Classes();
        sameClass = classes;
        newClassWithSameValues = new Classes();
        differentClass = new Classes();
        differentClass.setClassName("Test");
        nullClass = null;
    }

    @Test
    void testEqualsReflexive() {
        assertEquals(classes, classes);
        assertEquals(classes, sameClass);
    }

    @Test
    void testEqualsSymmetric() {
        assertEquals(classes, sameClass);
        assertEquals(sameClass, classes);
        assertEquals(classes, newClassWithSameValues);
        assertEquals(newClassWithSameValues, classes);
        assertNotEquals(classes, differentClass);
        assertNotEquals(differentClass, classes);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(classes, nullClass);
        assertNotEquals(nullClass, differentClass);
    }

    @Test
    void testEqualsTransitive() {
        assertEquals(classes, sameClass);
        assertEquals(sameClass, newClassWithSameValues);
        assertEquals(classes, newClassWithSameValues);
    }

    @Test
    void testHashCode() {
        assertEquals(classes.hashCode(), sameClass.hashCode());
        assertEquals(classes.hashCode(), newClassWithSameValues.hashCode());
        assertNotEquals(classes.hashCode(), differentClass.hashCode());
    }
}