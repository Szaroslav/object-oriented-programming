package agh.ics.oop.project1.world.maps;

import agh.ics.oop.project1.world.Vector2d;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.world.config.WorldConfig;

public class InfernalPortal extends AbstractMap {
    public InfernalPortal(WorldConfig config) {
        super(config);
    }

    @Override
    public void animalPositionChanged(Vector2d oldPosition, Animal animal) {
        if (!isOrganismInBounds(animal)) {
            animal.decreaseEnergy(config.getInt("ENERGY_PER_REPRODUCING"));
            animal.setPosition(new Vector2d(
                (int) (Math.random() * WIDTH + LOWER_LEFT_BOUNDARY.x), (int) (Math.random() * HEIGHT + LOWER_LEFT_BOUNDARY.y)
            ));
        }

        super.animalPositionChanged(oldPosition, animal);
    }
}
