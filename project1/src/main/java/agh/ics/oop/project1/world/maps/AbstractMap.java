package agh.ics.oop.project1.world.maps;

import agh.ics.oop.project1.Organism;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.plant.Grass;
import agh.ics.oop.project1.animal.IAnimalObserver;
import agh.ics.oop.project1.plant.PlantsGrowthVariant;
import agh.ics.oop.project1.utils.Pair;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.world.engine.WorldEngineConfig;
import agh.ics.oop.project1.utils.Vector2d;

import java.util.*;

public class AbstractMap implements IAnimalObserver {
    protected final int width = WorldEngineConfig.getInstance().getInt("MAP_WIDTH");
    protected final int height = WorldEngineConfig.getInstance().getInt("MAP_HEIGHT");
    protected final Vector2d LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    protected final Vector2d UPPER_RIGHT_BOUNDARY = new Vector2d(width - 1, height - 1);

    protected final List<Animal> animalsList = new ArrayList<>();
    protected final Map<Vector2d, TreeSet<Animal>> animalsMap = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final PlantsGrowthVariant growthVariant = PlantsGrowthVariant.fromString(WorldEngineConfig.getInstance().getProperty("PLANTS_GROWTH_VARIANT"));

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

    public void plant() {
        int prefferedFieldChance = Random.range(0, 100);
        Vector2d position;

        if (growthVariant == PlantsGrowthVariant.FOREST_EQUATORS) {
            int equatorHeight = (int) (height * 0.2);
            equatorHeight += equatorHeight % 2 == 0 && height % 2 == 1 || equatorHeight % 2 == 1 && height % 2 == 0 ? 1 : 0;
            Vector2d lowerLeft = new Vector2d(LOWER_LEFT_BOUNDARY.x, LOWER_LEFT_BOUNDARY.y + (height - equatorHeight) / 2 + 1);
            Vector2d upperRight = new Vector2d(UPPER_RIGHT_BOUNDARY.x, LOWER_LEFT_BOUNDARY.y + (height - equatorHeight) / 2 + 1 + equatorHeight);

            do {
                position = new Vector2d(Random.range(LOWER_LEFT_BOUNDARY.x, UPPER_RIGHT_BOUNDARY.x), Random.range(LOWER_LEFT_BOUNDARY.y, UPPER_RIGHT_BOUNDARY.y));
            } while ((prefferedFieldChance >= 0 && prefferedFieldChance <= 80) != position.between(lowerLeft, upperRight));
        }
        else if (growthVariant == PlantsGrowthVariant.TOXIC_BODIES) {

        }
    }

    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    public void removeAnimal(Animal animal) {
        animalsList.remove(animal);
        animalsMap.get(animal.getPosition()).remove(animal);
    }

    public boolean animalsAreAbleToReproduce(Vector2d position) {
        if (animalsMap.get(position) == null || animalsMap.get(position).size() < 2)
            return false;

        Pair<Animal, Animal> parents = new Pair<>(animalsMap.get(position).pollFirst(), animalsMap.get(position).first());
        animalsMap.get(position).add(parents.getFirst());

        assert parents.getFirst() != null;
        return parents.getFirst().isStrong() && parents.getSecond().isStrong();
    }

    public Pair<Animal, Animal> getParents(Vector2d position) {
        return new Pair<>(animalsMap.get(position).pollFirst(), animalsMap.get(position).pollFirst());
    }

//    protected void reproduceAnimals() {
//        for (int x = UPPER_RIGHT_BOUNDARY.x; x <= UPPER_RIGHT_BOUNDARY.x; x++) {
//            for (int y = LOWER_LEFT_BOUNDARY.y; y <= UPPER_RIGHT_BOUNDARY.y; y++) {
//                Vector2d field = new Vector2d(x, y);
//                if (animalsMap.get(field) == null || animalsMap.get(field).size() < 2)
//                    continue;
//
//                Animal firstPotentialParent = animalsMap.get(field).pollFirst();
//                Animal secondPotentialParent = animalsMap.get(field).pollFirst();
//                if (!firstPotentialParent.isStrong() || !secondPotentialParent.isStrong())
//                    continue;
//
//                Animal child = firstPotentialParent.reproduce(secondPotentialParent);
//                child.setMap(this);
//                child.setObserver(this);
//            }
//        }
//    }

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
