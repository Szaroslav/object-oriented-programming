package agh.ics.oop.project1;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

public class RotationTest {
    @Test
    public void testRotate() {
        out.println("Testing Rotation.rotate(Rotation, int)");
        Rotation rotation = Rotation.NORTH;

        assertEquals(Rotation.NORTH_EAST, Rotation.rotate(rotation, 1));
        assertEquals(Rotation.SOUTH_WEST, Rotation.rotate(rotation, 5));
        assertEquals(Rotation.NORTH, Rotation.rotate(rotation, 8));
        assertEquals(Rotation.NORTH_WEST, Rotation.rotate(rotation, -1));
        assertEquals(Rotation.SOUTH_WEST, Rotation.rotate(rotation, -3));
        assertEquals(Rotation.NORTH_WEST, Rotation.rotate(rotation, -9));
    }

    @Test
    public void testInvert() {
        out.println("Testing Rotation.invert(Rotation)");

        assertEquals(Rotation.SOUTH, Rotation.invert(Rotation.NORTH));
        assertEquals(Rotation.SOUTH_WEST, Rotation.invert(Rotation.NORTH_EAST));
        assertEquals(Rotation.WEST, Rotation.invert(Rotation.EAST));
        assertEquals(Rotation.NORTH_WEST, Rotation.invert(Rotation.SOUTH_EAST));
        assertEquals(Rotation.NORTH, Rotation.invert(Rotation.SOUTH));
        assertEquals(Rotation.NORTH_EAST, Rotation.invert(Rotation.SOUTH_WEST));
        assertEquals(Rotation.EAST, Rotation.invert(Rotation.WEST));
        assertEquals(Rotation.SOUTH_EAST, Rotation.invert(Rotation.NORTH_WEST));
    }
}
