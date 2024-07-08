package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day25 {
    private static final Logger LOGGER = Logger.getLogger(Day25.class.getName());

    private static boolean moved;
    private static int width;
    private static int height;

    private Day25() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input25.txt")).stream() //
                .toList();

        height = lines.size();
        width = lines.getFirst().length();

        char[][] map = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = lines.get(y).charAt(x);
            }
        }

        int steps = 0;
        do {
            moved = false;
            map = moveSouthbound(moveEastbound(map));
            steps++;
        } while (moved);

        LOGGER.info(String.format("Part 1: %d", steps));
    }

    private static char[][] moveEastbound(char[][] map) {
        char[][] nextMap = deepCopy(map);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int nextX = (x + 1) % width;
                if (map[y][x] == '>' && map[y][nextX] == '.') {
                    nextMap[y][x] = '.';
                    nextMap[y][nextX] = '>';
                    moved = true;
                }
            }
        }
        return nextMap;
    }

    private static char[][] moveSouthbound(char[][] map) {
        char[][] nextMap = deepCopy(map);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int nextY = (y + 1) % height;
                if (map[y][x] == 'v' && map[nextY][x] == '.') {
                    nextMap[y][x] = '.';
                    nextMap[nextY][x] = 'v';
                    moved = true;
                }
            }
        }
        return nextMap;
    }

    private static char[][] deepCopy(char[][] map) {
        char[][] tmp = new char[height][width];
        for (int y = 0; y < height; y++) {
            tmp[y] = Arrays.copyOf(map[y], width);
        }
        return tmp;
    }

}
