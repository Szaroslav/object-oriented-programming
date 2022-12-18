package agh.ics.oop;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class SimulationEngine implements IEngine, Runnable {
    private final int MOVE_DELAY = 500;
    private MoveDirection[] moveDirections;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(IWorldMap map, Vector2d[] initialPositions) {
        for (Vector2d position : initialPositions) {
            Animal a = new Animal(map, position);
            if (map.place(a))
                animals.add(a);
        }
    }

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] initialPositions) {
        this(map, initialPositions);
        moveDirections = directions;
    }

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] initialPositions, int grassNumber) {
        this(directions, map, initialPositions);

        GrassField grassField = (GrassField) map;
        for (int i = 0; i < grassNumber; i++) {
//            if (i == 0) grassField.plant(new Grass(new Vector2d(2, 3)));
            grassField.plant(new Grass(grassField.getEmptyPosition()));
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < moveDirections.length; i++) {
                Thread.sleep(MOVE_DELAY);
                animals.get(i % animals.size()).move(moveDirections[i]);
            }
        }
        catch (InterruptedException ex) {
            System.out.println("Symulacja została przerwana");
            System.out.println(ex);
        }
    }

    public void setMoveDirections(MoveDirection[] directions) {
        moveDirections = directions;
    }
}
