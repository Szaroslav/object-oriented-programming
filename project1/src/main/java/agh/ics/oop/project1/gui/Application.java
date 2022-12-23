package agh.ics.oop.project1.gui;

import agh.ics.oop.project1.world.WorldConfigOptions;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.WorldConfig;
import agh.ics.oop.project1.world.maps.AbstractMap;
import agh.ics.oop.project1.world.maps.Earth;
import agh.ics.oop.project1.world.maps.InfernalPortal;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Properties;

public class Application extends javafx.application.Application {
    private final int CELL_SIZE = 52;
    private int width;
    private int height;
    private UserForm userForm;
//    private GridPane grid = new GridPane();
//    private VBox ui = new VBox();
//    private TextField moveDirectionsTextField = new TextField();
//    private AbstractWorldMap map;
//    private SimulationEngine engine;
//    private Map<Vector2d, GuiElementBox> guiElementBoxes = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
//        try {
//            renderGrid();
//            renderUI();
//
//            HBox hBox = new HBox(16, grid, ui);
//            Scene scene = new Scene(hBox, 768, 600);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        }
//        catch (FileNotFoundException ex) {
//            System.out.println(ex);
//        }
        Scene scene = new Scene(userForm.getForm(), 768, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        try {
            userForm = new UserForm(this);
        }
        catch (FileNotFoundException ex) {

        }
    }

    public void startSimulation(String configName) {
        WorldConfig config = new WorldConfig(configName);
        startSimulation(config);
    }

    public void startSimulation(Properties configOptions) {
        WorldConfig config = new WorldConfig(configOptions);
        startSimulation(config);
    }

    private void startSimulation(WorldConfig config) {
        AbstractMap map = new Earth(config);
        if (config.getProperty(WorldConfigOptions.MAP_TYPE.getName()).equals("INFERNAL_PORTAL"))
            map = new InfernalPortal(config);

        try {
            SimulationStage stage = new SimulationStage(map, config);
            stage.show();

            WorldEngine engine = new WorldEngine(map, config, stage);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        }
        catch (FileNotFoundException ex) {

        }

    }

//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
//        Platform.runLater(() -> {
//            final Vector2d[] b = map.getBoundary();
//            GuiElementBox guiElementBox = guiElementBoxes.remove(oldPosition);
//            grid.getChildren().remove(guiElementBox.getBox());
//            guiElementBoxes.put(newPosition, guiElementBox);
//
//            if (b[1].x - b[0].x + 2 == width && b[1].y - b[0].y + 2 == height) {
//                int i = newPosition.x - b[0].x + 1, j = b[1].y - newPosition.y + 1;
//                addToGrid(guiElementBox.getBox(), i, j);
//            }
//            else {
//                width = b[1].x - b[0].x + 2;
//                height = b[1].y - b[0].y + 2;
//                rerenderGrid();
//            }
//        });
//    }
//
//    private void addToGrid(Node node, int i, int j) {
//        grid.add(node, i, j);
//        GridPane.setHalignment(node, HPos.CENTER);
//        GridPane.setValignment(node, VPos.CENTER);
//    }
//
//    private void renderGrid() throws FileNotFoundException {
//        Vector2d[] boundary = map.getBoundary();
//        width = boundary[1].x - boundary[0].x + 2;
//        height = boundary[1].y - boundary[0].y + 2;
//        grid.setGridLinesVisible(true);
//
//        renderAxis(boundary[0], boundary[1]);
//        renderBody(boundary[0], boundary[1]);
//        setGridCellsSize();
//    }
//
//    private void renderAxis(Vector2d lowerLeft, Vector2d upperRight) {
//        addToGrid(new Label("y\\x"), 0, 0);
//
//        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
//            addToGrid(new Label(Integer.toString(x)), i, 0);
//        }
//        for (int y = lowerLeft.y, i = height - 1; y <= upperRight.y; y++, i--) {
//            addToGrid(new Label(Integer.toString(y)), 0, i);
//        }
//    }
//
//    private void renderBody(Vector2d lowerLeft, Vector2d upperRight) throws FileNotFoundException {
//        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
//            for (int y = lowerLeft.y, j = height - 1; y <= upperRight.y; y++, j--) {
//                Vector2d p = new Vector2d(x, y);
//                IMapElement el = (IMapElement) map.objectAt(p);
//
//                if (el != null) {
//                    GuiElementBox guiElementBox = new GuiElementBox(el);
//                    guiElementBoxes.put(p, guiElementBox);
//                    addToGrid(guiElementBox.getBox(), i, j);
//
//                    ((AbstractWorldMapElement) el).addPositionObserver(this);
//                }
//            }
//        }
//    }
//
//    private void renderUI() {
//        Button button = new Button("Start");
//        button.setOnAction(event -> {
//            engine.setMoveDirections(OptionsParser.parse(moveDirectionsTextField.getText().split(" ")));
//            Thread engineThread = new Thread(engine);
//            engineThread.start();
//        });
//
//        VBox spacer = new VBox();
//        spacer.setMinHeight(16);
//
//        ui.getChildren().addAll(new Label("Move directions (f, b, l, r):"), moveDirectionsTextField, spacer, button);
//    }
//
//    private void rerenderGrid() {
//        final Vector2d lowerLeft = map.getBoundary()[0], upperRight = map.getBoundary()[1];
//
//        grid.getChildren().clear();
//        renderAxis(lowerLeft, upperRight);
//        for (Map.Entry<Vector2d, GuiElementBox> entry : guiElementBoxes.entrySet()) {
//            int i = entry.getKey().x - lowerLeft.x + 1, j = upperRight.y - entry.getKey().y + 1;
//            addToGrid(entry.getValue().getBox(), i, j);
//        }
//    }
//
//    private void setGridCellsSize() {
//        for (int i = 0; i < width; i++)
//            grid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
//        for (int i = 0; i < height; i++)
//            grid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
//    }
}