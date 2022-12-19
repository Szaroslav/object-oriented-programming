package agh.ics.oop.project1.animal;

import agh.ics.oop.project1.utils.Vector2d;

public interface IAnimalObserver {
    void animalTriedToMove(Animal animal);
    void animalPositionChanged(Vector2d oldPosition, Animal animal);
    void animalDied(Vector2d position);
}
