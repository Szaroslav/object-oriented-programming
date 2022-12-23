package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.AbstractOrganism;
import agh.ics.oop.project1.animal.Animal;
import agh.ics.oop.project1.world.WorldConfig;
import agh.ics.oop.project1.world.WorldConfigOptions;
import agh.ics.oop.project1.world.engine.IEngineObserver;
import agh.ics.oop.project1.world.maps.AbstractMap;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class SimulationStage extends Stage implements IEngineObserver {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int CELL_SIZE;
    private final GridPane board;
    private final AbstractMap map;
    private final WorldConfig config;
    private final Image plantTexture;
    private final Image animalIcon;

    public SimulationStage(AbstractMap map, WorldConfig config) throws FileNotFoundException {
        board = new GridPane();
        plantTexture = new Image(new FileInputStream("src/main/resources/textures/grass.png"));
        animalIcon = new Image(new FileInputStream("src/main/resources/icons/lion.png"));
        this.map = map;
        this.config = config;

        CELL_SIZE = Math.min(WIDTH / map.getWidth(), HEIGHT / map.getHeight());
        for (int i = 0; i < map.getWidth(); i++) board.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        for (int i = 0; i < map.getHeight(); i++) board.getRowConstraints().add(new RowConstraints(CELL_SIZE));

        renderBoard();

        Scene scene = new Scene(board, 800, 600);
        setScene(scene);
    }

    @Override
    public void simulationDayFinished() {
        renderBoard();
    }

    public int fromGameToBoardY(int y) {
        return map.getHeight() - y - 1;
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

            if (renderHPBar && organism instanceof Animal) {
                int strongAnimalEnergy = config.getInt(WorldConfigOptions.STRONG_ANIMAL_MINIMUM_ENERGY.getName());
                Rectangle rectangle = new Rectangle(CELL_SIZE, 6);
                rectangle.setFill(Color.hsb(
                    88.0 * Math.min(((Animal) organism).getEnergy(), strongAnimalEnergy) / strongAnimalEnergy,
                    .82,
                    .9
                ));
                rectangle.setStroke(Color.TRANSPARENT);
                rectangle.relocate(0, CELL_SIZE - 6);
                pane.getChildren().add(rectangle);
            }

            board.add(pane, organism.getPosition().x, fromGameToBoardY(organism.getPosition().y));
        }
    }
}
