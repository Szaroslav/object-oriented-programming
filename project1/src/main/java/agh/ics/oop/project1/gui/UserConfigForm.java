package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.utils.Pair;
import agh.ics.oop.project1.world.config.WorldConfig;
import agh.ics.oop.project1.world.config.WorldConfigOptions;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static agh.ics.oop.project1.world.config.WorldConfigOptions.*;

public class UserConfigForm {
    private final int INPUT_WIDTH = 48;
    private final int COMBO_BOX_WIDTH = 128;
    private final String ICONS_DIRECTORY = "src/main/resources/icons";
    private int currentRowIndex = 0;
    private boolean isFileConfigSelected;
    private String configName;
    private Properties configOptions;

    private final Image tickIcon;
    private final Image crossIcon;
    private final GridPane grid;
    private final List<ActionNode<TextField>> inputList = new ArrayList<>();
    private final Map<String, String> invalidInputMsgMap = new HashMap<>();
    private final List<ActionNode<ComboBox<String>>> comboBoxList = new ArrayList<>();
    CheckBox saveToCSVCheckBox;
    private final Application application;

    public UserConfigForm(Application application) throws FileNotFoundException {
        isFileConfigSelected = false;
        configName = null;
        configOptions = null;
        tickIcon = new Image(new FileInputStream(ICONS_DIRECTORY + "/tick.png"));
        crossIcon = new Image(new FileInputStream(ICONS_DIRECTORY + "/cross.png"));
        grid = new GridPane();
        grid.getStyleClass().add("root");
        grid.setHgap(8);
        grid.setVgap(4);
        this.application = application;

        render();
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

    private void renderTitle() {
        Label title = new Label("Simulation configuration");
        title.getStyleClass().add("title");
        grid.add(title, 0, currentRowIndex++, 2, 1);
    }

    private void renderButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setOnAction(this::onSubmit);
        grid.add(button, 0, currentRowIndex++);
    }

