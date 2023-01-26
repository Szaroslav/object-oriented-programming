package agh.ics.oop.project2.gui;

import agh.ics.oop.project2.game.world.elements.AbstractWorldElement;
import agh.ics.oop.project2.game.world.elements.WorldElements;
import javafx.scene.image.ImageView;

public class WorldElementGUI {
    public final AbstractWorldElement worldElement;
    public final ImageView imageView;

    public WorldElementGUI(WorldElements element) {
        worldElement = null;

        if (element == WorldElements.RIVER) {
            imageView = new ImageView(ImageAtlas.getRiver());
        }
        else if (element == WorldElements.BRIDGE) {
            imageView = new ImageView(ImageAtlas.getBridge());
        }
        else {
            imageView = new ImageView();
        }

        imageView.setFitHeight(Application.CELL_SIZE);
        imageView.setFitWidth(Application.CELL_SIZE);
    }

    public WorldElementGUI(AbstractWorldElement el) {
        worldElement = el;
        imageView = null;
    }
}
