package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap {
    protected final Vector2d UPPER_RIGHT_BOUNDARY;
    protected final Vector2d LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);

    protected AbstractWorldMap(Vector2d upperRight) {
        UPPER_RIGHT_BOUNDARY = upperRight;
    }

    @Override
    public String toString() {
        Vector2d[] b = getBoundary();
        if (b.length < 2)
            return null;
        return visualizer.draw(b[0], b[1]);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition()))
            return false;

        animals.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) instanceof Animal;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    abstract Vector2d[] getBoundary();
}
