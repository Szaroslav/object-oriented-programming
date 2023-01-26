package agh.ics.oop.project2.game.world.elements;

import agh.ics.oop.project2.utils.Vector2d;

public abstract class AbstractWorldElement {
//    public final WorldElements elementType;
    protected Vector2d position;

//    protected AbstractWorldElement(WorldElements elementType) {
//        this.elementType = elementType;
//    }

    public Vector2d getPosition() {
        return position;
    }
}
