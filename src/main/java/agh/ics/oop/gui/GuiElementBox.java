package agh.ics.oop.gui;

import agh.ics.oop.*;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox implements IPositionChangeObserver, IDirectionChangeObserver {
    private final IMapElement mapElement;
    private final ImageView icon;
    private final VBox vBox;

    public GuiElementBox(IMapElement mapElement) throws FileNotFoundException {
        this.mapElement = mapElement;
        if (mapElement instanceof Animal a) {
            a.addDirectionObserver(this);
            a.addPositionObserver(this);
        }

        icon = new ImageView(new Image(new FileInputStream(mapElement.getResourceName())));
        icon.setSmooth(true);
        icon.setFitWidth(40);
        icon.setFitHeight(40);

        vBox = new VBox(0);
        vBox.getChildren().addAll(icon, getMapElementLabel());
        vBox.setAlignment(Pos.CENTER);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        rerenderBox();
    }

    @Override
    public void directionChanged(MapDirection newDirection) {
        try {
            icon.setImage(new Image(new FileInputStream(mapElement.getResourceName())));
            rerenderBox();
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

    private void rerenderBox() {
        Platform.runLater(() -> {
            vBox.getChildren().clear();
            vBox.getChildren().addAll(icon, getMapElementLabel());
        });
    }
}
