package agh.ics.oop.project1.world;

public enum WorldConfigOptions {
    MAP_WIDTH("MAP_WIDTH", "Map width"),
    MAP_HEIGHT("MAP_HEIGHT", "Map height"),
    MAP_TYPE("MAP_TYPE",  "Map type"),
    INITIAL_ANIMALS_NUMBER("INITIAL_ANIMALS_NUMBER",  "Initial number of animals"),
    INITIAL_ANIMALS_ENERGY("INITIAL_ANIMALS_ENERGY",  "Initial energy of animals"),
    ANIMAL_BEHAVIOUR("ANIMAL_BEHAVIOUR",  "Animals behaviour"),
    ANIMAL_MUTATION("ANIMAL_MUTATION",  "Animals mutation behaviour"),
    STRONG_ANIMAL_MINIMUM_ENERGY("STRONG_ANIMAL_MINIMUM_ENERGY",  "Minimum energy of strong animal"),
    ENERGY_PER_EATING("ENERGY_PER_EATING",  "Energy gained per eating"),
    ENERGY_PER_REPRODUCING("ENERGY_PER_REPRODUCING",  "Energy lost per reproducing"),
    MINIMUM_MUTATIONS_NUMBER("MINIMUM_MUTATIONS_NUMBER",  "Minimum number of animal mutations"),
    MAXIMUM_MUTATIONS_NUMBER("MAXIMUM_MUTATIONS_NUMBER",  "Maximum number of animal mutations"),
    ANIMAL_GENOME_SIZE("ANIMAL_GENOME_SIZE",  "Genome size of animal"),
    PLANTS_GROWTH_VARIANT("PLANTS_GROWTH_VARIANT",  "Variant of plants growth"),
    INITIAL_PLANTS_NUMBER("INITIAL_PLANTS_NUMBER",  "Initial number of plants"),
    PLANTS_PER_DAY("PLANTS_PER_DAY",  "Number of spawned plants per day");

    private final String name;
    private final String representativeText;

    WorldConfigOptions(String name, String representativeText) {
        this.name = name;
        this.representativeText = representativeText;
    }

    public String getName() {
        return name;
    }

    public String getRepresentativeText() {
        return representativeText;
    }
}
