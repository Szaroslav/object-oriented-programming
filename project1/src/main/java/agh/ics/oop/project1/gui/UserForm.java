package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.utils.ArrayUtils;
import agh.ics.oop.project1.world.engine.WorldEngineConfig;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.Arrays;

public class UserForm {
    GridPane grid;

    public UserForm() {
        grid = new GridPane();
//        Label label = new Label("Test");
//        ComboBox<String> combo = new ComboBox<>();
//        combo.getItems().add("");
//        combo.getItems().addAll(WorldEngineConfig.getConfigNames());
//        grid.add(label, 0, 0);
//        grid.add(combo, 1, 0);
        renderComboBox(0, "Simulation configuration", ArrayUtils.concat(new String[]{""}, WorldEngineConfig.getConfigNames()));
        renderVSpacer(1);
        renderInputRow(2, "Map width");
        renderInputRow(3, "Map height");
        renderComboBox(4, "Map type", new String[]{"Earth", "Infernal portal"});
        renderVSpacer(5);
        renderInputRow(6, "Initial number of animals");
        renderInputRow(7, "Initial energy of animals");
        renderComboBox(8, "Animals behaviour", new String[]{"Full fate", "Slight madness"});
        renderComboBox(9, "Animals mutation behaviour", new String[]{"Full random", "Slight adjustment"});
        renderInputRow(10, "Minimum energy of strong animal");
        renderInputRow(11, "Energy gained per eating");
        renderInputRow(12, "Minimum number of animal mutations");
        renderInputRow(13, "Maximum number of animal mutations");
        renderInputRow(14, "Genome size of animal");
        renderVSpacer(15);
        renderComboBox(16, "Variant of plants growth", new String[]{"Forest equators", "Toxic bodies"});
        renderInputRow(17, "Initial number of plants");
        renderInputRow(18, "Number of spawned plants per day");

        for (int i = 0; i < grid.getChildren().size(); i++) {
//            System.out.println(grid.getChildren().get(i));
        }
    }

    public GridPane getForm() {
        return grid;
    }

    private void renderInputRow(int row, String labelText) {
        grid.addRow(row, new Label(labelText), new TextField());
    }

    private void renderComboBox(int row, String labelText, String[] options) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(options);
        grid.addRow(row, new Label(labelText), combo);
    }

    private void renderVSpacer(int row) {
        Region spacer = new Region();
        spacer.setMinHeight(32);
        spacer.setPrefHeight(32);
        grid.add(spacer, 0, row, 2, 1);
    }
}
