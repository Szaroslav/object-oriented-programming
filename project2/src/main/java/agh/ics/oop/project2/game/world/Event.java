package agh.ics.oop.project2.game.world;

public class Event extends AbstractWorldElement {
    public final EventTypes EVENT_TYPE;
    public final int INITIAL_TURNS_TO_SOLVE;
    public final int MIN_TURNS;
    public final int MAX_TURNS;
    private int turnsToSolve;
    private int activeTurns;

    public Event(EventTypes type, int turnsToSolve) {
        EVENT_TYPE = type;
        INITIAL_TURNS_TO_SOLVE = this.turnsToSolve = turnsToSolve;

        if (!type.IS_CRITICAL) {
            MIN_TURNS = 3;
            MAX_TURNS = 5;
            activeTurns = 3 * turnsToSolve;
        }
        else {
            MIN_TURNS = 5;
            MAX_TURNS = 7;
            activeTurns = 2 * turnsToSolve;
        }
    }

    public int getTurnsToSolve() {
        return turnsToSolve;
    }

    public void setTurnsToSolve(int turnsToSolve) {
        this.turnsToSolve = turnsToSolve;
    }
}
