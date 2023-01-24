package agh.ics.oop.project2.game.world;

import agh.ics.oop.project2.utils.Vector2d;

public class Obstacle extends AbstractWorldElement {
    float slow;

    public Obstacle(Vector2d position, float slow) {
        this.position = position;
        this.slow = slow;
    }
}