package agh.ics.project1.animal;

public enum AnimalBehaviour {
    FULL_FATE("FULL_FATE") {
        @Override
        public int getActiveGen(int currentActiveGen, int genomeSize) {
            return (currentActiveGen + 1) % genomeSize;
        }
    },
    SLIGHT_MADNESS("SLIGHT_MADNESS") {
        @Override
        public int getActiveGen(int currentActiveGen, int genomeSize) {
            int madnessChance = (int) (Math.random() * 100);

            if (madnessChance >= 0 && madnessChance <= 80)
                return (currentActiveGen + 1) % genomeSize;

            int newActiveGen;
            do {
                newActiveGen = (int) (Math.random() * genomeSize);
            } while (newActiveGen == currentActiveGen);
            return newActiveGen;
        }
    };

    private String name;

    AnimalBehaviour(String name) {
        this.name = name;
    }

    public static AnimalBehaviour fromString(String name) {
        for (AnimalBehaviour behaviour : AnimalBehaviour.values())
            if (behaviour.name.equals(name))
                return behaviour;

        throw new IllegalArgumentException();
    }

    public int getActiveGen(int currentActiveGen, int genomeSize) {
        return 0;
    }
}
