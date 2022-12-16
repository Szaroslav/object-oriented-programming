package agh.ics.project1.animal;

import agh.ics.project1.Organism;
import agh.ics.project1.Rotation;

import java.util.Arrays;

public class Animal extends Organism {
    private int energy;
    private int[] genes;
    private int activeGen;
    private Rotation currentRotation;
    private AnimalMutationBehaviour mutationBehaviour;
    private AnimalMovingBehaviour movingBehaviour;

    public Animal(
        int energy,
        int[] genes,
        AnimalMutationBehaviour mutationBehaviour,
        AnimalMovingBehaviour movingBehaviour
    ) {
        this.energy = energy;
        this.genes = Arrays.copyOf(genes, genes.length);
        this.mutationBehaviour = mutationBehaviour;
        this.movingBehaviour = movingBehaviour;
    }

    public void move() {

    }

    public void reproduce() {

    }
}
