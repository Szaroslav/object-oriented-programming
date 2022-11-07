package agh.ics.oop;

import java.util.List;
import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] directions;
    private final List<Animal> animals = new ArrayList<Animal>();

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] initialPositions) {
        this.directions = directions;
        for (Vector2d position : initialPositions) {
            Animal a = new Animal(map, position);
            if (map.place(a))
                animals.add(a);
        }
    }

    public void run() {
        for (int i = 0; i < directions.length; i++) {
            animals.get(i % animals.size()).move(directions[i]);
        }
    }
}
