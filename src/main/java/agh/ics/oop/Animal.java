package agh.ics.oop;

import java.util.Locale;

public class Animal {
    private Vector2d position = new Vector2d(2, 2);
    private MapDirection direction = MapDirection.NORTH;

    public String toString() {
        assert direction.toString() != null;
        return "Zwierzak znajduje sie na pozycji " + position.toString() + " oraz jego orientacja to " + direction.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public boolean isDirectedAt(MapDirection direction) {
        return this.direction == direction;
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition;

        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                newPosition = this.position.add(this.direction.toUnitVector());
                if (newPosition.between(World.BOUNDARY_BOTTOM_LEFT, World.BOUNDARY_UPPER_RIGHT))
                    this.position = newPosition;
            }
            case BACKWARD -> {
                newPosition = this.position.subtract(this.direction.toUnitVector());
                if (newPosition.between(World.BOUNDARY_BOTTOM_LEFT, World.BOUNDARY_UPPER_RIGHT))
                    this.position = newPosition;
            }
        }
    }

    public void moveRepeatedly(MoveDirection[] directions) {
        for (MoveDirection d : directions) move(d);
    }
}
