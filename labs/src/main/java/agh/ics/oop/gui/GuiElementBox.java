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
        ((AbstractWorldMapElement) mapElement).addPositionObserver(this);
        if (mapElement instanceof Animal a) {
            a.addDirectionObserver(this);
        }

        icon = new ImageView(new Image(new FileInputStream(mapElement.getResourceName())));
        icon.setSmooth(true);
        icon.setFitWidth(32);
        icon.setFitHeight(32);

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
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public Label getMapElementLabel() {
        Label label = new Label(mapElement.getLabelText());
        label.setStyle("-fx-font-size: 10px");
        return label;
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
