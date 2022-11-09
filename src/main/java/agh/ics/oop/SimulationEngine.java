package agh.ics.oop;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] directions;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] initialPositions) {
        this.directions = directions;

        for (Vector2d position : initialPositions) {
            Animal a = new Animal(map, position);
            if (map.place(a))
                animals.add(a);
        }
    }

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] initialPositions, int grassNumber) {
        this(directions, map, initialPositions);

        GrassField field = (GrassField) map;
        for (int i = 0; i < grassNumber; i++) {
            Grass g;
            do {
                g = new Grass(new Vector2d((int) (Math.random() * grassNumber), (int) (Math.random() * grassNumber)));
            } while (!field.plant(g));
        }
    }

    public void run() {
        for (int i = 0; i < directions.length; i++) {
            animals.get(i % animals.size()).move(directions[i]);
        }
    }
}
