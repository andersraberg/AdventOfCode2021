package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.anders_raberg.adventofcode2021.utilities.Pair;

public class Day13 {
    private static final Logger LOGGER = Logger.getLogger(Day13.class.getName());
    private static final Pattern COORD_PATTERN = Pattern.compile("(\\d+),(\\d+)");
    private static final Pattern FOLD_PATTERN = Pattern.compile("fold along (\\w)=.*");

    private Day13() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input13.txt"));
        Set<Pair<Integer, Integer>> coords = new HashSet<>();
        List<String> foldingInstructions = new ArrayList<>();

        for (String line : lines) {
            Matcher mC = COORD_PATTERN.matcher(line);
            Matcher mF = FOLD_PATTERN.matcher(line);
            if (mC.matches()) {
                coords.add(new Pair<>(Integer.parseInt(mC.group(1)), Integer.parseInt(mC.group(2))));
            }
            if (mF.matches()) {
                foldingInstructions.add(mF.group(1));
            }
        }

        int rows = coords.stream().mapToInt(Pair::second).max().orElseThrow();
        int columns = coords.stream().mapToInt(Pair::first).max().orElseThrow();

        char[][] paper = new char[rows + 1][columns + 1];
        for (int y = 0; y < rows + 1; y++) {
            for (int x = 0; x < columns + 1; x++) {
                paper[y][x] = coords.contains(new Pair<>(x, y)) ? '#' : '.';
            }
        }

        paper = foldingInstructions.get(0).equals("x") ? foldHoriz(paper) : foldVert(paper);
        LOGGER.info(String.format("Part 1: %d", count(paper)));

        for (int i = 1; i < foldingInstructions.size(); i++) {
            paper = foldingInstructions.get(i).equals("x") ? foldHoriz(paper) : foldVert(paper);
        }
        LOGGER.info(String.format("Part 2: %n%s", toString(paper)));
    }

    private static char[][] foldVert(char[][] paper) {
        char[][] res = new char[paper.length / 2][paper[0].length];
        for (int y = 0; y < res.length; y++) {
            for (int x = 0; x < res[0].length; x++) {
                res[y][x] = paper[y][x] == '#' || paper[paper.length - 1 - y][x] == '#' ? '#' : '.';
            }
        }
        return res;
    }

    private static char[][] foldHoriz(char[][] paper) {
        char[][] res = new char[paper.length][paper[0].length / 2];
        for (int y = 0; y < res.length; y++) {
            for (int x = 0; x < res[0].length; x++) {
                res[y][x] = paper[y][x] == '#' || paper[y][paper[0].length - 1 - x] == '#' ? '#' : '.';
            }
        }
        return res;
    }

    private static int count(char[][] paper) {
        int count = 0;
        for (int y = 0; y < paper.length; y++) {
            for (int x = 0; x < paper[0].length; x++) {
                count = paper[y][x] == '#' ? count + 1 : count;
            }
        }
        return count;
    }

    private static String toString(char[][] paper) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < paper.length; y++) {
            for (int x = 0; x < paper[0].length; x++) {
                sb.append(paper[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
