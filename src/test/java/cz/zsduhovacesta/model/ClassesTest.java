package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ClassesTest {
    private Classes classes = new Classes();
    private Classes sameClass = classes;
    private Classes newClassWithSameValues = new Classes();
    private Classes differentClass = createDifferentClass();
    private Classes nullClass = null;

    private static Classes createDifferentClass() {
        Classes classes = new Classes();
        classes.setClassName("Class");
        return classes;
    }

    @Test
    public void testEqualsReflexive() {
        assertEquals(classes, classes);
        assertEquals(classes, sameClass);
    }

    @Test
    public void testEqualsSymmetric() {
        assertEquals(classes, sameClass);
        assertEquals(sameClass, classes);
        assertEquals(classes, newClassWithSameValues);
        assertEquals(newClassWithSameValues, classes);
        assertNotEquals(classes, differentClass);
        assertNotEquals(differentClass, classes);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(classes, nullClass);
        assertNotEquals(nullClass, differentClass);
    }

    @Test
    public void testEqualsTransitive() {
        assertEquals(classes, sameClass);
        assertEquals(sameClass, newClassWithSameValues);
        assertEquals(classes, newClassWithSameValues);
    }

    @Test
    public void testHashCode() {
        assertEquals(classes.hashCode(), sameClass.hashCode());
        assertEquals(classes.hashCode(), newClassWithSameValues.hashCode());
        assertNotEquals(classes.hashCode(), differentClass.hashCode());
    }
}