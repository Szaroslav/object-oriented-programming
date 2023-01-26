package agh.ics.oop.project2.gui;

import javafx.scene.image.Image;

public final class ImageAtlas {
    // superheroes
    // ...
    private static final Image river = new Image("file:src/main/resources/images/river-tile.png");
    private static final Image bridge = new Image("file:src/main/resources/images/bridge-tile.png");
    private static final Image obstacle = new Image("file:src/main/resources/images/obstacle-tile.png");
    private static final Image slowObstacle = new Image("file:src/main/resources/images/slow-obstacle-tile.png");
//    public static final Image superheroesHeadquarters = new Image("");
//    public static final Image majorApartments = new Image("");

    public static Image getRiver() {
        return river;
    }

    public static Image getBridge() {
        return bridge;
    }

    public static Image getObstacle() {
        return obstacle;
    }

    public static Image getSlowObstacle() {
        return slowObstacle;
    }
}
