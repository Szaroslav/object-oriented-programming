package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.world.config.WorldConfigOptions;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.config.WorldConfig;
import agh.ics.oop.project1.world.maps.AbstractMap;
import agh.ics.oop.project1.world.maps.Earth;
import agh.ics.oop.project1.world.maps.InfernalPortal;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Properties;

public class Application extends javafx.application.Application {
    private int simIndex = 0;
    private UserConfigForm userConfigForm;

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(userConfigForm.getForm(), 400, 816);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        try {
            userConfigForm = new UserConfigForm(this);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.out.println(ex);
        }
    }

    public void startSimulation(String configName, boolean saveToCSV) {
        WorldConfig config = new WorldConfig(configName);
        startSimulation(config, saveToCSV);
    }

    public void startSimulation(Properties configOptions, boolean saveToCSV) {
        WorldConfig config = new WorldConfig(configOptions);
        startSimulation(config, saveToCSV);
    }

    private void startSimulation(WorldConfig config, boolean saveToCSV) {
        AbstractMap map = new Earth(config);
        if (config.getProperty(WorldConfigOptions.MAP_TYPE.getName()).equals("INFERNAL_PORTAL"))
            map = new InfernalPortal(config);

        try {
            SimulationStage stage = new SimulationStage(map, config, simIndex++, saveToCSV);
            stage.show();

            WorldEngine engine = new WorldEngine(map, config, stage);
            engine.start();

            stage.setEngine(engine);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.out.println(ex);
        }
    }
}