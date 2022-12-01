package agh.ics.oop;

public interface IMapElement {
    @Override
    String toString();
    Vector2d getPosition();
    String getResourceName();
    String getLabel();
    boolean isAt(Vector2d pos);
}
