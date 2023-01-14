package agh.ics.oop.project1.animal;

import agh.ics.oop.project1.world.Vector2d;
import agh.ics.oop.project1.world.AbstractOrganism;
import agh.ics.oop.project1.world.Rotation;
import agh.ics.oop.project1.utils.ArrayUtils;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.world.config.WorldConfig;
import agh.ics.oop.project1.world.config.WorldConfigOptions;
import agh.ics.oop.project1.world.maps.AbstractMap;

import java.util.Arrays;

public class Animal extends AbstractOrganism implements Comparable<Animal> {
    private int energy;
    private int ageDays;
    private final int[] genes; // przydałaby się klasa na genom
    private int activeGen;
    private int childrenNumber;
    private int eatenPlantsNumber;
    private int dayOfDeath = 0;
    private Rotation currentRotation;
    private final AnimalBehaviour behaviour;
    private final AnimalMutation mutation;
    private AbstractMap map;
    private final WorldConfig config;
    private IAnimalObserver observer;

    public Animal(
            Vector2d position,
            int energy,
            int[] genes,
            AnimalBehaviour behaviour,
            AnimalMutation mutation,
            WorldConfig config
    ) {
        this.config = config;

        this.position = position;
        this.energy = energy;
        this.ageDays = 0;
        this.eatenPlantsNumber = 0;
        this.genes = Arrays.copyOf(genes, genes.length);
        this.behaviour = behaviour;
        this.mutation = mutation;

        this.mutation.mutate(
                this.genes,
                config.getInt(WorldConfigOptions.MINIMUM_MUTATIONS_NUMBER.getName()),
                config.getInt(WorldConfigOptions.MAXIMUM_MUTATIONS_NUMBER.getName())
        );
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

    @Override
    public String toString() {
        return "Animal " + position;
    }

    @Override
    public void setPosition(Vector2d newPosition) { // czy upublicznienie tej metody jest najlepszym rozwiązaniem?
        Vector2d oldPosition = position;
        position = newPosition;
        observer.animalPositionChanged(oldPosition, this);
    }

    public int getAge() {
        return ageDays;
    }

    public void growOld() {
        ageDays++;
    }

    public void onDeath() {
        dayOfDeath = ageDays;
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public int getEatenPlantsNumber() {
        return eatenPlantsNumber;
    }

    public int[] getGenes() {
        return genes;
    }

    public int getActiveGen() {
        return activeGen;
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
        increaseEnergy(config.getInt("ENERGY_PER_EATING"));
        eatenPlantsNumber++;
    }

    public Animal reproduce(Animal lover) {
        int[] childGenes = ArrayUtils.concatVar(genes, lover.genes, (double) energy / (energy + lover.energy), Random.range(0, 2) == 0);
        Animal child = new Animal(
                this.position,
                2 * config.getInt("ENERGY_PER_REPRODUCING"),
                childGenes,
                behaviour,
                mutation,
                config
        );

        decreaseEnergy(config.getInt("ENERGY_PER_REPRODUCING"));
        childrenNumber++;
        lover.decreaseEnergy(config.getInt("ENERGY_PER_REPRODUCING"));
        lover.childrenNumber++;

        return child;
    }

    public void invert() {
        currentRotation = Rotation.invert(currentRotation);
    }

    public boolean isDead() {
        return energy <= 0;
    }

    public boolean isStrong() {
        return energy >= config.getInt("STRONG_ANIMAL_MINIMUM_ENERGY");
    }

    public int getEnergy() {
        return energy;
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