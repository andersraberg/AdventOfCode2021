package se.anders_raberg.adventofcode2021;

import java.util.logging.Logger;
import java.util.stream.LongStream;

public class Day21Part1 {
    private static final long WINNING_SCORE = 1000;
    private static final int ROLLS_PER_TURN = 3;
    private static final Logger LOGGER = Logger.getLogger(Day21Part1.class.getName());
    private static long losingPlayerScore;

    private static final long[] POSITIONS = { 10, 6 };
    private static final long[] SCORES = { 0, 0 };

    private static class Dice {
        private long _nextValue = 1;

        public long roll(long n) {
            return LongStream.rangeClosed(1, n).map(x -> roll()).sum();
        }

        public long roll() {
            long tmp = _nextValue;
            _nextValue = _nextValue % 100 + 1;
            return tmp;
        }

    }

    private Day21Part1() {
    }

    public static void run() {
        Dice dice = new Dice();
        long rollCounter = 0;

        while (losingPlayerScore == 0) {
            for (int i = 0; i < 2; i++) {
                POSITIONS[i] = move(POSITIONS[i], dice.roll(ROLLS_PER_TURN));
                SCORES[i] += POSITIONS[i];
                rollCounter += ROLLS_PER_TURN;
                if (SCORES[i] >= WINNING_SCORE) {
                    losingPlayerScore = SCORES[(i + 1) % 2];
                    break;
                }
            }

        }
        LOGGER.info(String.format("Part 1: %d", losingPlayerScore * rollCounter));
    }

    private static long move(long start, long steps) {
        return (start - 1 + steps) % 10 + 1;
    }

}
