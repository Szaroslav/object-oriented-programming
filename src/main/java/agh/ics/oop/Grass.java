package agh.ics.oop;

public class Grass extends AbstractWorldMapElement {
    public Grass(Vector2d pos) {
        position = pos;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!this.getClass().equals(other.getClass())) return false;

        Grass a = (Grass) other;
        return position.equals(a.position);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public String getResourceName() {
        return "src/main/resources/grass.png";
    }

    @Override
    public String getLabelText() {
        return "G " + position;
    }
}
