package agh.ics.oop.project1.plant;

public enum PlantsGrowthVariant {
    FOREST_EQUATORS("FOREST_EQUATORS"),
    TOXIC_BODIES("TOXIC_BODIES");

    private String name;

    PlantsGrowthVariant(String name) {
        this.name = name;
    }

    public static PlantsGrowthVariant fromString(String name) {
        for (PlantsGrowthVariant variant : PlantsGrowthVariant.values())
            if (variant.name.equals(name))
                return variant;

        throw new IllegalArgumentException();
    }
}
