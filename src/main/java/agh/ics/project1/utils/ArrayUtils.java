package agh.ics.project1.utils;

public class ArrayUtils {
    public static int[] concatVar(int[] arr1, int[] arr2, double percentage, boolean fromLeft) {
        if (arr1.length != arr2.length || percentage < 0 || percentage > 1)
            throw new IllegalArgumentException();

        int[] arr = new int[arr1.length];
        if (fromLeft) {
            int boundIndex = (int) Math.ceil(arr1.length * percentage);
            System.arraycopy(arr1, 0, arr, 0, boundIndex);
            System.arraycopy(arr2, boundIndex, arr, boundIndex, arr2.length - boundIndex);
        }
        else {
            int boundIndex = (int) Math.floor(arr1.length * (1 - percentage));
            System.arraycopy(arr2, 0, arr, 0, boundIndex);
            System.arraycopy(arr1, boundIndex, arr, boundIndex, arr2.length - boundIndex);
        }

        return arr;
    }
}
