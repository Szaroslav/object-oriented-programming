package agh.ics.oop.project1.world.engine;

import agh.ics.oop.project1.world.Vector2d;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.animal.AnimalBehaviour;
import agh.ics.oop.project1.animal.AnimalMutation;
import agh.ics.oop.project1.utils.Pair;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.world.config.WorldConfig;
import agh.ics.oop.project1.world.config.WorldConfigOptions;
import agh.ics.oop.project1.world.maps.AbstractMap;

import java.util.ArrayList;
import java.util.List;

public class WorldEngine extends Thread {
    private boolean isPaused;
    private int simulationDay = 0;
    private final List<Animal> animals = new ArrayList<>();
    private final AbstractMap map;
    private final WorldConfig config;
    private final IEngineObserver observer;

    public WorldEngine(AbstractMap map, WorldConfig config, IEngineObserver observer) {
        this.map = map;
        this.config = config;
        this.observer = observer;

        initAnimals();
        initPlants();
    }

    @Override
    public void run() {
        try {
            while (true) {
                harvestSouls();
                moveAnimals();
                map.updateAnimalsMap();
                letAnimalsEat();
                reproduceAnimals();
                plant(config.getInt(WorldConfigOptions.PLANTS_PER_DAY.getName()));
                onSimulationDayFinished();
            }
        }
        catch (InterruptedException ex) {
            System.out.println("World engine has been closed");
            System.out.println(ex);
        }
    }

    public int getSimulationDay() {
        return simulationDay;
    }

    public synchronized void pause() throws InterruptedException {
        isPaused = true;
    }

    public synchronized void unpause() {
        isPaused = false;
        notify();
    }

    private void initAnimals() {
        AnimalBehaviour behaviour = AnimalBehaviour.fromString(config.getProperty(WorldConfigOptions.ANIMAL_BEHAVIOUR.getName()));
        AnimalMutation mutation = AnimalMutation.fromString(config.getProperty(WorldConfigOptions.ANIMAL_MUTATION.getName()));

        for (int i = 0; i < config.getInt(WorldConfigOptions.INITIAL_ANIMALS_NUMBER.getName()); i++) {
            int[] genes = new int[config.getInt(WorldConfigOptions.ANIMAL_GENOME_SIZE.getName())];
            for (int j = 0; j < genes.length; j++)
                genes[j] = Random.range(0, 8);

            Animal animal = new Animal(
                new Vector2d(
                    Random.range(0, config.getInt(WorldConfigOptions.MAP_WIDTH.getName())),
                    Random.range(0, config.getInt(WorldConfigOptions.MAP_HEIGHT.getName()))
                ),
                config.getInt(WorldConfigOptions.INITIAL_ANIMALS_ENERGY.getName()),
                genes,
                behaviour,
                mutation,
                config
            );

            animals.add(animal);
            map.place(animal);
        }
    }

    private void initPlants() {
        plant(config.getInt(WorldConfigOptions.INITIAL_PLANTS_NUMBER.getName()));
    }

    private void harvestSouls() {
        List<Animal> animalsToHarvest = new ArrayList<>();
        for (Animal animal : animals)
            if (animal.isDead())
                animalsToHarvest.add(animal);
        for (Animal animal : animalsToHarvest) {
            animal.onDeath();
            animals.remove(animal);
            map.removeAnimal(animal);
        }
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            animal.move();
        }
    }

    private void letAnimalsEat() {
        for (Animal animal : animals) {
            if (!map.containsPlant(animal.getPosition()))
                continue;

            map.getStrongestAnimal(animal.getPosition()).eat();
            map.removePlant(animal.getPosition());
        }
    }

    private void reproduceAnimals() {
        for (int x = 0; x < config.getInt(WorldConfigOptions.MAP_WIDTH.getName()); x++) {
            for (int y = 0; y < config.getInt(WorldConfigOptions.MAP_HEIGHT.getName()); y++) {
                Vector2d field = new Vector2d(x, y);
                if (!map.animalsAreAbleToReproduce(field))
                    continue;

                Pair<Animal, Animal> parents = map.getParents(field);
                Animal child = parents.first().reproduce(parents.second());
                map.place(child);
                animals.add(child);
            }
        }
    }

    private void plant(final int n) {
        for (int i = 0; i < n; i++) {
            try {
                map.plant();
            }
            catch (IllegalStateException ex) {
                System.out.println("There are too many plants");
                return;
            }
        }
    }

    private void onSimulationDayFinished() throws InterruptedException {
        simulationDay++;
        for (Animal animal : animals)
            animal.growOld();
        map.updateStats();
        observer.simulationDayFinished();
        sleep(80);

        synchronized (this) {
            if (isPaused)
                wait();
        }
    }
}
