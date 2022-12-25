package agh.ics.oop.project1.world;

import agh.ics.oop.project1.gui.Application;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.maps.AbstractMap;
import agh.ics.oop.project1.world.maps.Earth;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("System wystartował");
        System.setProperty("prism.lcdtext", "false");
        javafx.application.Application.launch(Application.class, args);
    }
}
