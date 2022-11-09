package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("System wystartował");

        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions, 10);
        engine.run();
        out.println(map);

        out.println("System zakończył działanie");
    }
}