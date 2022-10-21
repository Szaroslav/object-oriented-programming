package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static final Vector2d BOUNDARY_BOTTOM_LEFT = new Vector2d(0, 0);
    public static final Vector2d BOUNDARY_UPPER_RIGHT = new Vector2d(4, 4);

    public static void main(String[] args) {
        out.println("System wystartował");

        Vector2d position1 = new Vector2d(1,2);
        out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        out.println(position2);
        out.println(position1.add(position2));

        Animal animal = new Animal();
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        out.println(animal.toString());

        out.println("System zakończył działanie");
    }

    public static void run(Direction[] dirs) {
        out.println("Zwierzak zaczyna się poruszać");

        for (Direction dir : dirs) {
            switch (dir) {
                case FORWARD -> out.println("Zwierzak idzie do przodu");
                case BACKWARD -> out.println("Zwierzak idzie do tyłu");
                case RIGHT -> out.println("Zwierzak skręca w prawo");
                case LEFT -> out.println("Zwierzak skręca w lewo");
            }
        }

        out.println("Zwierzak poszedł nareszcie na upragnioną drzemkę");
    }
}