package se.anders_raberg.adventofcode2021;

import java.util.logging.Logger;

import com.google.common.collect.Range;

import se.anders_raberg.adventofcode2021.utilities.Pair;

public class Day17 {
    private static final Logger LOGGER = Logger.getLogger(Day17.class.getName());
    private static final Range<Integer> X_RANGE = Range.closed(138, 184);
    private static final Range<Integer> Y_RANGE = Range.closed(-125, -71);
    private static final int MAX_VALUE = 200;

    private Day17() {
    }

    public static void run() {
        int maxHeight = 0;
        int hitCounter = 0;
        for (int x = 0; x < MAX_VALUE; x++) {
            for (int y = -MAX_VALUE; y < MAX_VALUE; y++) {
                Pair<Boolean, Integer> fire = fire(x, y);
                if (Boolean.TRUE.equals(fire.first())) {
                    hitCounter++;
                    maxHeight = Math.max(maxHeight, fire.second());
                }
            }
        }
        LOGGER.info(String.format("Part 1: %d", maxHeight));
        LOGGER.info(String.format("Part 2: %d", hitCounter));
    }

    private static Pair<Boolean, Integer> fire(int xVel, int yVel) {
        int x = 0;
        int y = 0;
        int vx = xVel;
        int vy = yVel;
        int maxHeight = 0;

        while (x <= X_RANGE.upperEndpoint() && y >= Y_RANGE.lowerEndpoint()) {
            x += vx;
            y += vy;
            vx -= Integer.compare(vx, 0);
            vy--;

            maxHeight = Math.max(maxHeight, y);
            if (X_RANGE.contains(x) && Y_RANGE.contains(y)) {
                return new Pair<>(true, maxHeight);
            }
        }
        return new Pair<>(false, 0);
    }

}
