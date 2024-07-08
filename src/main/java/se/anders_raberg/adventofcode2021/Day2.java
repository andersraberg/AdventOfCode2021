package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import se.anders_raberg.adventofcode2021.utilities.Pair;

public class Day2 {
    private static final Logger LOGGER = Logger.getLogger(Day2.class.getName());

    private enum Direction {
        UP, DOWN, FORWARD
    }

    private Day2() {
    }

    public static void run() throws IOException {

        List<Pair<Direction, Integer>> movements = Files.readAllLines(Paths.get("inputs/input2.txt")).stream() //
                .map(Day2::parse) //
                .toList();

        // Part 1
        int up = filterAndSum(movements, Direction.UP);
        int down = filterAndSum(movements, Direction.DOWN);
        int forward = filterAndSum(movements, Direction.FORWARD);

        LOGGER.info(String.format("Part 1: %d", (down - up) * forward));

        // Part 2
        int aim = 0;
        int depth = 0;
        int horizPos = 0;
        for (Pair<Direction, Integer> movement : movements) {
            switch (movement.first()) {
            case UP:
                aim -= movement.second();
                break;
            case DOWN:
                aim += movement.second();
                break;
            case FORWARD:
                horizPos += movement.second();
                depth += aim * movement.second();
                break;
            }
        }
        LOGGER.info(String.format("Part 2: %d", (depth * horizPos)));
    }

    private static Pair<Direction, Integer> parse(String data) {
        String[] split = data.split("\s+");
        return new Pair<>(Direction.valueOf(split[0].toUpperCase()), Integer.parseInt(split[1]));
    }

    private static int filterAndSum(List<Pair<Direction, Integer>> movements, Direction direction) {
        return movements.stream().filter(d -> d.first() == direction).mapToInt(Pair::second).sum();
    }

}
