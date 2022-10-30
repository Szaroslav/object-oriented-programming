package agh.ics.oop;

public class SimulationEngine implements IEngine {
    private MoveDirection[] directions;
    private RectangularMap worldMap;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] initialPositions) {
        this.directions = directions;
        this.worldMap = (RectangularMap) map;
        for (Vector2d position : initialPositions)
            map.place(new Animal(map, position));
    }

    public void run() {
        for (int i = 0; i < directions.length; i++) {
            worldMap.getAnimal(i % worldMap.getAnimalsNumber()).move(directions[i]);
        }
    }
}
