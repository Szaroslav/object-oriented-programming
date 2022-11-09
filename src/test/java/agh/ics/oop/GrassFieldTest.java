package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    @Test
    public void testCanMoveTo() {
        out.println("Sprawdzam metodę GrassField.canMoveTo()");
        IWorldMap map = createMap();

        assertTrue(map.canMoveTo(new Vector2d(-1, -1)));
        assertTrue(map.canMoveTo(new Vector2d(3, 11)));
        assertFalse(map.canMoveTo(new Vector2d(0, 0)));
        assertFalse(map.canMoveTo(new Vector2d(3, 12)));
    }

    @Test
    public void testPlace() {
        out.println("Sprawdzam metodę GrassField.place()");
        IWorldMap map = createMap();

        assertTrue( map.place(new Animal(map, new Vector2d(-5, 22))) );
        assertTrue( map.place(new Animal(map, new Vector2d(2, 12))) );
        assertFalse( map.place(new Animal(map, new Vector2d(0, 0))) );
        assertFalse( map.place(new Animal(map, new Vector2d(3, 12))) );
    }

    @Test
    public void testIsOccupied() {
        out.println("Sprawdzam metodę GrassField.isOccupied()");
        IWorldMap map = createMap();

        assertTrue(map.isOccupied(new Vector2d(0, 0)));
        assertTrue(map.isOccupied(new Vector2d(3, 12)));
        assertFalse(map.isOccupied(new Vector2d(4, 12)));
    }

    @Test
    public void testObjectAt() {
        out.println("Sprawdzam metodę GrassField.objectAt()");
        IWorldMap map = createMap();

        assertEquals(new Animal(map, new Vector2d(0, 0)), map.objectAt(new Vector2d(0, 0)));
        assertEquals(new Animal(map, new Vector2d(3, 12)), map.objectAt(new Vector2d(3, 12)));
        assertNull(map.objectAt(new Vector2d(4, 12)));
    }

    private IWorldMap createMap() {
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(0, 0)));
        map.place(new Animal(map, new Vector2d(3, 12)));

        return map;
    }
}
