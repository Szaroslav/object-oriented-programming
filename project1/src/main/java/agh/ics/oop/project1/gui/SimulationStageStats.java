package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.maps.MapStats;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.Arrays;

public class SimulationStageStats {
    private final VBox content = new VBox();
    private Animal selectedAnimal;
    private WorldEngine engine;
    private final MapStats mapStats;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private final VBox animalStatsBox = new VBox();

    public SimulationStageStats(MapStats stats) {
        mapStats = stats;
        render();
    }

    public VBox getContent() {
        return content;
    }

    public void setEngine(WorldEngine engine) {
        this.engine = engine;
    }

    public void setSelectedAnimal(Animal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
        renderAnimalStats();
    }

    public void render() {
        Platform.runLater(() -> {
            content.getChildren().clear();
            Label title = new Label("Simulation statistics");
            title.getStyleClass().add("title");
            Label simSubtitle = new Label("Simulation");
            simSubtitle.getStyleClass().add("subtitle");

            renderAnimalStats();

            content.getChildren().addAll(
                title,
                simSubtitle,
                new Label("Simulation day " + engine.getSimulationDay()),
                new Label("Alive number of animals: " + mapStats.getAliveAnimalsNumber()),
                new Label("Number of plants: " + mapStats.getPlantsNumber()),
                new Label("Number of empty fields: " + mapStats.getEmptyFields()),
                new Label("Most common genes of alive animals: " + Arrays.toString(mapStats.getCommonGenes())),
                new Label("Average energy of alive animals: " + decimalFormat.format(mapStats.getAnimalsEnergyAvg())),
                new Label("Average age of dead animals: " + decimalFormat.format(mapStats.getDeadAnimalsAgeAvg()) + " days"),
                animalStatsBox
            );
        });
    }

    private void renderAnimalStats() {
        animalStatsBox.getChildren().clear();
        if (selectedAnimal == null)
            return;

        Label title = new Label("Selected animal");
        title.getStyleClass().add("subtitle");
        animalStatsBox.getChildren().addAll(
            title,
            new Label(
                !selectedAnimal.isDead()
                ? "Age: " + selectedAnimal.getAge() + " days"
                : "Animal is dead, it survived " + selectedAnimal.getDayOfDeath() + " days"
            ),
            new Label("Position: " + selectedAnimal.getPosition()),
            new Label("Energy: " + selectedAnimal.getEnergy()),
            new Label("Number of children: " + selectedAnimal.getChildrenNumber()),
            new Label("Number of overall eaten plants: " + selectedAnimal.getEatenPlantsNumber()),
            new Label("Genes: " + Arrays.toString(selectedAnimal.getGenes())),
            new Label("Currently active gen: " + selectedAnimal.getActiveGen())
        );
    }
}
