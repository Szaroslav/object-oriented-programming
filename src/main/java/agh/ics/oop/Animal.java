package agh.ics.oop;

import java.util.Locale;

public class Animal extends AbstractWorldMapElement {
    private final IWorldMap worldMap;
    private MapDirection direction = MapDirection.NORTH;

    public Animal(IWorldMap map) {
        worldMap = map;
        position = new Vector2d(2, 2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        worldMap = map;
        position = initialPosition;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection dir) {
        worldMap = map;
        position = initialPosition;
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
        if (!this.getClass().equals(other.getClass())) return false;

        Animal a = (Animal) other;
        return position.equals(a.position) && direction == a.direction;
    }

    @Override
    public int hashCode() {
        return position.hashCode() + direction.ordinal();
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition;

        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                newPosition = this.position.add(this.direction.toUnitVector());
                if (worldMap.canMoveTo(newPosition))
                    this.position = newPosition;
            }
            case BACKWARD -> {
                newPosition = this.position.subtract(this.direction.toUnitVector());
                if (worldMap.canMoveTo(newPosition))
                    this.position = newPosition;
            }
        }
    }
}
