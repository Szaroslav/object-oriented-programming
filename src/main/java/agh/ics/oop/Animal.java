package agh.ics.oop;

import java.util.Locale;

public class Animal {
    private final IWorldMap worldMap;
    private Vector2d position = new Vector2d(2, 2);
    private MapDirection direction = MapDirection.NORTH;

    public Animal(IWorldMap map) {
        worldMap = map;
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

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!this.getClass().equals(other.getClass())) return false;

        Animal a = (Animal) other;
        return position.equals(a.position) && direction == a.direction;
    }

    public int hashCode() {
        return position.hashCode() + direction.ordinal();
    }

    public String toString() {
        return switch (direction) {
            case NORTH ->   "N";
            case EAST ->    "E";
            case SOUTH ->   "S";
            case WEST ->    "W";
            default ->      null;
        };
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public boolean isDirectedAt(MapDirection direction) {
        return this.direction == direction;
    }

    public Vector2d getPosition() {
        return position;
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

    public void moveRepeatedly(MoveDirection[] directions) {
        for (MoveDirection d : directions) move(d);
    }
}
