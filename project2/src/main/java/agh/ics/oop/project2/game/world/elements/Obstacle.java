package agh.ics.oop.project2.game.world.elements;

import agh.ics.oop.project2.utils.Vector2d;

public class Obstacle extends AbstractWorldElement {
    public final float slow;
    public final WorldElements elementType;

    public Obstacle(Vector2d position, WorldElements elementType, float slow) {
        this.position = position;
        this.elementType = elementType;
        this.slow = slow;
    }
}