package agh.ics.oop;

public enum MoveDirection {
    FORWARD("f"),
    BACKWARD("b"),
    RIGHT("r"),
    LEFT("l");

    private String str;

    private MoveDirection(String str) {
        this.str = str;
    }

    public static MoveDirection fromString(String str) {
        return switch (str) {
            case "f" -> FORWARD;
            case "b" -> BACKWARD;
            case "l" -> LEFT;
            case "r" -> RIGHT;
            default -> null;
        };
    }

    public static String[] getStringRepresentations() {
        return new String[]{FORWARD.str, BACKWARD.str, LEFT.str, RIGHT.str};
    }
}
