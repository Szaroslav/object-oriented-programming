package agh.ics.oop;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EngineTest {
    @Test
    public void testEngine() {
        out.println("Sprawdzam silnik");

        MoveDirection[] directions = OptionsParser.parse(new String[]{
            "f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"
        });
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertEquals( new Animal(map, new Vector2d(2, 0), MapDirection.SOUTH), map.objectAt(new Vector2d(2, 0)) );
        assertEquals( new Animal(map, new Vector2d(3, 4), MapDirection.NORTH), map.objectAt(new Vector2d(3, 4)) );

        directions = OptionsParser.parse(new String[]{
                "l", "b", "b", "f", "f", "b", "f", "f", "b", "r", "f", "b", "f", "r", "b", "l", "f", "b"
        });
        map = new RectangularMap(8, 8);
        positions = new Vector2d[]{ new Vector2d(2,2), new Vector2d(0,0), new Vector2d(4, 3) };
        engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertEquals( new Animal(map, new Vector2d(0, 3), MapDirection.WEST), map.objectAt(new Vector2d(0, 3)) );
        assertEquals( new Animal(map, new Vector2d(1, 1), MapDirection.EAST), map.objectAt(new Vector2d(1, 1)) );
        assertEquals( new Animal(map, new Vector2d(4, 0), MapDirection.NORTH), map.objectAt(new Vector2d(4, 0)) );
    }
}
