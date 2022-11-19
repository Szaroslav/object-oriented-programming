package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {
    public RectangularMap(int width, int height) {
        upperRightBoundary = new Vector2d(width - 1, height - 1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && position.between(lowerLeftBoundary, upperRightBoundary);
    }

    @Override
    public Vector2d[] getBoundary() {
        return new Vector2d[]{lowerLeftBoundary, upperRightBoundary};
    }
}
