package agh.ics.oop;

public class GrassField implements IWorldMap {
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    public boolean place(Animal animal) {
        return true;
    }

    public boolean isOccupied(Vector2d position) {
        return true;
    }

    public Object objectAt(Vector2d position) {
        return null;
    }
}
