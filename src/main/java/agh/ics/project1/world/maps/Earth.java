package agh.ics.project1.world.maps;

import agh.ics.oop.Vector2d;
import agh.ics.project1.animal.Animal;

public class Earth extends AbstractMap {
    @Override
    public void animalTriedToMove(Animal animal) {
        animal.invert();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.y < LOWER_LEFT_BOUNDARY.y || position.y > UPPER_RIGHT_BOUNDARY.y)
            return false;
        return true;
    }
}
