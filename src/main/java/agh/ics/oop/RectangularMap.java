package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {
    public RectangularMap(int width, int height) {
        super(new Vector2d(width - 1, height - 1));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && position.between(LOWER_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY);
    }

    @Override
    public Vector2d[] getBoundary() {
        return new Vector2d[]{LOWER_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY};
    }
}
