package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private final VBox vBox;

    public GuiElementBox(IMapElement mapElement) throws FileNotFoundException {
        ImageView icon = new ImageView(new Image(new FileInputStream(mapElement.getResourceName())));
        icon.setSmooth(true);
        icon.setFitWidth(40);
        icon.setFitHeight(40);

        vBox = new VBox(0);
        vBox.getChildren().addAll(icon, new Label(mapElement.getLabel()));
        vBox.setAlignment(Pos.CENTER);
    }

    public VBox getBox() {
        return vBox;
    }
}
