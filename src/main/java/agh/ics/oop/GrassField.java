package agh.ics.oop;

import java.util.HashMap;
import java.lang.Math;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    private final Vector2d GRASS_LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    private final Vector2d GRASS_UPPER_RIGHT_BOUNDARY;
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final MapBoundary mapBoundary = new MapBoundary();

    public GrassField (int n) {
        GRASS_UPPER_RIGHT_BOUNDARY = new Vector2d((int) Math.sqrt(10 * n), (int) Math.sqrt(10 * n));
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (super.objectAt(position) != null)
            return super.objectAt(position);

        return grasses.get(position);
    }

    @Override
    public Vector2d[] getBoundary() {
        return mapBoundary.get();
    }

    @Override
    public boolean place(Animal animal) {
        super.place(animal);
        animal.addObserver(mapBoundary);
        mapBoundary.add(animal.getPosition());

        return true;
    }

    public boolean plant(Grass grass) {
        if (objectAt(grass.getPosition()) != null || !grass.getPosition().between(GRASS_LOWER_LEFT_BOUNDARY, GRASS_UPPER_RIGHT_BOUNDARY))
            return false;

        grasses.put(grass.getPosition(), grass);
        mapBoundary.add(grass.getPosition());
        return true;
    }
}