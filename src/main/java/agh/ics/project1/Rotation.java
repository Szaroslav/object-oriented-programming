package agh.ics.project1;

import agh.ics.oop.Vector2d;

public enum Rotation {
    NORTH(new Vector2d(0, 1)),
    NORTH_EAST(new Vector2d(1, 1)),
    EAST(new Vector2d(1, 0)),
    SOUTH_EAST(new Vector2d(1, -1)),
    SOUTH(new Vector2d(0, -1)),
    SOUTH_WEST(new Vector2d(-1, -1)),
    WEST(new Vector2d(-1, 0)),
    NORTH_WEST(new Vector2d(-1, 1));

    private Vector2d position;

    private Rotation(Vector2d position) {
        this.position = position;
    }

    public Vector2d toVector() {
        return position;
    }

    public Rotation rotate(int rotationSteps) {
        final int currentIndex = this.ordinal();
        Rotation[] rotations = Rotation.values();
        return rotations[(currentIndex + rotationSteps) % rotations.length + ((currentIndex + rotationSteps) % rotations.length >= 0 ? 0 : rotations.length)];
    }
}
