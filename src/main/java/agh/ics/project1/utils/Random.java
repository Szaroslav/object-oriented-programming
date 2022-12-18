package agh.ics.project1.utils;

public class Random {
    public static int range(int from, int toExclusively) {
        return (int) (Math.random() * (toExclusively - from) + from);
    }

    public static double range(double from, double toExclusively) {
        return Math.random() * (toExclusively - from) + from;
    }
}
