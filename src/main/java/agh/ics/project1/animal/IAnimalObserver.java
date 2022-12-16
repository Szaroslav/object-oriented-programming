package agh.ics.project1.animal;

import agh.ics.oop.Vector2d;

public interface IAnimalObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);
    void animalsReproduced();
}
