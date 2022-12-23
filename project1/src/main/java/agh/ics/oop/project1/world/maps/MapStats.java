package agh.ics.oop.project1.world.maps;

public class MapStats {
    private int aliveAnimalsNumber = 0;
    private int plantsNumber = 0;
    private int deadAnimalsNumber = 0;
    private int deadAnimalsSumAge = 0;
    private int emptyFields = 0;
    private int[] commonGenes;
    private int animalsEnergySum;

    public int getAliveAnimalsNumber() {
        return aliveAnimalsNumber;
    }

    public void setAliveAnimalsNumber(int aliveAnimalsNumber) {
        this.aliveAnimalsNumber = aliveAnimalsNumber;
    }

    public int getPlantsNumber() {
        return plantsNumber;
    }

    public void setPlantsNumber(int plantsNumber) {
        this.plantsNumber = plantsNumber;
    }

    public int getDeadAnimalsSumAge() {
        return deadAnimalsSumAge;
    }

    public void setDeadAnimalsSumAge(int deadAnimalsSumAge) {
        this.deadAnimalsSumAge = deadAnimalsSumAge;
    }

    public int getDeadAnimalsNumber() {
        return deadAnimalsNumber;
    }

    public void setDeadAnimalsNumber(int deadAnimalsNumber) {
        this.deadAnimalsNumber = deadAnimalsNumber;
    }

    public int getEmptyFields() {
        return emptyFields;
    }

    public void setEmptyFields(int emptyFields) {
        this.emptyFields = emptyFields;
    }

    public int[] getCommonGenes() {
        return commonGenes;
    }

    public void setCommonGenes(int[] commonGenes) {
        this.commonGenes = commonGenes;
    }

    public int getAnimalsEnergySum() {
        return animalsEnergySum;
    }

    public void setAnimalsEnergySum(int animalsEnergySum) {
        this.animalsEnergySum = animalsEnergySum;
    }

    public double getAnimalsEnergyAvg() {
        return aliveAnimalsNumber > 0 ? (double) animalsEnergySum / aliveAnimalsNumber : 0;
    }

    public double getDeadAnimalsAgeAvg() {
        return deadAnimalsNumber > 0 ? (double) deadAnimalsSumAge / deadAnimalsNumber : 0;
    }
}
