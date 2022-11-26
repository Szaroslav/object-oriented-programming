package agh.ics.oop;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);

    @Override
    public String toString() {
        Vector2d[] b = getBoundary();
        if (b.length < 2)
            return null;
        return visualizer.draw(b[0], b[1]);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public boolean place(Animal animal) {
        if (isOccupied(animal.getPosition()))
            throw new IllegalArgumentException(animal.getPosition() + " is already occupied");

        animal.addObserver(this);
        animals.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal a = animals.get(oldPosition);
        animals.remove(oldPosition, a);
        animals.put(newPosition, a);
    }

    public abstract Vector2d[] getBoundary();
}
