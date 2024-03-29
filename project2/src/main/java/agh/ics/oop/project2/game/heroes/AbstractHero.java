package agh.ics.oop.project2.game.heroes;

import agh.ics.oop.project2.game.world.elements.AbstractWorldElement;

public class AbstractHero extends AbstractWorldElement {
    public final int MAX_DISTANCE;
    public final boolean CAN_RIVER_MOVE;
    public final int[] EVENTS_TURNS_TO_SOLVE;
    public final int[] EVENTS_TURNS_TO_SOLVE_FASTER;
    private int currentMaxDistance;

    protected AbstractHero(int maxDistance, boolean riverMove, int[] solve, int[] solveFaster) {
        MAX_DISTANCE = maxDistance;
        CAN_RIVER_MOVE = riverMove;
        EVENTS_TURNS_TO_SOLVE = solve;
        EVENTS_TURNS_TO_SOLVE_FASTER = solveFaster;
    }

    public int getCurrentMaxDistance() {
        return currentMaxDistance;
    }

    public void setCurrentMaxDistance(int currentMaxDistance) {
        this.currentMaxDistance = currentMaxDistance;
    }

    public void move() {

    }
}
