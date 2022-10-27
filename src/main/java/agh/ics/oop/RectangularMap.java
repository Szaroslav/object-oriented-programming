package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final Vector2d size;
    private List<Animal> animals = new ArrayList<>();

    public RectangularMap(int width, int height) {
        size = new Vector2d(width, height);
    }

    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0, 0), size.subtract(new Vector2d(1, 1)));
    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.between(new Vector2d(0, 0), size.subtract(new Vector2d(1, 1)));
    }

    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals)
            if (animal.isAt(position))
                return true;
        return false;
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

    public Animal getAnimal(int i) {
        if (i >= animals.size() || i < 0)
            return null;
        return animals.get(i);
    }

    public int getAnimalNumber() {
        return animals.size();
    }
}
