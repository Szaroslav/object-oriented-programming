package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static final Vector2d BOUNDARY_BOTTOM_LEFT = new Vector2d(0, 0);
    public static final Vector2d BOUNDARY_UPPER_RIGHT = new Vector2d(4, 4);

    public static void main(String[] args) {
        out.println("System wystartował");

        Animal animal = new Animal();
        animal.moveRepeatedly(OptionsParser.parse(args));
        out.println(animal.toString());

        out.println("System zakończył działanie");
    }
}