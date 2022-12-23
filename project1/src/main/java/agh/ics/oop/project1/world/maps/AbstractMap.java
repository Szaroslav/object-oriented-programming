package agh.ics.oop.project1.world.maps;

import agh.ics.oop.project1.AbstractOrganism;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.plant.Plant;
import agh.ics.oop.project1.animal.IAnimalObserver;
import agh.ics.oop.project1.plant.PlantsGrowthVariant;
import agh.ics.oop.project1.utils.Pair;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.world.WorldConfig;
import agh.ics.oop.project1.utils.Vector2d;
import agh.ics.oop.project1.world.WorldConfigOptions;

import java.util.*;

public abstract class AbstractMap implements IAnimalObserver {
    protected final int WIDTH;
    protected final int HEIGHT;
    protected final Vector2d LOWER_LEFT_BOUNDARY;
    protected final Vector2d UPPER_RIGHT_BOUNDARY;
    protected final WorldConfig config;

    protected final List<Animal> animalsList = new ArrayList<>();
    protected final Map<Vector2d, TreeSet<Animal>> animalsMap = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final List<Pair<Vector2d, Integer>> deathsCounterList = new ArrayList<>();
    protected final PlantsGrowthVariant growthVariant;

    protected AbstractMap(WorldConfig config) {
        this.config = config;
        WIDTH = config.getInt(WorldConfigOptions.MAP_WIDTH.getName());
        HEIGHT = config.getInt(WorldConfigOptions.MAP_HEIGHT.getName());
        LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
        UPPER_RIGHT_BOUNDARY = new Vector2d(WIDTH - 1, HEIGHT - 1);
        growthVariant = PlantsGrowthVariant.fromString(config.getProperty(WorldConfigOptions.PLANTS_GROWTH_VARIANT.getName()));

        for (int y = LOWER_LEFT_BOUNDARY.y; y <= UPPER_RIGHT_BOUNDARY.y; y++)
            for (int x = LOWER_LEFT_BOUNDARY.x; x <= UPPER_RIGHT_BOUNDARY.x; x++)
                deathsCounterList.add(new Pair<>(new Vector2d(x, y), 0));
    }

    @Override
    public void animalTriedToMove(Animal animal) {}

    @Override
    public void animalPositionChanged(Vector2d oldPosition, Animal animal) {}

    @Override
    public void animalDied(Vector2d position) {
        for (Pair<Vector2d, Integer> pair : deathsCounterList) {
            if (pair.getFirst().equals(position)) {
                pair = new Pair<Vector2d, Integer>(position, pair.getSecond() + 1);
                return;
            }
        }
    }

    public void place(Animal animal) throws IllegalArgumentException {
        if (!isOrganismInBounds(animal))
            throw new IllegalArgumentException();

        animal.setMap(this);
        animal.setObserver(this);
        animalsList.add(animal);
        addAnimalToMap(animal);
    }

    public Plant plant() throws IllegalStateException {
        if (plants.size() >= WIDTH * HEIGHT)
            throw new IllegalStateException();

        int prefferedFieldChance;
        Vector2d position = new Vector2d(0, 0);

        if (growthVariant == PlantsGrowthVariant.FOREST_EQUATORS) {
            Pair<Vector2d, Vector2d> equatorBoundaries = getEquatorBoundaries();

            do {
                prefferedFieldChance = Random.range(0, 101);
                position = new Vector2d(Random.range(LOWER_LEFT_BOUNDARY.x, UPPER_RIGHT_BOUNDARY.x + 1), Random.range(LOWER_LEFT_BOUNDARY.y, UPPER_RIGHT_BOUNDARY.y + 1));
            } while (
                (prefferedFieldChance >= 0 && prefferedFieldChance <= 80)
                != position.between(equatorBoundaries.getFirst(), equatorBoundaries.getSecond())
                || plants.get(position) != null
            );
        }
        else if (growthVariant == PlantsGrowthVariant.TOXIC_BODIES) {
            deathsCounterList.sort(Comparator.comparingInt(Pair::getSecond));
            do {
                prefferedFieldChance = Random.range(0, 101);
                int i = prefferedFieldChance >= 0 && prefferedFieldChance <= 80
                        ? Random.range(0, (int) Math.ceil(.2 * deathsCounterList.size()))
                        : Random.range((int) Math.ceil(.2 * deathsCounterList.size()), deathsCounterList.size());
                position = deathsCounterList.get(i).getFirst();
            } while (plants.get(position) != null);
        }

        Plant plant = new Plant(position);
        plants.put(position, plant);
        return plant;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public List<Animal> getAnimalsList() {
        return animalsList;
    }

    public List<Plant> getPlantsList() {
        return new ArrayList<>(plants.values());
    }

    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    public void removeAnimal(Animal animal) {
        animalsList.remove(animal);
        animalsMap.get(animal.getPosition()).remove(animal);
    }

    public void updateAnimalsMap() {
        animalsMap.clear();
        for (Animal animal : animalsList)
            addAnimalToMap(animal);
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

    public Animal getStrongestAnimal(Vector2d position) {
        return animalsMap.get(position).first();
    }

    public boolean containsPlant(Vector2d position) {
        return plants.get(position) != null;
    }

    public void removePlant(Vector2d position) throws IllegalArgumentException {
        if (plants.remove(position) == null)
            throw new IllegalArgumentException();
    }


    protected boolean isOrganismInBounds(AbstractOrganism organism) {
        return organism.getPosition().between(LOWER_LEFT_BOUNDARY, UPPER_RIGHT_BOUNDARY);
    }

    private void addAnimalToMap(Animal animal) {
        animalsMap.computeIfAbsent(animal.getPosition(), k -> new TreeSet<>());
        animalsMap.get(animal.getPosition()).add(animal);
    }

    private Pair<Vector2d, Vector2d> getEquatorBoundaries() {
        int equatorHeight = (int) (HEIGHT * 0.2);
        equatorHeight += equatorHeight % 2 == 0 && HEIGHT % 2 == 1 || equatorHeight % 2 == 1 && HEIGHT % 2 == 0 ? 1 : 0;
        Vector2d lowerLeft = new Vector2d(LOWER_LEFT_BOUNDARY.x, LOWER_LEFT_BOUNDARY.y + (HEIGHT - equatorHeight) / 2 + 1);
        Vector2d upperRight = new Vector2d(UPPER_RIGHT_BOUNDARY.x, LOWER_LEFT_BOUNDARY.y + (HEIGHT - equatorHeight) / 2 + 1 + equatorHeight);

        return new Pair<>(lowerLeft, upperRight);
    }
}