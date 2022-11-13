package agh.ics.oop;

public interface IMapElement {
    @Override
    String toString();

    Vector2d getPosition();
    boolean isAt(Vector2d pos);
}
