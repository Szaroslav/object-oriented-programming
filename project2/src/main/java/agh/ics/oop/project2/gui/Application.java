package agh.ics.oop.project2.gui;

import agh.ics.oop.project2.game.world.City;
import agh.ics.oop.project2.game.world.elements.Obstacle;
import agh.ics.oop.project2.game.world.elements.WorldElements;
import agh.ics.oop.project2.utils.Vector2d;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    public final static int CELL_SIZE = 24;
    private City city;
    private GridPane board;

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(board, 400, 600);
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        city = new City(16, 16);
        board = new GridPane();

        initGUI();
    }

    private void initGUI() {
        renderBoard();
//        renderSideGUI();

        initBoard();
//        setOnCloseRequest(event -> engine.interrupt());

//        GridPane grid = new GridPane();
//        grid.getStyleClass().add("root");
//        grid.setHgap(8);
//        grid.setVgap(8);
//        grid.add(board, 0, 0);
//        grid.add(stats.getContent(), 1, 0);
//        grid.add(sideGUI, 0, 1);
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm());
    }

    private void initBoard() {
        board.setGridLinesVisible(true);
        for (int i = 0; i < city.WIDTH; i++) board.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        for (int i = 0; i < city.HEIGHT; i++) board.getRowConstraints().add(new RowConstraints(CELL_SIZE));
    }

    private void renderBoard() {
        Platform.runLater(() -> {
            board.getChildren().clear();
            for (Vector2d pos : city.getRiver().getWaterPositionsList()) {
                WorldElementGUI elementGUI = new WorldElementGUI(WorldElements.RIVER);
                board.add(elementGUI.imageView, pos.x, city.HEIGHT - pos.y - 1);
            }
            for (Vector2d pos : city.getRiver().getBridgePositionsList()) {
                WorldElementGUI elementGUI = new WorldElementGUI(WorldElements.BRIDGE);
                board.add(elementGUI.imageView, pos.x, city.HEIGHT - pos.y - 1);
            }
            for (Obstacle obs : city.getObstaclesList()) {
                WorldElementGUI elementGUI = new WorldElementGUI(obs.elementType);
                board.add(elementGUI.imageView, obs.getPosition().x, city.HEIGHT - obs.getPosition().y - 1);
            }
        });
    }
}
