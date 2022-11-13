package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class GrassField extends AbstractWorldMap {
    private final List<Grass> grasses = new ArrayList<>();

    public GrassField (int n) {
        super(new Vector2d((int) Math.sqrt(10 * n), (int) Math.sqrt(10 * n)));
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        if (object != null)
            return object;

        for (IMapElement grass : grasses)
            if (grass.isAt(position))
                return grass;

        return null;
    }

    @Override
    public Vector2d[] getBoundary() {
        Vector2d[] boundaries = {
            new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE),
            new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE)
        };
        boundaries = getMinMax(animals, boundaries);
        boundaries = getMinMax(grasses, boundaries);

        return boundaries;
    }

    public boolean plant(Grass grass) {
        if (isOccupied(grass.getPosition()) || !grass.getPosition().between(LOWER_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY))
            return false;

        grasses.add(grass);
        return true;
    }

    protected Vector2d[] getMinMax(List<? extends IMapElement> mapElements, Vector2d[] boundaries) {
        for (IMapElement mapElement : mapElements) {
            boundaries[0] = boundaries[0].lowerLeft(mapElement.getPosition());
            boundaries[1] = boundaries[1].upperRight(mapElement.getPosition());
        }

        return boundaries;
    }
}
