package agh.ics.oop.project1;

import agh.ics.oop.project1.utils.Vector2d;

public class Organism {
    protected Vector2d position;
    protected int ageDays;

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d newPosition) {
        position = newPosition;
    }
}
