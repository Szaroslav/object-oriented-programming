package agh.ics.project1.animal;

import agh.ics.project1.utils.Random;
import agh.ics.project1.world.engine.WorldEngineConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public enum AnimalMutation {
    FULL_RANDOM("FULL_RANDOM") {
        @Override
        public void mutate(int[] genes) {
            List<Integer> range = getShuffledIndexesList(genes.length);

            for (int i : range) {
                int newGen;
                do {
                    newGen = Random.range(0, 8);
                } while (newGen == genes[i]);
                genes[i] = newGen;
            }
        }
    },
    SLIGHT_ADJUSTMENT("SLIGHT_ADJUSTMENT") {
        @Override
        public void mutate(int[] genes) {
            List<Integer> range = getShuffledIndexesList(genes.length);

            for (int i : range) {
                genes[i] = Random.range(0, 2) == 0
                            ? genes[i] - 1 > 0 ? genes[i] - 1 : 7
                            : genes[i] + 1 < 8 ? genes[i] + 1 : 0;
            }
        }
    };

    private String name;

    AnimalMutation(String name) {
        this.name = name;
    }

    public static AnimalMutation fromString(String name) {
        for (AnimalMutation mutation : AnimalMutation.values())
            if (mutation.name.equals(name))
                return mutation;

        throw new IllegalArgumentException();
    }

    public void mutate(int[] genes) {}

    private static List<Integer> getShuffledIndexesList(int genomeSize) {
        List<Integer> range = new ArrayList<>(IntStream.range(0, genomeSize).boxed().toList());
        Collections.shuffle(range);
        range = range.subList(0, Random.range(
            WorldEngineConfig.getInstance().getInt("MINIMUM_MUTATIONS_NUMBER"),
            WorldEngineConfig.getInstance().getInt("MAXIMUM_MUTATIONS_NUMBER") + 1
        ));

        return range;
    }
}
