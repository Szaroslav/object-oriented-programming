package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.utils.ArrayUtils;
import agh.ics.oop.project1.utils.WriterCSV;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.maps.MapStats;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.IntStream;

public class SimulationStageStats {
    private final boolean saveToCSV;
    private final VBox content = new VBox();
    private Animal selectedAnimal;
    private WorldEngine engine;
    private final MapStats mapStats;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private final VBox animalStatsBox = new VBox();
    private final File outputFile;

    public SimulationStageStats(MapStats stats, int index, int genomeSize, boolean saveToCSV) {
        this.saveToCSV = saveToCSV;
        mapStats = stats;
        render();

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
        String currentDate = format.format(new Date());
        outputFile = new File("src/main/resources/logs/simulation" + index + "-" + currentDate + ".csv");

        if (!saveToCSV)
            return;

        try {
            WriterCSV.writeln(outputFile, getSimulationHeader(genomeSize));
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
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

    public void saveToCSV() {
        if (!saveToCSV)
            return;

        try {
            String genesStr = Arrays.toString(mapStats.getCommonGenes());
            WriterCSV.writeln(outputFile, new String[]{
                Integer.toString(engine.getSimulationDay()),
                Integer.toString(mapStats.getAliveAnimalsNumber()),
                Integer.toString(mapStats.getPlantsNumber()),
                Integer.toString(mapStats.getEmptyFields()),
                genesStr.substring(1, genesStr.length() - 1),
                Double.toString(mapStats.getAnimalsEnergyAvg()),
                Double.toString(mapStats.getDeadAnimalsAgeAvg())
            });
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public String[] getSimulationHeader(int genomeSize) {
        String[] header1 = {
            "Simulation day",
            "Alive number of animals",
            "Number of plants",
            "Number of empty fields"
        };
        String[] header2 = (String[]) IntStream.range(1, genomeSize + 1).mapToObj(v -> "Gen " + v).toArray(String[]::new);
        String[] header3 = {
            "Average energy of alive animals",
            "Average age of dead animals"
        };

        return ArrayUtils.concat(ArrayUtils.concat(header1, header2), header3);
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
