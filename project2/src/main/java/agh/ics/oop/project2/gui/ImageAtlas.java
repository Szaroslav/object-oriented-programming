package agh.ics.oop.project2.gui;

import javafx.scene.image.Image;

public final class ImageAtlas {
    // superheroes
    // ...
    private static final Image firefighter = new Image("file:src/main/resources/images/firefighter.png");
    private static final Image river = new Image("file:src/main/resources/images/river-tile.png");
    private static final Image bridge = new Image("file:src/main/resources/images/bridge-tile.png");
    private static final Image obstacle = new Image("file:src/main/resources/images/obstacle.png");
    private static final Image slowObstacle = new Image("file:src/main/resources/images/slow-obstacle.png");
    // Hi, I'm Endrju Golora, professional fire
    private static final Image fireEvent = new Image("file:src/main/resources/images/fire.png");
    private static final Image villainEvent = new Image("file:src/main/resources/images/villain.png");
    private static final Image availableMove = new Image("file:src/main/resources/images/available-move.png");
//    public static final Image superheroesHeadquarters = new Image("");
//    public static final Image majorApartments = new Image("");


    public static Image getFirefighter() {
        return firefighter;
    }

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

    public static Image getFireEvent() {
        return fireEvent;
    }

    public static Image getVillainEvent() {
        return villainEvent;
    }
}
