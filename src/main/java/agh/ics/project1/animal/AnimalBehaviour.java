package agh.ics.project1.animal;

public enum AnimalBehaviour {
    FULL_FATE {
        @Override
        public int getActiveGen(int currentActiveGen, int genomeSize) {
            return (currentActiveGen + 1) % genomeSize;
        }
    },
    SLIGHT_MADNESS {
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

    public int getActiveGen(int currentActiveGen, int genomeSize) {
        return 0;
    }
}
