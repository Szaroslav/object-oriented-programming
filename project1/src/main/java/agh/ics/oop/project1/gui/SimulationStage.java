package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.world.AbstractOrganism;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.world.Vector2d;
import agh.ics.oop.project1.world.config.WorldConfig;
import agh.ics.oop.project1.world.config.WorldConfigOptions;
import agh.ics.oop.project1.world.engine.IEngineObserver;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.maps.AbstractMap;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimulationStage extends Stage implements IEngineObserver {
    private final int WIDTH = 1200;
    private final int HEIGHT = 720;
    private final int BOARD_WIDTH = 700;
    private final int BOARD_HEIGHT = 620;
    private final int CELL_SIZE;
    private boolean isPaused = false;
    private final GridPane board;
    private final SimulationStageStats stats;
    private final VBox sideGUI = new VBox(8);
    private final ComboBox<Animal> animalsList = new ComboBox<>();
    private final AbstractMap map;
    private WorldEngine engine;
    private final WorldConfig config;
    private final Image plantTexture;
    private final Image animalIcon;

    public SimulationStage(AbstractMap map, WorldConfig config, int index, boolean saveToCSV) throws FileNotFoundException {
        CELL_SIZE = Math.min(BOARD_WIDTH / map.getWidth(), BOARD_HEIGHT / map.getHeight());
        board = new GridPane();
        stats = new SimulationStageStats(map.getStats(), index, config.getInt(WorldConfigOptions.ANIMAL_GENOME_SIZE.getName()), saveToCSV);
        plantTexture = new Image(new FileInputStream("src/main/resources/textures/grass.png"));
        animalIcon = new Image(new FileInputStream("src/main/resources/icons/lion.png"));
        this.map = map;
        this.config = config;

        initGUI();
    }

    @Override
    public void simulationDayFinished() {
        renderBoard(false);
        stats.render();
        stats.saveToCSV();
    }

    public int fromGameToBoardY(int y) {
        return map.getHeight() - y - 1;
    }

    public void setEngine(WorldEngine engine) {
        this.engine = engine;
        stats.setEngine(engine);
    }

    private void initGUI() {
        renderBoard(false);
        renderSideGUI();

        initBoard();
        setOnCloseRequest(event -> engine.interrupt());

        GridPane grid = new GridPane();
        grid.getStyleClass().add("root");
        grid.setHgap(8);
        grid.setVgap(8);
        grid.add(board, 0, 0);
        grid.add(stats.getContent(), 1, 0);
        grid.add(sideGUI, 0, 1);

        Scene scene = new Scene(grid, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm());
        setScene(scene);
    }

    private void initBoard() {
        for (int i = 0; i < map.getWidth(); i++) board.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        for (int i = 0; i < map.getHeight(); i++) board.getRowConstraints().add(new RowConstraints(CELL_SIZE));
    }

    private void renderSideGUI() {
        initAnimalsList();
        renderAnimalsList();
        renderPauseButton();
    }

    private void renderPauseButton() {
        Button pauseButton = new Button("Pause / resume simulation");
        pauseButton.setOnAction(event -> {
            isPaused = !isPaused;
            animalsList.setDisable(!isPaused);
            renderAnimalsList();

            if (isPaused) {
                engine.pause();
                renderBoard(true);
            }
            else {
                engine.unpause();
            }
        });

        sideGUI.getChildren().add(pauseButton);
    }

    private void initAnimalsList() {
        renderAnimalsList();
        animalsList.setDisable(true);
        animalsList.setPrefWidth(200);
        animalsList.setOnAction(event -> {
            stats.setSelectedAnimal(animalsList.getSelectionModel().getSelectedItem());
        });

        sideGUI.getChildren().add(animalsList);
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

    private void renderBoard(boolean highlight) {
        Platform.runLater(() -> {
            board.getChildren().clear();
            renderOrganisms(map.getPlantsList(), plantTexture, false, false);
            renderOrganisms(map.getAnimalsList(), animalIcon, true, highlight);
        });
    }

    private void renderOrganisms(List<? extends AbstractOrganism> organismsList, Image image, boolean renderHPBar, boolean highlight) {
        for (AbstractOrganism organism : organismsList) {
            Pane pane = new Pane();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(CELL_SIZE);
            imageView.setFitHeight(CELL_SIZE);
            pane.getChildren().add(imageView);

            if (renderHPBar && organism instanceof Animal)
                renderAnimalHPBar((Animal) organism, pane);
            if (highlight && organism instanceof Animal && Arrays.equals(map.getStats().getCommonGenes(), ((Animal) organism).getGenes()) && CELL_SIZE >= 16)
                highlightAnimal(pane);

            board.add(pane, organism.getPosition().x, fromGameToBoardY(organism.getPosition().y));
        }
    }

    private void renderAnimalHPBar(Animal animal, Pane root) {
        final int strongAnimalEnergy = config.getInt(WorldConfigOptions.STRONG_ANIMAL_MINIMUM_ENERGY.getName());
        final int height = CELL_SIZE >= 16 ? 6 : CELL_SIZE;

        Rectangle rectangle = new Rectangle(CELL_SIZE, height);
        rectangle.setFill(Color.hsb(
            88.0 * Math.min(animal.getEnergy(), strongAnimalEnergy) / strongAnimalEnergy,
            .82,
            .9
        ));
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.relocate(0, CELL_SIZE - height);
        root.getChildren().add(rectangle);
    }

    private void highlightAnimal(Pane root) {
        Rectangle rectangle = new Rectangle(CELL_SIZE * .25, CELL_SIZE * .25);
        rectangle.setFill(Color.GOLD);
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.relocate(CELL_SIZE - CELL_SIZE * .25 - 1, 1);
        root.getChildren().add(rectangle);
    }
}