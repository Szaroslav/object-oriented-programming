package agh.ics.oop.project1;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import agh.ics.oop.project1.utils.Vector2d;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void testEquals() {
        out.println("Sprawdzam metodę Vector2d.equals()");

        Vector2d v1, v2;

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(0, 0);
        assertEquals(v1, v2);

        v1 = new Vector2d(3, 0);
        v2 = new Vector2d(0, -2137);
        assertNotEquals(v1, v2);

        v1 = new Vector2d(0, 0);
        assertNotEquals(v1, new Object());
    }

    @Test
    public void testToString() {
        out.println("Sprawdzam metodę Vector2d.toString()");

        assertEquals("(0, -33)", new Vector2d(0, -33).toString());
        assertEquals("(-2147483648, 3)", new Vector2d(-2147483648, 3).toString());
    }

    @Test
    public void testPrecedes() {
        out.println("Sprawdzam metodę Vector2d.precedes()");

        Vector2d v1, v2;

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(1, 0);
        assertTrue(v1.precedes(v2));

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(0, 0);
        assertTrue(v1.precedes(v2));

        v1 = new Vector2d(12, 3);
        v2 = new Vector2d(0, 5);
        assertFalse(v1.precedes(v2));

        v1 = new Vector2d(-5, 0);
        v2 = new Vector2d(-10, 0);
        assertFalse(v1.precedes(v2));
    }

    @Test
    public void testFollows() {
        out.println("Sprawdzam metodę Vector2d.follows()");

        Vector2d v1, v2;

        v1 = new Vector2d(1, 0);
        v2 = new Vector2d(0, 0);
        assertTrue(v1.follows(v2));

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(0, 0);
        assertTrue(v1.follows(v2));

        v1 = new Vector2d(4, 325);
        v2 = new Vector2d(-2, 700);
        assertFalse(v1.follows(v2));

        v1 = new Vector2d(-1, 1);
        v2 = new Vector2d(-2, 2);
        assertFalse(v1.follows(v2));
    }

    @Test
    public void testUpperRight() {
        out.println("Sprawdzam metodę Vector2d.upperRight()");

        Vector2d v1, v2;

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(2, 6);
        assertEquals(new Vector2d(2, 6), v1.upperRight(v2));

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(4, -4);
        assertEquals(new Vector2d(4, 0), v1.upperRight(v2));
    }

    @Test
    public void testLowerLeft() {
        out.println("Sprawdzam metodę Vector2d.lowerLeft()");

        Vector2d v1, v2;

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(2, 6);
        assertEquals(new Vector2d(0, 0), v1.lowerLeft(v2));

        v1 = new Vector2d(0, 8);
        v2 = new Vector2d(4, 2);
        assertEquals(new Vector2d(0, 2), v1.lowerLeft(v2));
    }

    @Test
    public void testAdd() {
        out.println("Sprawdzam metodę Vector2d.add()");

        Vector2d v1, v2;

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(3, -2);
        assertEquals(new Vector2d(3, -2), v1.add(v2));

        v1 = new Vector2d(-3, -8);
        v2 = new Vector2d(-3, 2);
        assertEquals(new Vector2d(-6, -6), v1.add(v2));
    }

    @Test
    public void testSubtract() {
        out.println("Sprawdzam metodę Vector2d.subtract()");

        Vector2d v1, v2;

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(3, -2);
        assertEquals(new Vector2d(-3, 2), v1.subtract(v2));

        v1 = new Vector2d(-3, -8);
        v2 = new Vector2d(-3, 2);
        assertEquals(new Vector2d(0, -10), v1.subtract(v2));
    }

    @Test
    public void testOpposite() {
        out.println("Sprawdzam metodę Vector2d.opposite()");

        assertEquals(new Vector2d(0, 0), new Vector2d(0, 0).opposite());
        assertEquals(new Vector2d(5, -3), new Vector2d(-5, 3).opposite());
        assertEquals(new Vector2d(-128, 0), new Vector2d(128, 0).opposite());
    }

    @Test
    public void testBetween() {
        out.println("Sprawdzam metodę Vector2d.between()");
        Vector2d bottom =   new Vector2d(0, 0);
        Vector2d upper =    new Vector2d(4, 4);

        assertTrue(     new Vector2d(1, 3).between(bottom, upper));
        assertTrue(     new Vector2d(4, 2).between(bottom, upper));
        assertTrue(     new Vector2d(4, 4).between(bottom, upper));
        assertFalse(    new Vector2d(-1, 0).between(bottom, upper));
        assertFalse(    new Vector2d(3, -2).between(bottom, upper));
        assertFalse(    new Vector2d(6, 3).between(bottom, upper));
        assertFalse(    new Vector2d(3, 5).between(bottom, upper));
    }
}