    private void renderInputRow(WorldConfigOptions option, boolean includeZero) {
        Pane pane = new Pane();
        TextField input = new TextField();
        input.setPrefWidth(INPUT_WIDTH);
        input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches(includeZero ? "^(0|([1-9][0-9]*))" : "^([1-9][0-9]*)") || change.getControlNewText().equals(""))
                return change;
            return null;
        }));
        pane.getChildren().add(input);

        inputList.add(new ActionNode<>(input, pane, option.getName()));
        grid.addRow(currentRowIndex++, new Label(option.getRepresentativeText()), pane);
    }

    private void renderComboBox(String name, String labelText, String[] options) {
        Pane pane = new Pane();
        ComboBox<String> combo = new ComboBox<>();
        combo.setPrefWidth(COMBO_BOX_WIDTH);
        combo.getItems().addAll(options);
        pane.getChildren().add(combo);

        comboBoxList.add(new ActionNode<>(combo, pane, name));
        grid.addRow(currentRowIndex++, new Label(labelText), pane);
    }

    private void renderConfigComboBox(String labelText) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().add("");
        combo.getItems().addAll(WorldConfig.getConfigNames());
        combo.setOnAction(event -> {
            configName = combo.getSelectionModel().getSelectedItem();
            isFileConfigSelected = !configName.equals("");
            setFormDisabledStatus(isFileConfigSelected);
        });
        grid.addRow(currentRowIndex++, new Label(labelText), combo);
    }

    private void renderCheckBox() {
        saveToCSVCheckBox = new CheckBox();
        grid.addRow(currentRowIndex++, new Label("Save logs as CSV file"), saveToCSVCheckBox);
    }

    private void renderSubtitle(String text) {
        Label subtitle = new Label(text);
        subtitle.getStyleClass().add("subtitle");
        grid.add(subtitle, 0, currentRowIndex++, 2, 1);
        renderVSpacer(4);
    }

    private void renderVSpacer(int size) {
        Region spacer = new Region();
        spacer.setMinHeight(size);
        spacer.setPrefHeight(size);
        grid.add(spacer, 0, currentRowIndex++, 2, 1);
    }

    private void render() {
        renderTitle();
        renderVSpacer(32);
        renderConfigComboBox("Predefined configurations");
        renderVSpacer(16);
        renderMapSettings();
        renderVSpacer(16);
        renderAnimalsSettings();
        renderVSpacer(16);
        renderPlantsSettings();
        renderVSpacer(8);
        renderCheckBox();
        renderVSpacer(16);
        renderButton("Start new simulation");
    }

    private void renderMapSettings() {
        renderSubtitle("Map settings");
        renderInputRow(MAP_WIDTH, false);
        renderInputRow(MAP_HEIGHT, false);
        renderComboBox(MAP_TYPE.getName(),  MAP_TYPE.getRepresentativeText(), new String[]{"Earth", "Infernal portal"});
    }

    private void renderAnimalsSettings() {
        renderSubtitle("Animals settings");
        renderInputRow(INITIAL_ANIMALS_NUMBER, false);
        renderInputRow(INITIAL_ANIMALS_ENERGY, false);
        renderComboBox(ANIMAL_BEHAVIOUR.getName(), ANIMAL_BEHAVIOUR.getRepresentativeText(), new String[]{"Full fate", "Slight madness"});
        renderComboBox(ANIMAL_MUTATION.getName(), ANIMAL_MUTATION.getRepresentativeText(), new String[]{"Full random", "Slight adjustment"});
        renderInputRow(STRONG_ANIMAL_MINIMUM_ENERGY, false);
        renderInputRow(ENERGY_PER_EATING, false);
        renderInputRow(ENERGY_PER_REPRODUCING, false);
        renderInputRow(MINIMUM_MUTATIONS_NUMBER, true);
        renderInputRow(MAXIMUM_MUTATIONS_NUMBER, true);
        renderInputRow(ANIMAL_GENOME_SIZE, false);
    }

    private void renderPlantsSettings() {
        renderSubtitle("Plants settings");
        renderComboBox(PLANTS_GROWTH_VARIANT.getName(), PLANTS_GROWTH_VARIANT.getRepresentativeText(), new String[]{"Forest equators", "Toxic bodies"});
        renderInputRow(INITIAL_PLANTS_NUMBER, true);
        renderInputRow(PLANTS_PER_DAY, true);
    }

    private void setFormDisabledStatus(boolean value) {
        for (ActionNode<TextField> node : inputList)
            node.region().setDisable(value);
        for (ActionNode<ComboBox<String>> node : comboBoxList)
            node.region().setDisable(value);
    }

    private boolean validateRequired() {
        boolean isValid = true;

        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).region().getText().equals("")) {
                renderIcon(crossIcon, inputList, i, "This field is required (integer)");
                isValid = false;
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

    private boolean validateValues(Properties configOptions) {
        boolean isValid = true;
        List<Pair<WorldConfigOptions, String>> validatedOptions = new ArrayList<>();
        invalidInputMsgMap.clear();

        try {
            validatedOptions = WorldConfig.validate(configOptions);
        }
        catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            for (Pair<WorldConfigOptions, String> optionPair : validatedOptions)
                invalidInputMsgMap.put(optionPair.first().getName(), optionPair.second());

            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).region().getText().equals("")) {
                    renderIcon(crossIcon, inputList, i, "This field is required (integer)");
                    isValid = false;
                }
                if (invalidInputMsgMap.get(inputList.get(i).name()) != null) {
                    renderIcon(crossIcon, inputList, i, invalidInputMsgMap.get(inputList.get(i).name()));
                    isValid = false;
                }
                else {
                    renderIcon(tickIcon, inputList, i, null);
                }
            }
        }

        return isValid;
    }

    private void onSubmit(ActionEvent event) {
        if (isFileConfigSelected) {
            application.startSimulation(configName, saveToCSVCheckBox.isSelected());
        } else {
            if (!validateRequired())
                return;

            configOptions = new Properties();
            for (ActionNode<TextField> node : inputList)
                configOptions.setProperty(node.name(), node.region().getText());
            for (ActionNode<ComboBox<String>> node : comboBoxList) {
                if (node.region().getSelectionModel().getSelectedItem() == null)
                    continue;

                configOptions.setProperty(
                        node.name(),
                        node.region().getSelectionModel().getSelectedItem().toUpperCase().replace(' ', '_')
                );
            }

            if (!validateValues(configOptions))
                return;

            application.startSimulation(configOptions, saveToCSVCheckBox.isSelected());
        }
    }
}
