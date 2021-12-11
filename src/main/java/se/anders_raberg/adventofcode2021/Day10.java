package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Day10 {
    private static final Logger LOGGER = Logger.getLogger(Day10.class.getName());
    private static final Map<Character, Character> DELIMITER_PAIRS = Map.of( //
            '(', ')', //
            '[', ']', //
            '{', '}', //
            '<', '>');

    private static final Map<Character, Long> CLOSING_DELIMITER_VALUES_PART1 = Map.of( //
            ')', 3L, //
            ']', 57L, //
            '}', 1197L, //
            '>', 25137L);

    private static final Map<Character, Long> CLOSING_DELIMITER_VALUES_PART2 = Map.of( //
            ')', 1L, //
            ']', 2L, //
            '}', 3L, //
            '>', 4L);

    private Day10() {
    }

    public static void run() throws IOException {
        List<String> data = Files.readAllLines(Paths.get("inputs/input10.txt")).stream() //
                .toList();

        LOGGER.info(() -> String.format("Part 1: %d", data.stream() //
                .mapToLong(Day10::syntaxErrorValue) //
                .sum()));

        List<Long> values = data.stream() //
                .filter(c -> syntaxErrorValue(c) == 0) //
                .map(Day10::completionValue) //
                .sorted() //
                .toList();

        LOGGER.info("Part 2 : " + values.get(values.size() / 2));
    }

    private static long completionValue(String line) {
        Deque<Character> openingDelimiters = new ArrayDeque<>();
        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);
            if (DELIMITER_PAIRS.keySet().contains(current)) {
                openingDelimiters.push(current);
            } else {
                openingDelimiters.pop();
            }
        }
        return openingDelimiters.stream() //
                .map(c -> CLOSING_DELIMITER_VALUES_PART2.get(DELIMITER_PAIRS.get(c))) //
                .reduce(0L, (a, b) -> 5 * a + b);
    }

    private static long syntaxErrorValue(String line) {
        Deque<Character> openingDelimiters = new ArrayDeque<>();
        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);
            if (DELIMITER_PAIRS.keySet().contains(current)) {
                openingDelimiters.push(current);
            } else {
                Character pop = openingDelimiters.pop();
                if (current != DELIMITER_PAIRS.get(pop)) {
                    return CLOSING_DELIMITER_VALUES_PART1.get(current);
                }
            }
        }
        return 0;
    }

}
