package agh.ics.oop;

import static java.lang.System.out;
import static agh.ics.oop.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {
    @Test
    public void testParse() {
        out.println("Sprawdzam statyczną metodę OptionsParser.parse()");

        assertArrayEquals(
            new MoveDirection[]{ FORWARD, BACKWARD, LEFT, RIGHT },
            OptionsParser.parse(new String[] { "f", "b", "l", "r" })
        );
        assertArrayEquals(
                new MoveDirection[]{ FORWARD, BACKWARD, LEFT, RIGHT },
                OptionsParser.parse(new String[] { "f", "b", "B", "l", "r", "xD" })
        );
    }
}
