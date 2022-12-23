package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.world.WorldConfig;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static agh.ics.oop.project1.world.WorldConfigOptions.*;

public class UserForm {
    private final int INPUT_WIDTH = 48;
    private final int COMBO_BOX_WIDTH = 128;
    private final String ICONS_DIRECTORY = "src/main/resources/icons";
    private boolean isFileConfigSelected;
    private String configName;
    private Properties configOptions;

    private final Image tickIcon;
    private final Image crossIcon;
    private final GridPane grid;
    private final List<ActionNode<TextField>> inputList = new ArrayList<>();
    private final List<ActionNode<ComboBox<String>>> comboBoxList = new ArrayList<>();
    private final Application application;

    public UserForm(Application application) throws FileNotFoundException {
        isFileConfigSelected = false;
        configName = null;
        configOptions = null;

        tickIcon = new Image(new FileInputStream(ICONS_DIRECTORY + "/tick.png"));
        crossIcon = new Image(new FileInputStream(ICONS_DIRECTORY + "/cross.png"));
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

    private <T extends Region> void renderIcon(Image icon, List<ActionNode<T>> nodesList, int index, String message) {
        ActionNode<T> actionNode = nodesList.get(index);
        if (actionNode.pane().getChildren().size() > 1)
            actionNode.pane().getChildren().remove(1);

        ImageView imageView = new ImageView(icon);
        imageView.relocate(actionNode.region().getWidth(), -6);
        actionNode.pane().getChildren().add(imageView);

        if (message != null) {
            Tooltip tooltip = new Tooltip(message);
            tooltip.setShowDelay(Duration.seconds(.5));
            Tooltip.install(actionNode.pane(), tooltip);
            nodesList.set(index, new ActionNode<>(actionNode.region(), actionNode.pane(), tooltip, actionNode.name()));
        }
        else if (actionNode.tooltip() != null) {
            Tooltip.uninstall(actionNode.pane(), actionNode.tooltip());
            nodesList.set(index, new ActionNode<>(actionNode.region(), actionNode.pane(), actionNode.name()));
        }
    }

    private void renderButton(int row, String buttonText) {
        Button button = new Button(buttonText);
        button.setOnAction(this::onSubmit);
        grid.add(button, 0, row);
    }

    private void renderInputRow(int row, String name, String labelText) {
        Pane pane = new Pane();
        TextField input = new TextField();
        input.setPrefWidth(INPUT_WIDTH);
        input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("^([1-9][0-9]*)") || change.getControlNewText().equals(""))
                return change;
            return null;
        }));
        pane.getChildren().add(input);

        inputList.add(new ActionNode<>(input, pane, name));
        grid.addRow(row, new Label(labelText), pane);
    }

    private void renderComboBox(int row, String name, String labelText, String[] options) {
        Pane pane = new Pane();
        ComboBox<String> combo = new ComboBox<>();
        combo.setPrefWidth(COMBO_BOX_WIDTH);
        combo.getItems().addAll(options);
        pane.getChildren().add(combo);

        comboBoxList.add(new ActionNode<>(combo, pane, name));
        grid.addRow(row, new Label(labelText), pane);
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
            node.region().setDisable(value);
        for (ActionNode<ComboBox<String>> node : comboBoxList)
            node.region().setDisable(value);
    }

    private boolean validate() {
        boolean isValid = true;

        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).region().getText().equals("")) {
                renderIcon(crossIcon, inputList, i, "This field is required (positive integer)");
                isValid = false;
            }
            else {
                renderIcon(tickIcon, inputList, i, null);
            }
        }

        for (int i = 0; i < comboBoxList.size(); i++) {
            if (comboBoxList.get(i).region().getSelectionModel().getSelectedItem() == null) {
                renderIcon(crossIcon, comboBoxList, i, "This field is required");
                isValid = false;
            }
            else {
                renderIcon(tickIcon, comboBoxList, i, null);
            }
        }

        return isValid;
    }

    private void onSubmit(ActionEvent event) {
        if (isFileConfigSelected) {
            application.startSimulation(configName);
        } else {
            if (!validate())
                return;

            configOptions = new Properties();
            for (ActionNode<TextField> node : inputList)
                configOptions.setProperty(node.name(), node.region().getText());
            for (ActionNode<ComboBox<String>> node : comboBoxList)
                configOptions.setProperty(
                        node.name(),
                        node.region().getSelectionModel().getSelectedItem().toUpperCase().replace(' ', '_')
                );

            System.out.println(configOptions);
            application.startSimulation(configOptions);
        }
    }
}
