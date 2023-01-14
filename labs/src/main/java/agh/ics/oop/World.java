package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("System wystartował");

        try {
            Application.launch(App.class, args);
            out.println("System pomyślnie zakończył działanie");
        } catch (IllegalArgumentException ex) {
            out.println("System niepomyślnie zakończył działanie");
            out.println(ex.getMessage());
        }
    }
}