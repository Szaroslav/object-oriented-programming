package agh.ics.oop.project1.utils;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ArrayUtilsTest {
    @Test
    public void testConcatVar() {
        out.println("Testing ArrayUtils.concatVar(int[], int[], double, boolean)");

        assertArrayEquals(
            new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            ArrayUtils.concatVar(
                new int[]{0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5},
                new int[]{5, 5, 5, 5, 5, 5, 6, 7, 8, 9, 10},
                .5,
                true
            )
        );
        assertArrayEquals(
            new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            ArrayUtils.concatVar(
                new int[]{0, 0, 0, 0, 0, 5, 6, 7, 8, 9, 10},
                new int[]{0, 1, 2, 3, 4, 9, 9, 9, 9, 9, 9},
                .5,
                false
            )
        );
    }

    @Test
    public void testConcat() {
        out.println("Testing ArrayUtils.concat(int[], int[])");

        assertArrayEquals(
            new String[]{"Hello", "I", "Hope", "You", "Don't", "Read", "It", "2137 geng"},
            ArrayUtils.concat(
                new String[]{"Hello", "I", "Hope", "You", "Don't", "Read", "It"},
                new String[]{"2137 geng"}
            )
        );
        assertArrayEquals(
            new String[]{"That", "Was", "So", "Funny"},
            ArrayUtils.concat(
                new String[]{},
                new String[]{"That", "Was", "So", "Funny"}
            )
        );
    }
}
