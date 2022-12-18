package agh.ics.project1.world.maps;

import agh.ics.oop.Vector2d;
import agh.ics.project1.animal.Animal;
import agh.ics.project1.world.engine.WorldEngineConfig;

public class InfernalPortal extends AbstractMap {
    @Override
    public void animalPositionChanged(Vector2d oldPosition, Animal animal) {
        if (!isOrganismInBounds(animal)) {
            animal.decreaseEnergy(WorldEngineConfig.getInstance().getInt("ENERGY_PER_REPRODUCING"));
            animal.setPosition(new Vector2d(
                (int) (Math.random() * width + LOWER_LEFT_BOUNDARY.x), (int) (Math.random() * height + LOWER_LEFT_BOUNDARY.y)
            ));
        }

        super.animalPositionChanged(oldPosition, animal);
    }
}
