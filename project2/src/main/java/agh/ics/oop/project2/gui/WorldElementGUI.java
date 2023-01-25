package agh.ics.oop.project2.gui;

import agh.ics.oop.project2.game.world.AbstractWorldElement;
import agh.ics.oop.project2.game.world.WorldElements;
import javafx.scene.image.ImageView;

public class WorldElementGUI {
    public final AbstractWorldElement worldElement;
    public final ImageView imageView;

    public WorldElementGUI(WorldElements element) {
        worldElement = null;

        if (element == WorldElements.RIVER) {
            imageView = new ImageView(ImageAtlas.river);
        }
        else {
            imageView = null;
        }

        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
    }

    public WorldElementGUI(AbstractWorldElement el) {
        worldElement = el;
        imageView = null;
    }
}
