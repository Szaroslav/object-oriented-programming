package agh.ics.project1;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

public class RotationTest {
    @Test
    public void testRotate() {
        out.println("Testing Rotation.rotate(int)");
        Rotation rotation = Rotation.NORTH;

        assertEquals(Rotation.NORTH_EAST, rotation.rotate(1));
        assertEquals(Rotation.SOUTH_WEST, rotation.rotate(5));
        assertEquals(Rotation.NORTH, rotation.rotate(8));
        assertEquals(Rotation.NORTH_WEST, rotation.rotate(-1));
        assertEquals(Rotation.SOUTH_WEST, rotation.rotate(-3));
        assertEquals(Rotation.NORTH_WEST, rotation.rotate(-9));
    }
}
