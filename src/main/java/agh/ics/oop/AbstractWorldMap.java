package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    protected final Vector2d UPPER_RIGHT_BOUNDARY;
    protected final Vector2d LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    protected final List<Animal> animals = new ArrayList<>();
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

        animals.add(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) instanceof Animal;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (IMapElement animal : animals)
            if (animal.isAt(position))
                return animal;

        return null;
    }

    abstract Vector2d[] getBoundary();
}
