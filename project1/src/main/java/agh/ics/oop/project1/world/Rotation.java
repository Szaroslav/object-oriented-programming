package agh.ics.oop.project1.world;

public enum Rotation {
    NORTH(new Vector2d(0, 1)),
    NORTH_EAST(new Vector2d(1, 1)),
    EAST(new Vector2d(1, 0)),
    SOUTH_EAST(new Vector2d(1, -1)),
    SOUTH(new Vector2d(0, -1)),
    SOUTH_WEST(new Vector2d(-1, -1)),
    WEST(new Vector2d(-1, 0)),
    NORTH_WEST(new Vector2d(-1, 1));

    private final Vector2d position;

    Rotation(Vector2d position) {
        this.position = position;
    }

    public Vector2d toVector() {
        return position;
    }

    public static Rotation fromInt(int i) {
        Rotation[] rotations = Rotation.values();
        return rotations[i % rotations.length];
    }

    public static Rotation rotate(Rotation rotation, int rotationSteps) {
        final int currentIndex = rotation.ordinal();
        Rotation[] rotations = Rotation.values();
        return rotations[(currentIndex + rotationSteps) % rotations.length + ((currentIndex + rotationSteps) % rotations.length >= 0 ? 0 : rotations.length)];
    }

    public static Rotation invert(Rotation rotation) {
        Rotation[] rotations = Rotation.values();
        return rotations[(rotation.ordinal() + rotations.length / 2) % rotations.length];
    }
}
