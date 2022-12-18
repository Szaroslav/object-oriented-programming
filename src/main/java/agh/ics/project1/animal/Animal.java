package agh.ics.project1.animal;

import agh.ics.oop.Vector2d;
import agh.ics.project1.Organism;
import agh.ics.project1.Rotation;
import agh.ics.project1.utils.ArrayUtils;
import agh.ics.project1.utils.Random;
import agh.ics.project1.world.engine.WorldEngineConfig;
import agh.ics.project1.world.maps.AbstractMap;

import java.util.Arrays;
import java.util.Comparator;

public class Animal extends Organism implements Comparable<Animal> {
    private int energy;
    private int[] genes;
    private int activeGen;
    private int childrenNumber;
    private Rotation currentRotation;
    private final AnimalBehaviour behaviour;
    private final AnimalMutation mutation;
    private AbstractMap map;
    private IAnimalObserver observer;

    public Animal(
        Vector2d position,
        int energy,
        int[] genes,
        AnimalBehaviour behaviour,
        AnimalMutation mutation
    ) {
        this.position = position;
        this.energy = energy;
        this.genes = Arrays.copyOf(genes, genes.length);
        this.behaviour = behaviour;
        this.mutation = mutation;

        this.mutation.mutate(this.genes);
        this.activeGen = Random.range(0, genes.length);
        this.currentRotation = Rotation.fromInt(Random.range(0, 8));
        this.childrenNumber = 0;
    }

    @Override
    public int compareTo(Animal other) {
        if (this.energy - other.energy != 0)
            return this.energy - other.energy;
        if (this.ageDays - other.ageDays != 0)
            return this.ageDays - other.ageDays;
        if (this.childrenNumber - other.childrenNumber != 0)
            return childrenNumber - other.childrenNumber;
        return (int) (Math.random() * 3 - 1);
    }

    public void move(Vector2d newPosition) {
        if (!map.canMoveTo(newPosition)) {
            observer.animalTriedToMove(this);
            return;
        }

        activeGen = behaviour.getActiveGen(activeGen, genes.length);
        currentRotation = Rotation.rotate(currentRotation, genes[activeGen]);
        setPosition(newPosition);
    }

    public Animal reproduce(Animal lover) {
        int[] childGenes = ArrayUtils.concatVar(genes, lover.genes, (double) energy / (energy + lover.energy), Random.range(0, 2) == 0);
        Animal child = new Animal(
                this.position,
            2 * WorldEngineConfig.getInstance().getInt("ENERGY_PER_REPRODUCING"),
                childGenes,
                behaviour,
                mutation
        );

        decreaseEnergy(WorldEngineConfig.getInstance().getInt("ENERGY_PER_REPRODUCING"));
        childrenNumber++;
        lover.decreaseEnergy(WorldEngineConfig.getInstance().getInt("ENERGY_PER_REPRODUCING"));
        lover.childrenNumber++;

        return child;
    }

    public void invert() {
        currentRotation = Rotation.invert(currentRotation);
    }

    public void setPosition(Vector2d newPosition) {
        Vector2d oldPosition = position;
        position = newPosition;
        observer.animalPositionChanged(oldPosition, this);
    }

    public boolean isStrong() {
        return energy >= WorldEngineConfig.getInstance().getInt("STRONG_ANIMAL_MINIMUM_ENERGY");
    }

    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void decreaseEnergy(int energy) {
        this.energy -= energy;
    }

    public void setMap(AbstractMap map) {
        this.map = map;
    }

    public void setObserver(IAnimalObserver observer) {
        this.observer = observer;
    }
}
