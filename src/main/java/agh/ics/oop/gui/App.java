package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {
    AbstractWorldMap map;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        renderGrid(grid);

        Scene scene = new Scene(grid, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        MoveDirection[] directions = OptionsParser.parse(getParameters().getRaw().toArray(new String[0]));
        map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(3, 4) };
        IEngine engine = new SimulationEngine(directions, map, positions, 10);
//        engine.run();
    }

    private void addToGrid(GridPane grid, Node node, int i, int j) {
        grid.add(node, i, j);
        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);
    }

    private void renderGrid(GridPane grid) {
        Vector2d[] boundary = map.getBoundary();
        final int width = boundary[1].x - boundary[0].x + 2;
        final int height = boundary[1].y - boundary[0].y + 2;
        grid.setGridLinesVisible(true);

        renderAxis(grid, boundary[0], boundary[1]);
        renderBody(grid, boundary[0], boundary[1]);
        setGridCellsSize(grid, width, height, 20, 20);
    }

    private void renderAxis(GridPane grid, Vector2d lowerLeft, Vector2d upperRight) {
        addToGrid(grid, new Label("y\\x"), 0, 0);

        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
            addToGrid(grid, new Label(Integer.toString(x)), i, 0);
        }
        for (int y = lowerLeft.y, i = upperRight.y - lowerLeft.y + 1; y <= upperRight.y; y++, i--) {
            addToGrid(grid, new Label(Integer.toString(y)), 0, i);
        }
    }

    private void renderBody(GridPane grid, Vector2d lowerLeft, Vector2d upperRight) {
        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
            for (int y = lowerLeft.y, j = upperRight.y - lowerLeft.y + 1; y <= upperRight.y; y++, j--) {
                if (map.objectAt(new Vector2d(x, y)) != null)
                    addToGrid(grid, new Label(map.objectAt(new Vector2d(x, y)).toString()), i, j);
            }
        }
    }

    private void setGridCellsSize(GridPane grid, int gridWidth, int gridHeight, int w, int h) {
        for (int i = 0; i < gridWidth; i++)
            grid.getColumnConstraints().add(new ColumnConstraints(w));
        for (int i = 0; i < gridHeight; i++)
            grid.getRowConstraints().add(new RowConstraints(h));
    }
}
