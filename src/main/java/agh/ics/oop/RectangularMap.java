package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final Vector2d size;
    private List<Animal> animals = new ArrayList<Animal>();

    public RectangularMap(int width, int height) {
        size = new Vector2d(width, height);
    }

    public String toString() {

    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.between(new Vector2d(0, 0), size);
    }

    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals)
            if (animal.isAt(position))
                return true;
        return false;
    }

    public boolean place(Animal animal) {
        if (animal.position)
    }

    public Object objectAt(Vector2d position) {
        for (Animal animal : animals)
            if (animal.isAt(position))
                return animal;
        return null;
    }
}
