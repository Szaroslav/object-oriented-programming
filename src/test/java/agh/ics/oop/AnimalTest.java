package agh.ics.oop;

import static java.lang.System.out;
import static agh.ics.oop.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void testMove() {
        out.println("Sprawdzam pozycję z metody Animal.move()");
        Animal animal = new Animal();

        // animal.position = (2, 2), animal.direction = NORTH
        animal.moveRepeatedly(new MoveDirection[]{});
        assertTrue(animal.isAt(new Vector2d(2, 2)));
        assertTrue(animal.isDirectedAt(MapDirection.NORTH));

        // animal.position = (2, 2), animal.direction = NORTH
        animal.moveRepeatedly(new MoveDirection[]{ FORWARD, BACKWARD, BACKWARD });
        assertTrue(animal.isAt(new Vector2d(2, 1)));
        assertTrue(animal.isDirectedAt(MapDirection.NORTH));

        // animal.position = (2, 1), animal.direction = NORTH
        animal.moveRepeatedly(new MoveDirection[]{ RIGHT, FORWARD, RIGHT, BACKWARD, BACKWARD });
        assertTrue(animal.isAt(new Vector2d(3, 3)));
        assertTrue(animal.isDirectedAt(MapDirection.SOUTH));

        // animal.position = (3, 3), animal.direction = SOUTH
        animal.moveRepeatedly(new MoveDirection[]{ BACKWARD, BACKWARD, RIGHT, FORWARD });
        assertTrue(animal.isAt(new Vector2d(2, 4)));
        assertTrue(animal.isDirectedAt(MapDirection.WEST));

        // animal.position = (2, 4), animal.direction = WEST
        animal.moveRepeatedly(new MoveDirection[]{ RIGHT, RIGHT, LEFT, FORWARD, BACKWARD, LEFT, FORWARD, FORWARD, FORWARD });
        assertTrue(animal.isAt(new Vector2d(0, 3)));
        assertTrue(animal.isDirectedAt(MapDirection.WEST));
    }
}
