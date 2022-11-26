package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d ANIMAL_LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    private final Vector2d ANIMAL_UPPER_RIGHT_BOUNDARY;

    public RectangularMap(int width, int height) {
        ANIMAL_UPPER_RIGHT_BOUNDARY = new Vector2d(width - 1, height - 1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && position.between(ANIMAL_LOWER_LEFT_BOUNDARY, ANIMAL_UPPER_RIGHT_BOUNDARY);
    }

    @Override
    public Vector2d[] getBoundary() {
        return new Vector2d[]{ANIMAL_LOWER_LEFT_BOUNDARY, ANIMAL_UPPER_RIGHT_BOUNDARY};
    }
}
