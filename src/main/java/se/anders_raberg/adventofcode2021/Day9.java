package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import se.anders_raberg.adventofcode2021.utilities.Pair;

public class Day9 {
    private static final Logger LOGGER = Logger.getLogger(Day9.class.getName());
    private static final Table<Integer, Integer, Integer> BOARD = HashBasedTable.create();
    private static final Set<Pair<Integer, Integer>> VISITED = new HashSet<>();

    private Day9() {
    }

    public static void run() throws IOException {
        List<String> heightMap = Files.readAllLines(Paths.get("inputs/input9.txt")).stream() //
                .toList();

        for (int y = 0; y < heightMap.size(); y++) {
            String[] row = heightMap.get(y).trim().split("");
            for (int x = 0; x < row.length; x++) {
                BOARD.put(y, x, Integer.parseInt(row[x]));
            }
        }

        List<Cell<Integer, Integer, Integer>> lowPoints = BOARD.cellSet().stream().filter(c -> isLowPoint(c, BOARD))
                .toList();

        LOGGER.info(() -> String.format("Part 1: %d", lowPoints.stream().mapToInt(c -> c.getValue() + 1).sum()));

        LOGGER.info(() -> String.format("Part 2: %d",
                lowPoints.stream().map(p -> numberOfNeighborsInBasin(p.getRowKey(), p.getColumnKey()))
                        .sorted(Collections.reverseOrder()) //
                        .limit(3) //
                        .reduce(Math::multiplyExact).orElseThrow()));
    }

    private static int numberOfNeighborsInBasin(Integer row, Integer column) {
        int xyz = checkForBorder(BOARD.get(row, column));
        if (xyz > 8 || VISITED.contains(new Pair<>(row, column))) {
            return 0;
        } else {
            BOARD.put(row, column, Integer.MAX_VALUE);
            VISITED.add(new Pair<>(row, column));
            int sum = 1;
            sum = sum + numberOfNeighborsInBasin(row - 1, column);
            sum = sum + numberOfNeighborsInBasin(row + 1, column);
            sum = sum + numberOfNeighborsInBasin(row, column - 1);
            sum = sum + numberOfNeighborsInBasin(row, column + 1);
            return sum;
        }
    }

    private static boolean isLowPoint(Cell<Integer, Integer, Integer> cell, Table<Integer, Integer, Integer> board) {
        return List
                .of(checkForBorder(board.get(cell.getRowKey() - 1, cell.getColumnKey())),
                        checkForBorder(board.get(cell.getRowKey() + 1, cell.getColumnKey())),
                        checkForBorder(board.get(cell.getRowKey(), cell.getColumnKey() - 1)),
                        checkForBorder(board.get(cell.getRowKey(), cell.getColumnKey() + 1)))
                .stream().allMatch(v -> v > cell.getValue());
    }

    private static int checkForBorder(Integer value) {
        return value != null ? value : Integer.MAX_VALUE;
    }

}
