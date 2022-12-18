package agh.ics.oop;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {
    @Test
    public void testNext() {
       out.println("Sprawdzam metodę MapDirection.next()");
       assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
       assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
       assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
       assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
    }

    @Test
    public void testPrevious() {
        out.println("Sprawdzam metodę MapDirection.previous()");
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
    }

    @Test
    public void testToUnitVector() {
        out.println("Sprawdzam metodę MapDirection.toUnitVector()");
        assertEquals(new Vector2d(0, 1), MapDirection.NORTH.toUnitVector());
        assertEquals(new Vector2d(1, 0), MapDirection.EAST.toUnitVector());
        assertEquals(new Vector2d(0, -1), MapDirection.SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1, 0), MapDirection.WEST.toUnitVector());
    }
}
