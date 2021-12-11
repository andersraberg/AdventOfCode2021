package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class Day11 {
    private static final Logger LOGGER = Logger.getLogger(Day11.class.getName());
    private static final Table<Integer, Integer, Integer> GRID = HashBasedTable.create();

    private Day11() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input11.txt")).stream() //
                .toList();

        for (int y = 0; y < lines.size(); y++) {
            String[] row = lines.get(y).trim().split("");
            for (int x = 0; x < row.length; x++) {
                GRID.put(y, x, Integer.parseInt(row[x]));
            }
        }

        List<Cell<Integer, Integer, Integer>> flashedCells;
        long counter = 0;
        for (int i = 1; i < 1000; i++) {
            increaseCellsByOne(GRID.cellSet());
            do {
                flashedCells = GRID.cellSet().stream().filter(c -> c.getValue() > 9).toList();
                counter += flashedCells.size();

                flashedCells.forEach(c -> GRID.put(c.getRowKey(), c.getColumnKey(), 0));
                for (Cell<Integer, Integer, Integer> cell : flashedCells) {
                    Set<Cell<Integer, Integer, Integer>> findNeighboursNotFlashed = findNeighboursNotFlashed(
                            cell.getRowKey(), cell.getColumnKey());

                    increaseCellsByOne(findNeighboursNotFlashed);
                }
            } while (!flashedCells.isEmpty());
            if (i == 100) {
                LOGGER.info(String.format("Part 1: %d", counter));
            }
            if (GRID.cellSet().stream().filter(c -> c.getValue() == 0).count() == 100) {
                LOGGER.info(String.format("Part 2: %d", i));
                break;
            }
        }
    }

    private static void increaseCellsByOne(Collection<Cell<Integer, Integer, Integer>> cells) {
        cells.forEach(c -> GRID.put(c.getRowKey(), c.getColumnKey(), c.getValue() + 1));
    }

    private static Set<Cell<Integer, Integer, Integer>> findNeighboursNotFlashed(int row, int column) {
        return GRID.cellSet().stream() //
                .filter(c -> Range.closed(row - 1, row + 1).contains(c.getRowKey())) //
                .filter(c -> Range.closed(column - 1, column + 1).contains(c.getColumnKey())) //
                .filter(c -> !c.getValue().equals(0)) //
                .collect(Collectors.toSet());
    }

}
