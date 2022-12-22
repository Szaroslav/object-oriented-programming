package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.utils.ArrayUtils;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.engine.WorldEngineConfig;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserForm {
    private boolean isFileConfigSelected;
    private String configName;
    private Properties configOptions;
    private final GridPane grid;
    private final List<Node> actionNodesList = new ArrayList<>();
    private final Application application;

    public UserForm(Application application) {
        isFileConfigSelected = false;
        configName = null;
        configOptions = null;

        grid = new GridPane();
        this.application = application;

        renderConfigComboBox(0, "Simulation configuration");
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
        renderVSpacer(19);
        renderButton(20, "Start new simulation");

        for (int i = 0; i < grid.getChildren().size(); i++) {
//            System.out.println(grid.getChildren().get(i));
        }
    }

    public GridPane getForm() {
        return grid;
    }

    private void renderButton(int row, String buttonText) {
        Button button = new Button(buttonText);
        button.setOnAction(event -> {
            if (isFileConfigSelected) {
                application.startSimulation(configName);
            } else {
                application.startSimulation(configOptions);
            }
        });
        grid.add(button, 0, row);
    }

    private void renderInputRow(int row, String labelText) {
        TextField input = new TextField();
        actionNodesList.add(input);
        grid.addRow(row, new Label(labelText), input);
    }

    private void renderComboBox(int row, String labelText, String[] options) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(options);
        actionNodesList.add(combo);
        grid.addRow(row, new Label(labelText), combo);
    }

    private void renderConfigComboBox(int row, String labelText) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().add("");
        combo.getItems().addAll(WorldEngineConfig.getConfigNames());
        combo.setOnAction(event -> {
            configName = combo.getSelectionModel().getSelectedItem();
            isFileConfigSelected = !configName.equals("");
            setFormDisabledStatus(isFileConfigSelected);
        });
        grid.addRow(row, new Label(labelText), combo);
    }

    private void renderVSpacer(int row) {
        Region spacer = new Region();
        spacer.setMinHeight(32);
        spacer.setPrefHeight(32);
        grid.add(spacer, 0, row, 2, 1);
    }

    private void setFormDisabledStatus(boolean value) {
        for (Node node : actionNodesList)
            node.setDisable(value);
    }
}
