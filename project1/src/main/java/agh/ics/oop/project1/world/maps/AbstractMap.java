package agh.ics.oop.project1.world.maps;

import agh.ics.oop.project1.world.AbstractOrganism;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.plant.Plant;
import agh.ics.oop.project1.animal.IAnimalObserver;
import agh.ics.oop.project1.plant.PlantsGrowthVariant;
import agh.ics.oop.project1.utils.IntPair;
import agh.ics.oop.project1.utils.Pair;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.world.config.WorldConfig;
import agh.ics.oop.project1.world.Vector2d;
import agh.ics.oop.project1.world.config.WorldConfigOptions;

import java.util.*;

public abstract class AbstractMap implements IAnimalObserver {
    protected final int WIDTH;
    protected final int HEIGHT;
    protected final Vector2d LOWER_LEFT_BOUNDARY;
    protected final Vector2d UPPER_RIGHT_BOUNDARY;
    protected final MapStats stats = new MapStats();
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
        for (int i = 0; i < deathsCounterList.size(); i++) {
            if (deathsCounterList.get(i).first().equals(position)) {
                deathsCounterList.set(i, new Pair<>(position, deathsCounterList.get(i).second() + 1));
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
                != position.between(equatorBoundaries.first(), equatorBoundaries.second())
                || plants.get(position) != null
            );
        }
        else if (growthVariant == PlantsGrowthVariant.TOXIC_BODIES) {
            deathsCounterList.sort(Comparator.comparingInt(Pair::second));
            do {
                prefferedFieldChance = Random.range(0, 101);
                int i = prefferedFieldChance >= 0 && prefferedFieldChance <= 80
                        ? Random.range(0, (int) Math.ceil(.2 * deathsCounterList.size()))
                        : Random.range((int) Math.ceil(.2 * deathsCounterList.size()), deathsCounterList.size());
                position = deathsCounterList.get(i).first();
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
        stats.setDeadAnimalsNumber(stats.getDeadAnimalsNumber() + 1);
        stats.setDeadAnimalsSumAge(stats.getDeadAnimalsSumAge() + animal.getAge());
    }

    public void updateAnimalsMap() {
        animalsMap.clear();
        for (Animal animal : animalsList)
            addAnimalToMap(animal);
    }

    public MapStats getStats() {
        return stats;
    }

    public void updateStats() {
        stats.setAliveAnimalsNumber(animalsList.size());
        stats.setPlantsNumber(plants.size());
        stats.setEmptyFields(0);
        stats.setAnimalsEnergySum(0);

        List<IntPair> genesCounts = new ArrayList<>();
        for (Animal animal : animalsList) {
            stats.setAnimalsEnergySum(stats.getAnimalsEnergySum() + animal.getEnergy());

            boolean found = false;
            for (int i = 0; i < genesCounts.size(); i++) {
                if (Arrays.equals(genesCounts.get(i).first(), animal.getGenes())) {
                    genesCounts.set(i, new IntPair(genesCounts.get(i).first(), genesCounts.get(i).second()));
                    break;
                }
            }
            if (!found)
                genesCounts.add(new IntPair(animal.getGenes(), 1));
        }
        genesCounts.sort(Comparator.comparingInt(IntPair::second));
        stats.setCommonGenes(genesCounts.size() > 0 ? genesCounts.get(0).first() : null);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Vector2d pos = new Vector2d(x, y);
                if (animalsMap.containsKey(pos) || plants.containsKey(pos))
                    stats.setEmptyFields(stats.getEmptyFields() + 1);
            }
        }
    }

    public boolean animalsAreAbleToReproduce(Vector2d position) {
        if (animalsMap.get(position) == null || animalsMap.get(position).size() < 2)
            return false;

        Pair<Animal, Animal> parents = new Pair<>(animalsMap.get(position).pollFirst(), animalsMap.get(position).first());
        animalsMap.get(position).add(parents.first());

        assert parents.first() != null;
        return parents.first().isStrong() && parents.second().isStrong();
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