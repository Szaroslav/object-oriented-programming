package agh.ics.project1.world.maps;

import agh.ics.project1.Organism;
import agh.ics.project1.animal.Animal;
import agh.ics.project1.Grass;
import agh.ics.oop.Vector2d;
import agh.ics.project1.animal.IAnimalObserver;
import agh.ics.project1.world.engine.WorldEngineConfig;

import java.util.*;

public class AbstractMap implements IAnimalObserver {
    protected final Vector2d LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    protected final Vector2d UPPER_RIGHT_BOUNDARY = new Vector2d(
        WorldEngineConfig.getInstance().getInt("MAP_WIDTH") - 1,
        WorldEngineConfig.getInstance().getInt("MAP_HEIGHT") - 1
    );

    protected final List<Animal> animalsList = new ArrayList<>();
    protected final Map<Vector2d, TreeSet<Animal>> animalsMap = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();

    @Override
    public void animalTriedToMove(Animal animal) {}

    @Override
    public void animalPositionChanged(Vector2d oldPosition, Animal animal) {}

    public boolean place(Animal animal) {
        if (!isOrganismInBounds(animal))
            return false;

        animal.setMap(this);
        animal.setObserver(this);
        animalsList.add(animal);
        animalsMap.computeIfAbsent(animal.getPosition(), k -> new TreeSet<>());
        animalsMap.get(animal.getPosition()).add(animal);

        return true;
    }

    public boolean canMoveTo(Vector2d position) {
        return true;
    }

//    public void adjustAnimal

//    public boolean isOccupied(Vector2d position) {
//        return objectAt(position) != null;
//    }
//
//    public Object objectAt(Vector2d position) {
//        return animals.get(position);
//    }

//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
//        Animal a = animals.get(oldPosition);
//        animals.remove(oldPosition, a);
//        animals.put(newPosition, a);
//    }

    protected boolean isOrganismInBounds(Organism organism) {
        return organism.getPosition().between(LOWER_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY);
    }
}
