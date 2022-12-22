package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.world.WorldConfig;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static agh.ics.oop.project1.world.WorldConfigOptions.*;

public class UserForm {
    private boolean isFileConfigSelected;
    private String configName;
    private Properties configOptions;
    private final GridPane grid;
    private final List<ActionNode<TextField>> inputList = new ArrayList<>();
    private final List<ActionNode<ComboBox<String>>> comboBoxList = new ArrayList<>();
    private final Application application;

    public UserForm(Application application) {
        isFileConfigSelected = false;
        configName = null;
        configOptions = null;

        grid = new GridPane();
        this.application = application;

        renderConfigComboBox(0, "Simulation configuration");
        renderVSpacer(1);
        renderInputRow(2, MAP_WIDTH.getName(), MAP_WIDTH.getRepresentativeText());
        renderInputRow(3, MAP_HEIGHT.getName(), MAP_HEIGHT.getRepresentativeText());
        renderComboBox(4, MAP_TYPE.getName(),  MAP_TYPE.getRepresentativeText(), new String[]{"Earth", "Infernal portal"});
        renderVSpacer(5);
        renderInputRow(6, INITIAL_ANIMALS_NUMBER.getName(),  INITIAL_ANIMALS_NUMBER.getRepresentativeText());
        renderInputRow(7, INITIAL_ANIMALS_ENERGY.getName(),  INITIAL_ANIMALS_ENERGY.getRepresentativeText());
        renderComboBox(8, ANIMAL_BEHAVIOUR.getName(), ANIMAL_BEHAVIOUR.getRepresentativeText(), new String[]{"Full fate", "Slight madness"});
        renderComboBox(9, ANIMAL_MUTATION.getName(), ANIMAL_MUTATION.getRepresentativeText(), new String[]{"Full random", "Slight adjustment"});
        renderInputRow(10, STRONG_ANIMAL_MINIMUM_ENERGY.getName(), STRONG_ANIMAL_MINIMUM_ENERGY.getRepresentativeText());
        renderInputRow(11, ENERGY_PER_EATING.getName(), ENERGY_PER_EATING.getRepresentativeText());
        renderInputRow(12, ENERGY_PER_REPRODUCING.getName(), ENERGY_PER_REPRODUCING.getRepresentativeText());
        renderInputRow(13, MINIMUM_MUTATIONS_NUMBER.getName(), MINIMUM_MUTATIONS_NUMBER.getRepresentativeText());
        renderInputRow(14, MAXIMUM_MUTATIONS_NUMBER.getName(), MAXIMUM_MUTATIONS_NUMBER.getRepresentativeText());
        renderInputRow(15, ANIMAL_GENOME_SIZE.getName(), ANIMAL_GENOME_SIZE.getRepresentativeText());
        renderVSpacer(16);
        renderComboBox(17, PLANTS_GROWTH_VARIANT.getName(), PLANTS_GROWTH_VARIANT.getRepresentativeText(), new String[]{"Forest equators", "Toxic bodies"});
        renderInputRow(18, INITIAL_PLANTS_NUMBER.getName(), INITIAL_PLANTS_NUMBER.getRepresentativeText());
        renderInputRow(19, PLANTS_PER_DAY.getName(), PLANTS_PER_DAY.getRepresentativeText());
        renderVSpacer(20);
        renderButton(21, "Start new simulation");

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
                configOptions = new Properties();
                for (ActionNode<TextField> node : inputList)
                    configOptions.setProperty(node.name(), node.node().getText());
                for (ActionNode<ComboBox<String>> node : comboBoxList)
                    configOptions.setProperty(
                        node.name(),
                        node.node().getSelectionModel().getSelectedItem().toUpperCase().replace(' ', '_')
                    );
                System.out.println(configOptions);
                application.startSimulation(configOptions);
            }
        });
        grid.add(button, 0, row);
    }

    private void renderInputRow(int row, String name, String labelText) {
        TextField input = new TextField();
        inputList.add(new ActionNode<>(input, name));
        grid.addRow(row, new Label(labelText), input);
    }

    private void renderComboBox(int row, String name, String labelText, String[] options) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(options);
        comboBoxList.add(new ActionNode<>(combo, name));
        grid.addRow(row, new Label(labelText), combo);
    }

    private void renderConfigComboBox(int row, String labelText) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().add("");
        combo.getItems().addAll(WorldConfig.getConfigNames());
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
        for (ActionNode<TextField> node : inputList)
            node.node().setDisable(value);
        for (ActionNode<ComboBox<String>> node : comboBoxList)
            node.node().setDisable(value);
    }
}
