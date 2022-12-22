package agh.ics.oop.project1.world;

import agh.ics.oop.project1.gui.Application;
import agh.ics.oop.project1.world.engine.WorldEngine;
import agh.ics.oop.project1.world.maps.AbstractMap;
import agh.ics.oop.project1.world.maps.Earth;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("System wystartował");
        javafx.application.Application.launch(Application.class, args);

//        try {
//            javafx.application.Application.launch(Application.class, args);
//            out.println("System pomyślnie zakończył działanie");
//        }
//        catch (IllegalArgumentException ex) {
//            out.println("System niepomyślnie zakończył działanie");
//            out.println(ex.getMessage());
//        }
    }
}
