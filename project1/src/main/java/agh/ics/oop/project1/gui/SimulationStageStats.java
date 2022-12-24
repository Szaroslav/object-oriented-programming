package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.world.maps.AbstractMap;
import agh.ics.oop.project1.world.maps.MapStats;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.Arrays;

public class SimulationStageStats {
    private final VBox content = new VBox();
    private MapStats mapStats;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public SimulationStageStats(MapStats stats) {
        mapStats = stats;
        render();
    }

    public VBox getContent() {
        return content;
    }

    public void render() {
        Platform.runLater(() -> {
            content.getChildren().clear();
            content.getChildren().addAll(
                new Label("Alive number of animals: " + mapStats.getAliveAnimalsNumber()),
                new Label("Number of plants: " + mapStats.getPlantsNumber()),
                new Label("Number of empty fields: " + mapStats.getEmptyFields()),
                new Label("Most common genes of alive animals: " + Arrays.toString(mapStats.getCommonGenes())),
                new Label("Average energy of alive animals: " + decimalFormat.format(mapStats.getAnimalsEnergyAvg())),
                new Label("Average age in days of dead animals: " + decimalFormat.format(mapStats.getDeadAnimalsAgeAvg()))
            );
        });
    }
}
