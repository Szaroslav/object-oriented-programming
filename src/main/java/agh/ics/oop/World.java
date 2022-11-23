package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("System wystartował");

        try {
            MoveDirection[] directions = OptionsParser.parse(args);
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(2,2) };
            IEngine engine = new SimulationEngine(directions, map, positions, 10);
            engine.run();
            out.println(map);
        }
        catch (IllegalArgumentException ex) {
            out.println(ex);
        }


        out.println("System zakończył działanie");
    }
}