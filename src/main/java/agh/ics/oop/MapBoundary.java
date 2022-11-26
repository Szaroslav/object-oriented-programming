package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    final SortedSet<Vector2d> mapElementsX = new TreeSet<>(this::compareByPositionX);
    final SortedSet<Vector2d> mapElementsY = new TreeSet<>(this::compareByPositionY);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        mapElementsX.remove(oldPosition);
        mapElementsX.add(newPosition);

        mapElementsY.remove(oldPosition);
        mapElementsY.add(newPosition);
    }

    public Vector2d[] get() {
        return new Vector2d[]{
            new Vector2d(mapElementsX.first().x, mapElementsY.first().y),
            new Vector2d(mapElementsX.last().x, mapElementsY.last().y)
        };
    }

    public boolean add(Vector2d position) {
        return mapElementsX.add(position) && mapElementsY.add(position);
    }

    private int compareByPositionX(Vector2d v1, Vector2d v2) {
        if (v1.x - v2.x != 0)
            return v1.x - v2.x;
        return v1.y - v2.y;
    }

    private int compareByPositionY(Vector2d v1, Vector2d v2) {
        if (v1.y - v2.y != 0)
            return v1.y - v2.y;
        return v1.x - v2.x;
    }
}
