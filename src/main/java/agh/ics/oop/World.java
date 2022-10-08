package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("System wystartował");

        Direction[] dirs = new Direction[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "f" -> dirs[i] = Direction.FORWARD;
                case "b" -> dirs[i] = Direction.BACKWARD;
                case "r" -> dirs[i] = Direction.RIGHT;
                case "l" -> dirs[i] = Direction.LEFT;
                default -> dirs[i] = Direction.IDLE;
            }
        }
        run(dirs);

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