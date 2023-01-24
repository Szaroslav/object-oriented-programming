package agh.ics.oop.project2.game.world;

public enum EventTypes {
    FIRE(false),
    PUZZLE(false),
    TECHNICAL_ISSUE(false),
    SUPERVILLAIN(true);

    public final boolean IS_CRITICAL;

    EventTypes(boolean isCritical) {
        IS_CRITICAL = isCritical;
    }
}
