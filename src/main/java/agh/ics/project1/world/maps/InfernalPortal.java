package agh.ics.project1.world.maps;

import agh.ics.oop.Vector2d;
import agh.ics.project1.animal.Animal;
import agh.ics.project1.world.engine.WorldEngineConfig;

public class InfernalPortal extends AbstractMap {
    @Override
    public void animalPositionChanged(Vector2d oldPosition, Animal animal) {
        if (!isOrganismInBounds(animal)) {
            final int width = UPPER_RIGHT_BOUNDARY.x - LOWER_LEFT_BOUNDARY.x + 1;
            final int height = UPPER_RIGHT_BOUNDARY.y - LOWER_LEFT_BOUNDARY.y + 1;
            animal.decreaseEnergy(WorldEngineConfig.getInstance().getInt("ENERGY_PER_REPRODUCING"));
            animal.setPosition(new Vector2d(
                (int) (Math.random() * width + LOWER_LEFT_BOUNDARY.x), (int) (Math.random() * height + LOWER_LEFT_BOUNDARY.y)
            ));
        }

        super.animalPositionChanged(oldPosition, animal);
    }
}
