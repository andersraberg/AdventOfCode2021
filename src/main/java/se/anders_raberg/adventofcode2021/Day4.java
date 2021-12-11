package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class Day4 {
    private static final Logger LOGGER = Logger.getLogger(Day4.class.getName());
    private static final int CELL_MARKED_VALUE = -1;

    private Day4() {
    }

    public static void run() throws IOException {
        String[] fileSections = new String(Files.readAllBytes(Paths.get("inputs/input4.txt"))).split("\n\n");
        List<Integer> drawnNumbers = Arrays.stream(fileSections[0].split(",")).map(Integer::parseInt).toList();
        List<Table<Integer, Integer, Integer>> boards = new ArrayList<>();

        for (int r = 1; r < fileSections.length; r++) {
            String[] rows = fileSections[r].split("\n");
            Table<Integer, Integer, Integer> board = HashBasedTable.create();

            for (int y = 0; y < rows.length; y++) {
                String[] row = rows[y].trim().split(" +");
                for (int x = 0; x < row.length; x++) {
                    board.put(y, x, Integer.parseInt(row[x]));
                }
            }
            boards.add(board);
        }

        List<Integer> scores = new ArrayList<>();
        Set<Integer> alreadyBingo = new HashSet<>();
        for (Integer number : drawnNumbers) {
            for (int i = 0; i < boards.size(); i++) {
                mark(boards.get(i), number);
                if (!alreadyBingo.contains(i) && checkForBingo(boards.get(i))) {
                    scores.add(number * getScore(boards.get(i)));
                    alreadyBingo.add(i);
                }
            }
        }

        LOGGER.info(() -> String.format("Part 1: %s", scores.get(0)));
        LOGGER.info(() -> String.format("Part 2: %s", scores.get(scores.size() - 1)));
    }

    private static void mark(Table<Integer, Integer, Integer> table, int drawnNumber) {
        table.cellSet().stream() //
                .filter(c -> c.getValue() == drawnNumber) //
                .findFirst() //
                .ifPresent(c -> table.put(c.getRowKey(), c.getColumnKey(), CELL_MARKED_VALUE));
    }

    private static boolean checkForBingo(Table<Integer, Integer, Integer> table) {
        return checkRowOrColumn(table.rowMap()) || checkRowOrColumn(table.columnMap());
    }

    private static boolean checkRowOrColumn(Map<Integer, Map<Integer, Integer>> rowOrColumn) {
        return rowOrColumn.values().stream().anyMatch(i -> i.values().stream().allMatch(v -> v == CELL_MARKED_VALUE));
    }

    private static int getScore(Table<Integer, Integer, Integer> table) {
        return table.cellSet().stream().mapToInt(Cell::getValue).filter(v -> v != CELL_MARKED_VALUE).sum();
    }

}
