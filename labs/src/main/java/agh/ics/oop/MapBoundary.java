package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedSet<Vector2d> mapElementsX = new TreeSet<>(this::compareByPositionX);
    private final SortedSet<Vector2d> mapElementsY = new TreeSet<>(this::compareByPositionY);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        removeElement(oldPosition);
        addElement(newPosition);
    }

    public Vector2d[] getCorners() {
        return new Vector2d[]{
            mapElementsX.first().lowerLeft(mapElementsY.first()),
            mapElementsX.last().upperRight(mapElementsY.last())
        };
    }

    public boolean addElement(Vector2d position) {
        return mapElementsX.add(position) && mapElementsY.add(position);
    }

    public boolean removeElement(Vector2d position) {
        return mapElementsX.remove(position) && mapElementsY.remove(position);
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
