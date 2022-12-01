package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private final IWorldMap worldMap;
    private MapDirection direction = MapDirection.NORTH;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map) {
        worldMap = map;
        position = new Vector2d(2, 2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this(map);
        position = initialPosition;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection dir) {
        this(map, initialPosition);
        direction = dir;
    }

    @Override
    public String toString() {
        return switch (direction) {
            case NORTH ->   "N";
            case EAST ->    "E";
            case SOUTH ->   "S";
            case WEST ->    "W";
            default ->      null;
        };
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !this.getClass().equals(other.getClass())) return false;

        Animal a = (Animal) other;
        return position.equals(a.position) && direction == a.direction;
    }

    @Override
    public int hashCode() {
        return position.hashCode() + direction.ordinal();
    }

    @Override
    public String getResourceName() {
        final String RESOURCES_PATH = "src/main/resources/";

        return switch (direction) {
            case NORTH ->   RESOURCES_PATH + "bee-up.png";
            case EAST ->    RESOURCES_PATH + "bee-right.png";
            case SOUTH ->   RESOURCES_PATH + "bee-down.png";
            case WEST ->    RESOURCES_PATH + "bee-left.png";
            default ->      null;
        };
    }

    @Override
    public String getLabel() {
        return "A " + position;
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition, oldPosition;

        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                newPosition = position.add(this.direction.toUnitVector());
                if (worldMap.canMoveTo(newPosition)) {
                    oldPosition = position;
                    position = newPosition;
                    positionChanged(oldPosition, position);
                }
            }
            case BACKWARD -> {
                newPosition = position.subtract(this.direction.toUnitVector());
                if (worldMap.canMoveTo(newPosition)) {
                    oldPosition = position;
                    position = newPosition;
                    positionChanged(oldPosition, position);
                }
            }
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers)
            observer.positionChanged(oldPosition, newPosition);
    }
}
