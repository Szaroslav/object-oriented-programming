package agh.ics.oop.project2.game;

import agh.ics.oop.project2.gui.Application;

import static java.lang.System.out;

public class Game {
    public static void main(String[] args) {
        out.println("System has been started");
        System.setProperty("prism.lcdtext", "false");
        javafx.application.Application.launch(Application.class, args);
    }
}
