package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Day1 {
    private static final Logger LOGGER = Logger.getLogger(Day1.class.getName());

    private Day1() {
    }

    public static void run() throws IOException {

        List<Integer> data = Files.readAllLines(Paths.get("inputs/input1.txt")).stream() //
                .map(Integer::valueOf) //
                .toList();

        LOGGER.info("Part 1: " + countIncreases(data));
        LOGGER.info("Part 2: " + countIncreases(generateRollingSums(data)));
    }

    private static int countIncreases(List<Integer> data) {
        int counter = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) > data.get(i - 1)) {
                counter++;
            }
        }
        return counter;
    }

    private static List<Integer> generateRollingSums(List<Integer> data) {
        List<Integer> result = new ArrayList<>();
        for (int i = 2; i < data.size(); i++) {
            result.add(data.get(i - 2) + data.get(i - 1) + data.get(i));
        }
        return result;
    }
}
