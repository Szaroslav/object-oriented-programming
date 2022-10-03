package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("System wystartował");

        Direction[] dirs = new Direction[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "f":
                    dirs[i] = Direction.FORWARD;
                    break;
                case "b":
                    dirs[i] = Direction.BACKWARD;
                    break;
                case "r":
                    dirs[i] = Direction.RIGHT;
                    break;
                case "l":
                    dirs[i] = Direction.LEFT;
                    break;
            }
        }
        run(dirs);

        out.println("System zakończył działanie");
    }

    public static void run(Direction[] dirs) {
        out.println("Zwierzak zaczyna się poruszać");

        for (Direction dir : dirs) {
            switch (dir) {
                case FORWARD:
                    out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    out.println("Zwierzak idzie do tyłu");
                    break;
                case RIGHT:
                    out.println("Zwierzak skręca w prawo");
                    break;
                case LEFT:
                    out.println("Zwierzak skręca w lewo");
                    break;
            }
        }

        out.println("Zwierzak poszedł nareszcie na upragnioną drzemkę");
    }
}