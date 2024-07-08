package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Day7 {
    private static final Logger LOGGER = Logger.getLogger(Day7.class.getName());

    private Day7() {
    }

    public static void run() throws IOException {
        List<Integer> crabPositions = Arrays
                .stream(new String(Files.readAllBytes(Paths.get("inputs/input7.txt"))).trim().split(","))
                .map(Integer::parseInt).toList();

        int maxTargetPos = crabPositions.stream().max(Integer::compare).orElseThrow();

        LOGGER.info(() -> String.format("Part 1: %d",
                IntStream.range(0, maxTargetPos).map(p -> costAllPart1(crabPositions, p)).min().orElseThrow()));
        LOGGER.info(() -> String.format("Part 2: %d",
                IntStream.range(0, maxTargetPos).map(p -> costAllPart2(crabPositions, p)).min().orElseThrow()));
    }

    private static int costAllPart1(List<Integer> crabPositions, int targetPos) {
        return crabPositions.stream().mapToInt(p -> Math.abs(p - targetPos)).sum();

    }

    private static int costAllPart2(List<Integer> crabPositions, int targetPos) {
        return crabPositions.stream().mapToInt(p -> costMoveOneCrab(Math.abs(p - targetPos))).sum();

    }

    private static int costMoveOneCrab(int positions) {
        return IntStream.rangeClosed(1, positions).sum();
    }

}
