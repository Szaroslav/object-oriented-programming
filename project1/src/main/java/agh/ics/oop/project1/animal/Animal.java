package agh.ics.oop.project1.animal;

import agh.ics.oop.project1.utils.Vector2d;
import agh.ics.oop.project1.AbstractOrganism;
import agh.ics.oop.project1.Rotation;
import agh.ics.oop.project1.utils.ArrayUtils;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.world.engine.WorldEngineConfig;
import agh.ics.oop.project1.world.maps.AbstractMap;

import java.util.Arrays;

public class Animal extends AbstractOrganism implements Comparable<Animal> {
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

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !this.getClass().equals(other.getClass())) return false;

        Animal a = (Animal) other;
        return position.equals(a.position)
                && currentRotation == a.currentRotation
                && energy == a.energy
                && ageDays == a.ageDays
                && activeGen == a.activeGen
                && childrenNumber == a.childrenNumber
                && Arrays.equals(genes, a.genes);
    }

    @Override
    public int hashCode() {
        return position.hashCode() + energy + ageDays + activeGen + childrenNumber;
    }

    public void move() {
        activeGen = behaviour.getActiveGen(activeGen, genes.length);
        currentRotation = Rotation.rotate(currentRotation, genes[activeGen]);
        Vector2d newPosition = position.add(currentRotation.toVector());

        if (!map.canMoveTo(newPosition)) {
            observer.animalTriedToMove(this);
            return;
        }

        setPosition(newPosition);
        decreaseEnergy(1);
    }

    public void eat() {
        increaseEnergy(WorldEngineConfig.getInstance().getInt("ENERGY_PER_EATING"));
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

    @Override
    public void setPosition(Vector2d newPosition) {
        Vector2d oldPosition = position;
        position = newPosition;
        observer.animalPositionChanged(oldPosition, this);
    }

    public boolean isDead() {
        return energy <= 0;
    }

    public boolean isStrong() {
        return energy >= WorldEngineConfig.getInstance().getInt("STRONG_ANIMAL_MINIMUM_ENERGY");
    }

    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void decreaseEnergy(int energy) {
        this.energy -= energy;
        if (energy <= 0)
            observer.animalDied(position);
    }

    public void setMap(AbstractMap map) {
        this.map = map;
    }

    public void setObserver(IAnimalObserver observer) {
        this.observer = observer;
    }
}
