package agh.ics.oop;

public enum MapDirection {
    NORTH, EAST, SOUTH, WEST;

    public String toString() {
        switch (this) {
            case NORTH: return "Północ";
            case EAST:  return "Wschód";
            case SOUTH: return "Południe";
            case WEST:  return "Zachód";
            default:    return "";
        }
    }

    public MapDirection next(MapDirection direction) {
        final int N = direction.ordinal();
        MapDirection[] directions = MapDirection.values();
        return directions[(N + 1) % directions.length];
    }

    public MapDirection previous(MapDirection direction) {
        final int N = direction.ordinal();
        MapDirection[] directions = MapDirection.values();
        return directions[Math.abs(N - 1) % directions.length];
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH: return new Vector2d(0, 1);
            case EAST:  return new Vector2d(1, 0);
            case SOUTH: return new Vector2d(0, -1);
            case WEST:  return new Vector2d(-1, 0);
            default:    return new Vector2d(0, 0);
        }
    }
}
