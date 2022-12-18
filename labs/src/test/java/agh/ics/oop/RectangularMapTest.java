package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    public void testCanMoveTo() {
        out.println("Sprawdzam metodę RectangularMap.canMoveTo()");
        IWorldMap map = createMap();

        assertTrue(map.canMoveTo(new Vector2d(3, 6)));
        assertFalse(map.canMoveTo(new Vector2d(0, 0)));
        assertFalse(map.canMoveTo(new Vector2d(-4, 0)));
    }

    @Test
    public void testPlace() {
        out.println("Sprawdzam metodę RectangularMap.place()");
        IWorldMap map = createMap();

        assertTrue( map.place(new Animal(map, new Vector2d(2, 6))) );
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> map.place(new Animal(map, new Vector2d(0, 0)))
        );
        assertTrue(ex.getMessage().contains("0, 0"));
    }

    @Test
    public void testIsOccupied() {
        out.println("Sprawdzam metodę RectangularMap.isOccupied()");
        IWorldMap map = createMap();

        assertTrue(map.isOccupied(new Vector2d(0, 0)));
        assertTrue(map.isOccupied(new Vector2d(9, 4)));
        assertFalse(map.isOccupied(new Vector2d(5, 4)));
    }

    @Test
    public void testObjectAt() {
        out.println("Sprawdzam metodę RectangularMap.objectAt()");
        IWorldMap map = createMap();

        assertEquals(new Animal(map, new Vector2d(0, 0)), map.objectAt(new Vector2d(0, 0)));
        assertEquals(new Animal(map, new Vector2d(9, 4)), map.objectAt(new Vector2d(9, 4)));
        assertNull(map.objectAt(new Vector2d(5, 4)));
    }

    private IWorldMap createMap() {
        IWorldMap map = new RectangularMap(10, 10);
        map.place(new Animal(map, new Vector2d(0, 0)));
        map.place(new Animal(map, new Vector2d(9, 4)));
        map.place(new Animal(map, new Vector2d(8, 16)));

        return map;
    }
}
