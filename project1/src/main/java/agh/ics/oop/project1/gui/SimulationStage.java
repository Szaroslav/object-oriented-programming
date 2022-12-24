package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.AbstractOrganism;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.utils.Vector2d;
import agh.ics.oop.project1.world.WorldConfig;
import agh.ics.oop.project1.world.WorldConfigOptions;
import agh.ics.oop.project1.world.engine.IEngineObserver;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.maps.AbstractMap;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SimulationStage extends Stage implements IEngineObserver {
    private final int WIDTH = 1200;
    private final int HEIGHT = 600;
    private final int CELL_SIZE;
    private boolean isPaused = false;
    private final GridPane board;
    private final SimulationStageStats stats;
    private final VBox sideUI = new VBox();
    private final ComboBox<Animal> animalsList = new ComboBox<>();
    private final AbstractMap map;
    private WorldEngine engine;
    private final WorldConfig config;
    private final Image plantTexture;
    private final Image animalIcon;

    public SimulationStage(AbstractMap map, WorldConfig config) throws FileNotFoundException {
        board = new GridPane();
        stats = new SimulationStageStats(map.getStats());
        plantTexture = new Image(new FileInputStream("src/main/resources/textures/grass.png"));
        animalIcon = new Image(new FileInputStream("src/main/resources/icons/lion.png"));
        this.map = map;
        this.config = config;

        setOnCloseRequest(event -> engine.interrupt());

        CELL_SIZE = Math.min(WIDTH / map.getWidth(), HEIGHT / map.getHeight());
        for (int i = 0; i < map.getWidth(); i++) board.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        for (int i = 0; i < map.getHeight(); i++) board.getRowConstraints().add(new RowConstraints(CELL_SIZE));

        renderBoard();
        renderPauseButton();
        initAnimalsList();

        HBox hBox = new HBox(board, stats.getContent(), sideUI);
        Scene scene = new Scene(hBox, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm());
        setScene(scene);
    }

    @Override
    public void simulationDayFinished() {
        renderBoard();
        stats.render();
    }

    public int fromGameToBoardY(int y) {
        return map.getHeight() - y - 1;
    }

    public void setEngine(WorldEngine engine) {
        this.engine = engine;
        stats.setEngine(engine);
    }

    private void renderPauseButton() {
        Button pauseButton = new Button("Pause / resume simulation");
        pauseButton.setOnAction(event -> {
            isPaused = !isPaused;
            animalsList.setDisable(!isPaused);
            renderAnimalsList();

            if (isPaused)
                engine.pause();
            else
                engine.unpause();
        });

        sideUI.getChildren().add(pauseButton);
    }

    private void initAnimalsList() {
        renderAnimalsList();
        animalsList.setDisable(true);
        animalsList.setOnAction(event -> {
            stats.setSelectedAnimal(animalsList.getSelectionModel().getSelectedItem());
        });

        sideUI.getChildren().add(animalsList);
    }

    private void renderAnimalsList() {
        if (!isPaused)
            return;

        map.getAnimalsList().sort((a1, a2) -> {
            Vector2d pos1 = a1.getPosition(), pos2 = a2.getPosition();

            if (pos1.x != pos2.x) return pos1.x - pos2.x;
            if (pos1.y != pos2.y) return pos1.y - pos2.y;
            return 0;
        });

        Animal selectedAnimal = animalsList.getSelectionModel().getSelectedItem();
        animalsList.getItems().clear();
        animalsList.getItems().add(null);
        for (Animal animal : map.getAnimalsList()) animalsList.getItems().add(animal);

        if (animalsList.getItems().contains(selectedAnimal))
            animalsList.getSelectionModel().select(selectedAnimal);
        else
            animalsList.getSelectionModel().select(0);
    }

    private void renderBoard() {
        Platform.runLater(() -> {
            board.getChildren().clear();
            renderOrganisms(map.getPlantsList(), plantTexture, false);
            renderOrganisms(map.getAnimalsList(), animalIcon, true);
        });
    }

    private void renderOrganisms(List<? extends AbstractOrganism> organismsList, Image image, boolean renderHPBar) {
        for (AbstractOrganism organism : organismsList) {
            Pane pane = new Pane();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(CELL_SIZE);
            imageView.setFitHeight(CELL_SIZE);
            pane.getChildren().add(imageView);

            if (renderHPBar && organism instanceof Animal)
                renderAnimalHPBar((Animal) organism, pane);

            board.add(pane, organism.getPosition().x, fromGameToBoardY(organism.getPosition().y));
        }
    }

    private void renderAnimalHPBar(Animal animal, Pane root) {
        int strongAnimalEnergy = config.getInt(WorldConfigOptions.STRONG_ANIMAL_MINIMUM_ENERGY.getName());
        Rectangle rectangle = new Rectangle(CELL_SIZE, 6);
        rectangle.setFill(Color.hsb(
                88.0 * Math.min(animal.getEnergy(), strongAnimalEnergy) / strongAnimalEnergy,
                .82,
                .9
        ));
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.relocate(0, CELL_SIZE - 6);
        root.getChildren().add(rectangle);
    }
}