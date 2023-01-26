package agh.ics.oop.project2.gui;

import agh.ics.oop.project2.game.heroes.Firefighter;
import agh.ics.oop.project2.game.world.elements.AbstractWorldElement;
import agh.ics.oop.project2.game.world.elements.WorldElements;
import javafx.scene.image.ImageView;

public class WorldElementGUI {
    public final ImageView imageView;
    protected AbstractWorldElement worldElement;

    public WorldElementGUI() {
        worldElement = null;
        imageView = new ImageView();
        imageView.setFitHeight(Application.CELL_SIZE);
        imageView.setFitWidth(Application.CELL_SIZE);
    }

    public WorldElementGUI(WorldElements element) {
       this();

        if (element == WorldElements.RIVER) {
            imageView.setImage(ImageAtlas.getRiver());
        }
        else if (element == WorldElements.BRIDGE) {
            imageView.setImage(ImageAtlas.getBridge());
        }
        else if (element == WorldElements.OBSTACLE) {
            imageView.setImage(ImageAtlas.getObstacle());
        }
        else if (element == WorldElements.SLOW_OBSTACLE) {
            imageView.setImage(ImageAtlas.getSlowObstacle());
        }
    }

    public WorldElementGUI(AbstractWorldElement element) {
        this();

        if (element instanceof Firefighter) {
            worldElement = element;
            imageView.setImage(ImageAtlas.getFirefighter());
        }
    }

    public AbstractWorldElement getWorldElement() {
        return worldElement;
    }
}
