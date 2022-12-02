package agh.ics.oop.gui;

import agh.ics.oop.IDirectionChangeObserver;
import agh.ics.oop.IMapElement;

import agh.ics.oop.MapDirection;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox implements IDirectionChangeObserver {
    private final IMapElement mapElement;
    private final ImageView icon;
    private final VBox vBox;

    public GuiElementBox(IMapElement mapElement) throws FileNotFoundException {
        this.mapElement = mapElement;

        icon = new ImageView(new Image(new FileInputStream(mapElement.getResourceName())));
        icon.setSmooth(true);
        icon.setFitWidth(40);
        icon.setFitHeight(40);

        vBox = new VBox(0);
        vBox.getChildren().addAll(icon, getMapElementLabel());
        vBox.setAlignment(Pos.CENTER);
    }

    @Override
    public void directionChanged(MapDirection newDirection) {
        try {
            icon.setImage(new Image(new FileInputStream(mapElement.getResourceName())));
            vBox.getChildren().clear();
            vBox.getChildren().addAll(icon, getMapElementLabel());
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public Label getMapElementLabel() {
        return new Label(mapElement.getLabelText());
    }

    public VBox getBox() {
        return vBox;
    }
}
