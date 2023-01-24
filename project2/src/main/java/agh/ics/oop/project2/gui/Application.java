package agh.ics.oop.project2.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Label("What"), 400, 816);
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
    }
}
