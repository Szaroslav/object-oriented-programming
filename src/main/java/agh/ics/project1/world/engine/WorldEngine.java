package agh.ics.project1.world.engine;

import agh.ics.oop.Vector2d;
import agh.ics.project1.animal.Animal;
import agh.ics.project1.animal.AnimalBehaviour;
import agh.ics.project1.animal.AnimalMutation;
import agh.ics.project1.utils.Random;
import agh.ics.project1.world.maps.AbstractMap;

import java.util.ArrayList;
import java.util.List;

public class WorldEngine implements Runnable {
    private final List<Animal> animals = new ArrayList<>();
    private AbstractMap map;

    public WorldEngine(AbstractMap map) {
        this.map = map;

        initAnimals();
    }

    @Override
    public void run() {

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
}
