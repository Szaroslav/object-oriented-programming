package agh.ics.oop;

import java.util.HashMap;
import java.lang.Math;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    private final Vector2d GRASS_LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    private final Vector2d GRASS_UPPER_RIGHT_BOUNDARY;
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField (int n) {
        lowerLeftBoundary = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        upperRightBoundary = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
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
        return new Vector2d[]{lowerLeftBoundary, upperRightBoundary};
    }

    @Override
    public boolean place(Animal animal) {
        if (super.place(animal)) {
            setBoundaries(animal.getPosition());
            return true;
        }

        return false;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        super.positionChanged(oldPosition, newPosition);
        setBoundaries(newPosition);
    }

    public boolean plant(Grass grass) {
        if (objectAt(grass.getPosition()) != null || !grass.getPosition().between(GRASS_LOWER_LEFT_BOUNDARY, GRASS_UPPER_RIGHT_BOUNDARY))
            return false;

        grasses.put(grass.getPosition(), grass);
        setBoundaries(grass.getPosition());
        return true;
    }

    protected void setBoundaries(Vector2d point) {
        lowerLeftBoundary = lowerLeftBoundary.lowerLeft(point);
        upperRightBoundary = upperRightBoundary.upperRight(point);
    }
}