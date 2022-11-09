package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class GrassField implements IWorldMap {
    private final Vector2d UPPER_RIGHT_BOUNDARY;
    private final Vector2d BOTTOM_LEFT_BOUNDARY = new Vector2d(0, 0);
    private final List<Animal> animals = new ArrayList<>();
    private final List<Grass> grasses = new ArrayList<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);

    public GrassField (int n) {
        UPPER_RIGHT_BOUNDARY = new Vector2d((int) Math.sqrt(10 * n), (int) Math.sqrt(10 * n));
    }

    @Override
    public String toString() {
        Vector2d[] boundary = getRealBoundary();
        System.out.println(visualizer.draw(BOTTOM_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY));
        return visualizer.draw(boundary[0], boundary[1]);
    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition()))
            return false;

        animals.add(animal);
        return true;
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        for (Animal animal : animals)
            if (animal.isAt(position))
                return animal;

        for (Grass grass : grasses)
            if (grass.getPosition().equals(position))
                return grass;

        return null;
    }

    public boolean plant(Grass grass) {
        if (isOccupied(grass.getPosition()) || !grass.getPosition().between(BOTTOM_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY))
            return false;

        grasses.add(grass);
        return true;
    }

    protected Vector2d[] getRealBoundary() {
        int bottomX = Integer.MAX_VALUE, bottomY = Integer.MAX_VALUE;
        int upperX = Integer.MIN_VALUE, upperY = Integer.MIN_VALUE;

        for (Animal a : animals) {
            bottomX = Math.min(bottomX, a.getPosition().x);
            bottomY = Math.min(bottomY, a.getPosition().y);
            upperX  = Math.max(upperX, a.getPosition().x);
            upperY  = Math.max(upperY, a.getPosition().y);
        }

        for (Grass g : grasses) {
            bottomX = Math.min(bottomX, g.getPosition().x);
            bottomY = Math.min(bottomY, g.getPosition().y);
            upperX  = Math.max(upperX, g.getPosition().x);
            upperY  = Math.max(upperY, g.getPosition().y);
        }

        return new Vector2d[]{new Vector2d(bottomX, bottomY), new Vector2d(upperX, upperY)};
    }
}
