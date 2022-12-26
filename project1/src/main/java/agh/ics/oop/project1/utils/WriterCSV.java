package agh.ics.oop.project1.utils;

import java.io.*;

public class WriterCSV {
    public static String parse(String[] data) {
        return String.join(",", data);
    }

    public static void writeln(File outputFile, String[] data) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true))) {
            writer.println(parse(data));
        }
    }
}

