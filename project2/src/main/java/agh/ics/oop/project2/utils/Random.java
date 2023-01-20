package agh.ics.oop.project2.utils;

public class Random {
    public static int range(int from, int toExclusively) {
        return (int) (Math.random() * (toExclusively - from) + from);
    }

    public static double range(double from, double toExclusively) {
        return Math.random() * (toExclusively - from) + from;
    }

    public static int rangeWithoutRep(int from, int toExclusively, int[] excludedNumbers) throws IllegalArgumentException {
        if (excludedNumbers.length >= toExclusively - from)
            throw new IllegalArgumentException("Excluded numbers array has the same or greater length as given range");

        while (true) {
            int number = range(from, toExclusively);
            boolean contains = false;

            for (int num : excludedNumbers) {
                if (number == num) {
                    contains = true;
                    break;
                }
            }

            if (!contains)
                return number;
        }
    }

    public static Vector2d randomPoint(Vector2d[] fromArray, Vector2d[] excludedPoints) throws IllegalArgumentException {
        if (excludedPoints.length >= fromArray.length)
            throw new IllegalArgumentException("Excluded points array has the same or greater length as given array");

        while (true) {
            int i = range(0, fromArray.length);
            boolean contains = false;

            for (Vector2d p : excludedPoints) {
                if (fromArray[i].equals(p)) {
                    contains = true;
                    break;
                }
            }

            if (!contains)
                return fromArray[i];
        }
    }
}
