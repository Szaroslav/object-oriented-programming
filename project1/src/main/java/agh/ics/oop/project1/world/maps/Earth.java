package agh.ics.oop.project1.world.maps;

import agh.ics.oop.project1.world.Vector2d;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.world.config.WorldConfig;

public class Earth extends AbstractMap {
    public Earth(WorldConfig config) {
        super(config);
    }

    @Override
    public void animalTriedToMove(Animal animal) { // obie mapy obsługują wyjście zwierzęcia poza granice innymi metodami
        animal.invert();
    }

    @Override
    public void animalPositionChanged(Vector2d oldPosition, Animal animal) {
        if (animal.getPosition().x >= LOWER_LEFT_BOUNDARY.x && animal.getPosition().x <= UPPER_RIGHT_BOUNDARY.x)
            return;

        final int x = animal.getPosition().x;
        animal.setPosition(new Vector2d(x < LOWER_LEFT_BOUNDARY.x ? UPPER_RIGHT_BOUNDARY.x : LOWER_LEFT_BOUNDARY.x, animal.getPosition().y));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.y >= LOWER_LEFT_BOUNDARY.y && position.y <= UPPER_RIGHT_BOUNDARY.y;
    }
}
