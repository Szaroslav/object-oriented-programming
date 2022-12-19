package agh.ics.oop.project1.world.engine;

import agh.ics.oop.project1.plant.Plant;
import agh.ics.oop.project1.utils.Vector2d;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.animal.AnimalBehaviour;
import agh.ics.oop.project1.animal.AnimalMutation;
import agh.ics.oop.project1.utils.Pair;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.world.maps.AbstractMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldEngine implements Runnable {
    private final List<Animal> animals = new ArrayList<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private AbstractMap map;

    public WorldEngine(AbstractMap map) {
        this.map = map;

        initAnimals();
        initPlants();
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            System.out.println("xd" + i);
            harvestSouls();
            moveAnimals();
            map.updateAnimalsMap();
            letAnimalsEat();
            reproduceAnimals();
            plant();
        }
        System.out.println("xd");
    }

    private void initAnimals() {
        WorldEngineConfig config = WorldEngineConfig.getInstance();
        AnimalBehaviour behaviour = AnimalBehaviour.fromString(config.getProperty("ANIMAL_BEHAVIOUR"));
        AnimalMutation mutation = AnimalMutation.fromString(config.getProperty("ANIMAL_MUTATION"));

        for (int i = 0; i < config.getInt("INITIAL_ANIMALS_NUMBER"); i++) {
            int[] genes = new int[config.getInt("GENOME_SIZE")];
            for (int j = 0; j < genes.length; j++)
                genes[j] = Random.range(0, 8);

            Animal animal = new Animal(
                new Vector2d(Random.range(0, config.getInt("MAP_WIDTH")), Random.range(0, config.getInt("MAP_HEIGHT"))),
                config.getInt("INITIAL_ANIMALS_ENERGY"),
                genes,
                behaviour,
                mutation
            );

            animals.add(animal);
            map.place(animal);
        }
    }

    private void initPlants() {
        for (int i = 0; i < WorldEngineConfig.getInstance().getInt("INITIAL_PLANTS_NUMBER"); i++)
            map.plant();
    }

    private void harvestSouls() {
        List<Animal> animalsToRemove = new ArrayList<>();
        for (Animal animal : animals)
            if (animal.isDead())
                animalsToRemove.add(animal);
        for (Animal animal : animalsToRemove) {
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
            if (plants.get(animal.getPosition()) == null)
                continue;

            map.getStrongestAnimal(animal.getPosition()).eat();
            map.removePlant(plants.remove(animal.getPosition()));
        }
    }

    private void reproduceAnimals() {
        for (int x = 0; x < WorldEngineConfig.getInstance().getInt("MAP_WIDTH"); x++) {
            for (int y = 0; y < WorldEngineConfig.getInstance().getInt("MAP_HEIGHT"); y++) {
                Vector2d field = new Vector2d(x, y);
                if (!map.animalsAreAbleToReproduce(field))
                    continue;

                Pair<Animal, Animal> parents = map.getParents(field);
                Animal child = parents.getFirst().reproduce(parents.getSecond());
                map.place(child);
            }
        }
    }

    private void plant() {
        for (int i = 0; i < WorldEngineConfig.getInstance().getInt("PLANTS_PER_DAY"); i++) {
            try {
                Plant plant = map.plant();
                plants.put(plant.getPosition(), plant);
            }
            catch (IllegalStateException ex) {

            }
        }

    }
}
