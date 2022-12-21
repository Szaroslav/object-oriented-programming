package agh.ics.oop.project1.plant;

import agh.ics.oop.project1.utils.Pair;
import agh.ics.oop.project1.utils.Random;
import agh.ics.oop.project1.utils.Vector2d;
import agh.ics.oop.project1.world.maps.AbstractMap;

import java.util.Comparator;

public enum PlantsGrowthVariant {
    FOREST_EQUATORS("FOREST_EQUATORS"),
    TOXIC_BODIES("TOXIC_BODIES");

    private String name;

    PlantsGrowthVariant(String name) {
        this.name = name;
    }

    public static PlantsGrowthVariant fromString(String name) throws IllegalArgumentException {
        for (PlantsGrowthVariant variant : PlantsGrowthVariant.values())
            if (variant.name.equals(name))
                return variant;

        throw new IllegalArgumentException();
    }
}
