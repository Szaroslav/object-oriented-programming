package agh.ics.oop.project1.world;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!this.getClass().equals(other.getClass())) return false;

        Vector2d v = (Vector2d) other;
        return this.x == v.x && this.y == v.y;
    }

    public int hashCode() {
        return 83 * this.x + this.y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    public boolean between(Vector2d v1, Vector2d v2) {
        return this.follows(v1) && this.precedes(v2);
    }
}

