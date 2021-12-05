package se.anders_raberg.adventofcode2021;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {
    private static final Logger LOGGER = Logger.getLogger(Day5.class.getName());
    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    private record Point(int x, int y) {
    }

    private record Line(int startX, int startY, int endX, int endY) {
    }

    private Day5() {
    }

    public static void run() throws IOException {
        List<Line> lines = Files.readAllLines(Paths.get("inputs/input5.txt")).stream() //
                .map(Day5::parse) //
                .toList();

        List<Line> horizontalLines = lines.stream().filter(l -> l.startY == l.endY).toList();
        List<Line> verticalLines = lines.stream().filter(l -> l.startX == l.endX).toList();
        List<Line> diagonalLines = lines.stream().filter(l -> l.startX != l.endX).filter(l -> l.startY != l.endY)
                .toList();

        List<Point> pointsOnLines = new ArrayList<>();

        // Part 1
        horizontalLines.forEach(l -> pointsOnLines.addAll(walkLine(l)));
        verticalLines.forEach(l -> pointsOnLines.addAll(walkLine(l)));
        LOGGER.info("Part 1: " + countOverlapping(pointsOnLines));

        // Part2
        diagonalLines.forEach(l -> pointsOnLines.addAll(walkLine(l)));
        LOGGER.info("Part 2: " + countOverlapping(pointsOnLines));
    }

    private static List<Point> walkLine(Line line) {
        int xStep = Integer.compare(line.endX, line.startX);
        int yStep = Integer.compare(line.endY, line.startY);
        List<Point> result = new ArrayList<>();
        Point current = new Point(line.startX, line.startY);
        Point end = new Point(line.endX, line.endY);
        while (!current.equals(end)) {
            result.add(current);
            current = new Point(current.x + xStep, current.y + yStep);
        }
        result.add(current);
        return result;
    }

    private static Line parse(String line) {
        Matcher m = PATTERN.matcher(line);
        m.find();
        return new Line(parseInt(m.group(1)), parseInt(m.group(2)), parseInt(m.group(3)), parseInt(m.group(4)));
    }

    private static long countOverlapping(List<Point> points) {
        return points.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values()
                .stream().filter(v -> v > 1).count();
    }
}
