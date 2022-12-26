package agh.ics.oop.project1.world;

public abstract class AbstractOrganism {
    protected Vector2d position;

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d newPosition) {
        position = newPosition;
    }
}