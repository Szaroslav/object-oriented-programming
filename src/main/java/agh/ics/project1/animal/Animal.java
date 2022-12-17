package agh.ics.project1.animal;

import agh.ics.oop.Vector2d;
import agh.ics.project1.Organism;
import agh.ics.project1.Rotation;
import agh.ics.project1.world.maps.AbstractMap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

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
        int energy,
        int[] genes,
        AnimalBehaviour behaviour,
        AnimalMutation mutation
    ) {
        this.energy = energy;
        this.genes = Arrays.copyOf(genes, genes.length);
        this.behaviour = behaviour;
        this.mutation = mutation;
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
        Vector2d oldPosition = position;
        position = position.add(currentRotation.toVector());
        observer.animalPositionChanged(oldPosition, this);
    }

    public void reproduce() {

    }

    public void setMap(AbstractMap map) {
        this.map = map;
    }

    public void setObserver(IAnimalObserver observer) {
        this.observer = observer;
    }

    private void behave() {

    }
}
