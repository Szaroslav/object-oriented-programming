package agh.ics.oop;

import java.util.HashMap;
import java.lang.Math;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    private Vector2d realLowerLeftBoundary = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vector2d realUpperRightBoundary = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField (int n) {
        super(new Vector2d((int) Math.sqrt(10 * n), (int) Math.sqrt(10 * n)));
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        if (object != null)
            return object;

        return grasses.get(position);
    }

    @Override
    public Vector2d[] getBoundary() {
        return new Vector2d[]{realLowerLeftBoundary, realUpperRightBoundary};
    }

    @Override
    public boolean place(Animal animal) {
        if (super.place(animal)) {
            setBoundaries(animal.getPosition());
            return true;
        }

        return false;
    }

    public boolean plant(Grass grass) {
        if (objectAt(grass.getPosition()) != null || !grass.getPosition().between(LOWER_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY))
            return false;

        grasses.put(grass.getPosition(), grass);
        setBoundaries(grass.getPosition());
        return true;
    }

    protected void setBoundaries(Vector2d point) {
        realLowerLeftBoundary = realLowerLeftBoundary.lowerLeft(point);
        realUpperRightBoundary = realUpperRightBoundary.upperRight(point);
    }
}