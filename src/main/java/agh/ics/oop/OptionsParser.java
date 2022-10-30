package agh.ics.oop;

import static agh.ics.oop.MoveDirection.*;

public class OptionsParser {
    private static int evalLength(String[] strs) {
        int len = 0;
        String[] properStrs = getStringRepresentations();

        for (String s : strs) {
            for (String properStr : properStrs) {
                if (s.equals(properStr)) {
                    len++;
                    break;
                }
            }
        }

        return len;
    }

    public static MoveDirection[] parse(String[] strs) {
        MoveDirection[] directions = new MoveDirection[evalLength(strs)];
        int i = 0;
        for (String s : strs) {
            if (MoveDirection.fromString(s) != null)
                directions[i++] = MoveDirection.fromString(s);
        }

        return directions;
    }
}