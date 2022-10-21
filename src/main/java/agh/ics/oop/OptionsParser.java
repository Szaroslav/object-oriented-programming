package agh.ics.oop;

public class OptionsParser {
    public static MoveDirection[] parse(String[] strs) {
        int n = 0;
        for (String s : strs) {
            switch (s) {
                case "f", "b", "l", "r" -> n++;
            }
        }

        MoveDirection[] directions = new MoveDirection[n];
        for (String s : strs) {
            switch (s) {
                case
            }
        }
    }
}
