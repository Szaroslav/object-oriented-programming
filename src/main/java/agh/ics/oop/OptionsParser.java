package agh.ics.oop;

public class OptionsParser {
    private static int evalLength(String[] strs) {
        int len = 0;
        for (String s : strs) {
            switch (s) {
                case "f", "b", "l", "r" -> len++;
            }
        }

        return len;
    }

    public static MoveDirection[] parse(String[] strs) {
        MoveDirection[] directions = new MoveDirection[evalLength(strs)];
        int i = 0;
        for (String s : strs) {
            switch (s) {
                case "f" -> directions[i++] = MoveDirection.FORWARD;
                case "b" -> directions[i++] = MoveDirection.BACKWARD;
                case "l" -> directions[i++] = MoveDirection.LEFT;
                case "r" -> directions[i++] = MoveDirection.RIGHT;
            }
        }

        return directions;
    }
}