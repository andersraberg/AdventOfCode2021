package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.google.common.collect.Range;

public class Day22Part1 {
    private static final Logger LOGGER = Logger.getLogger(Day22Part1.class.getName());
    private static final Pattern PATTERN = Pattern.compile("(on|off) " //
            + "x=(-?\\d+)..(-?\\d+)," //
            + "y=(-?\\d+)..(-?\\d+)," //
            + "z=(-?\\d+)..(-?\\d+)");

    private static final Range<Integer> INIT_AREA_RANGE = Range.closed(-50, 50);

    private enum Mode {
        ON, OFF;
    }

    private record Coord(int x, int y, int z) {
    }

    private record Step(Mode mode, Range<Integer> xRange, Range<Integer> yRange, Range<Integer> zRange) {
        public Set<Coord> getCoords() {
            Set<Coord> res = new HashSet<>();
            for (int x : toArray(xRange)) {
                for (int y : toArray(yRange)) {
                    for (int z : toArray(zRange)) {
                        res.add(new Coord(x, y, z));
                    }
                }
            }
            return res;
        }
    }

    private Day22Part1() {
    }

    public static void run() throws IOException {
        List<Step> steps = Files.readAllLines(Paths.get("inputs/input22.txt")).stream() //
                .map(Day22Part1::parse) //
                .filter(Day22Part1::insideInitArea) //
                .toList();

        Set<Coord> turnedOn = new HashSet<>();
        for (Step step : steps) {
            Set<Coord> coords = step.getCoords();
            switch (step.mode()) {
            case ON -> turnedOn.addAll(coords);
            case OFF -> turnedOn.removeAll(coords);
            default -> throw new IllegalArgumentException("Unexpected value: " + step.mode());
            }
        }
        LOGGER.info(() -> String.format("Part 1: %d", turnedOn.size()));
    }

    private static Step parse(String str) {
        Matcher m = PATTERN.matcher(str);
        m.find();
        return new Step(Mode.valueOf(m.group(1).toUpperCase()), //
                Range.closed(parse(m, 2), parse(m, 3)), //
                Range.closed(parse(m, 4), parse(m, 5)), //
                Range.closed(parse(m, 6), parse(m, 7)));
    }

    private static int parse(Matcher m, int group) {
        return Integer.parseInt(m.group(group));
    }

    private static int[] toArray(Range<Integer> range) {
        return IntStream.rangeClosed(range.lowerEndpoint(), range.upperEndpoint()).toArray();
    }

    private static boolean insideInitArea(Step step) {
        return INIT_AREA_RANGE.encloses(step.xRange()) && INIT_AREA_RANGE.encloses(step.yRange())
                && INIT_AREA_RANGE.encloses(step.zRange);
    }
}
