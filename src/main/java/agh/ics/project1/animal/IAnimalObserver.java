package agh.ics.project1.animal;

import agh.ics.oop.Vector2d;

public interface IAnimalObserver {
    void animalTriedToMove(Animal animal);
    void animalPositionChanged(Vector2d oldPosition, Animal animal);
}
