package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final Vector2d UPPER_RIGHT_BOUNDARY;
    private final Vector2d BOTTOM_LEFT_BOUNDARY = new Vector2d(0, 0);
    private final List<Animal> animals = new ArrayList<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        UPPER_RIGHT_BOUNDARY = new Vector2d(width - 1, height - 1);
    }

    public String toString() {
        return visualizer.draw(BOTTOM_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY);
    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.between(BOTTOM_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition()))
            return false;

        animals.add(animal);
        return true;
    }

    public Object objectAt(Vector2d position) {
        for (Animal animal : animals)
            if (animal.isAt(position))
                return animal;
        return null;
    }
}
