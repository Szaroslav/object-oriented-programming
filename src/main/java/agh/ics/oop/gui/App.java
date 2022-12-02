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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Map;

public class App extends Application implements IPositionChangeObserver {
    private GridPane grid;
    private AbstractWorldMap map;
    private Map<Vector2d, GuiElementBox> guiElementBoxes;

    @Override
    public void start(Stage primaryStage) {
        try {
            grid = new GridPane();
            renderGrid();

            Scene scene = new Scene(grid, 768, 768);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void init() {
        MoveDirection[] directions = OptionsParser.parse(getParameters().getRaw().toArray(new String[0]));
        map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(3, 4) };
        IEngine engine = new SimulationEngine(directions, map, positions, 10);
        engine.run();
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (Node node : grid.getChildren()) {
            if (node.equals(guiElementBoxes.get(oldPosition).getBox())) {

            }
        }
    }

    private void addToGrid(Node node, int i, int j) {
        grid.add(node, i, j);
        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);
    }

    private void renderGrid() throws FileNotFoundException {
        Vector2d[] boundary = map.getBoundary();
        final int width = boundary[1].x - boundary[0].x + 2;
        final int height = boundary[1].y - boundary[0].y + 2;
        grid.setGridLinesVisible(true);

        renderAxis(boundary[0], boundary[1]);
        renderBody(boundary[0], boundary[1]);
        setGridCellsSize(width, height, 64, 64);
    }

    private void renderAxis(Vector2d lowerLeft, Vector2d upperRight) {
        addToGrid(new Label("y\\x"), 0, 0);

        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
            addToGrid(new Label(Integer.toString(x)), i, 0);
        }
        for (int y = lowerLeft.y, i = upperRight.y - lowerLeft.y + 1; y <= upperRight.y; y++, i--) {
            addToGrid(new Label(Integer.toString(y)), 0, i);
        }
    }

    private void renderBody(Vector2d lowerLeft, Vector2d upperRight) throws FileNotFoundException {
        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
            for (int y = lowerLeft.y, j = upperRight.y - lowerLeft.y + 1; y <= upperRight.y; y++, j--) {
                if (map.objectAt(new Vector2d(x, y)) != null)
                    addToGrid(new GuiElementBox((IMapElement) map.objectAt(new Vector2d(x, y))).getBox(), i, j);
            }
        }
    }

    private void setGridCellsSize(int gridWidth, int gridHeight, int w, int h) {
        for (int i = 0; i < gridWidth; i++)
            grid.getColumnConstraints().add(new ColumnConstraints(w));
        for (int i = 0; i < gridHeight; i++)
            grid.getRowConstraints().add(new RowConstraints(h));
    }
}
