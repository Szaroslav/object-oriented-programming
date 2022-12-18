package agh.ics.oop;

public enum MapDirection {
    NORTH("północ", new Vector2d(0, 1)),
    EAST("wschód", new Vector2d(1, 0)),
    SOUTH("południe", new Vector2d(0, -1)),
    WEST("zachód", new Vector2d(-1, 0));

    private final Vector2d position;
    private final String strDirection;

    MapDirection(String str, Vector2d p) {
        position = p;
        strDirection = str;
    }

    public String toString() {
        return strDirection;
    }

    public MapDirection next() {
        final int N = this.ordinal();
        MapDirection[] directions = MapDirection.values();
        return directions[(N + 1) % directions.length];
    }

    public MapDirection previous() {
        final int N = this.ordinal();
        MapDirection[] directions = MapDirection.values();
        return directions[(directions.length + (N - 1)) % directions.length];
    }

    public Vector2d toUnitVector() {
        return position;
    }
}
