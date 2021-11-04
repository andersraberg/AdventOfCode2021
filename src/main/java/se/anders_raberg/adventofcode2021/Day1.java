package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day1 {
    private Day1() {
    }

    public static void run() throws IOException {
        Files.readAllLines(Paths.get("inputs/input1.txt")).stream() //
                .forEach(System.out::println);

    }
}
