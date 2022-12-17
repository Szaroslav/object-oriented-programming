package agh.ics.project1.animal;

import agh.ics.project1.Organism;
import agh.ics.project1.Rotation;

import java.util.Arrays;

public class Animal extends Organism {
    private int energy;
    private int[] genes;
    private int activeGen;
    private Rotation currentRotation;
    private AnimalBehaviour behaviour;
    private AnimalMutation mutation;

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

    public void move() {

    }

    public void reproduce() {

    }
}
