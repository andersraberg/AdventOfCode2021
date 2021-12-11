package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.IntFunction;
import java.util.logging.Logger;

public class Day3 {
    private static final Logger LOGGER = Logger.getLogger(Day3.class.getName());

    private Day3() {
    }

    public static void run() throws IOException {

        List<String> binaryNumbers = Files.readAllLines(Paths.get("inputs/input3.txt")).stream() //
                .toList();

        int wordSize = binaryNumbers.get(0).length();

        // Part 1
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (int i = 0; i < wordSize; i++) {
            int diffOnesZeros = countDiffAtPos(binaryNumbers, i);
            gamma.append(diffOnesZeros > 0 ? '1' : '0');
            epsilon.append(diffOnesZeros > 0 ? '0' : '1');
        }

        int gammaVal = Integer.valueOf(gamma.toString(), 2);
        int epsilonVal = Integer.valueOf(epsilon.toString(), 2);
        LOGGER.info(String.format("Part 1: %d", gammaVal * epsilonVal));

        // Part 2
        int oxygen = Integer.valueOf(findRating(binaryNumbers, x -> x >= 0 ? '1' : '0'), 2);
        int scrubber = Integer.valueOf(findRating(binaryNumbers, x -> x >= 0 ? '0' : '1'), 2);
        LOGGER.info(String.format("Part 2: %d", oxygen * scrubber));
    }

    private static String findRating(List<String> binaryNumbers, IntFunction<Character> fun) {
        List<String> tmp = binaryNumbers;
        for (int i = 0; i < tmp.get(0).length(); i++) {
            int diffOnesZeros = countDiffAtPos(tmp, i);
            int index = i;
            char ch = fun.apply(diffOnesZeros);
            tmp = tmp.stream().filter(s -> s.charAt(index) == ch).toList();
            if (tmp.size() == 1) {
                return tmp.get(0);
            }
        }
        throw new IllegalArgumentException();
    }

    private static int countDiffAtPos(List<String> binaryNumbers, int pos) {
        int diffOnesZeros = 0;
        for (int j = 0; j < binaryNumbers.size(); j++) {
            diffOnesZeros += binaryNumbers.get(j).charAt(pos) == '1' ? 1 : -1;
        }
        return diffOnesZeros;
    }
}
