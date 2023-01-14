package agh.ics.oop;

public interface IMapElement {
    @Override
    String toString();

    Vector2d getPosition();

    String getResourceName();

    String getLabelText();
}
