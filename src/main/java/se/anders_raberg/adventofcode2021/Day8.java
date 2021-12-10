package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import se.anders_raberg.adventofcode2021.utilities.Pair;

public class Day8 {
    private static final Logger LOGGER = Logger.getLogger(Day8.class.getName());

    private Day8() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input8.txt")).stream() //
                .toList();

        List<Pair<List<String>, List<String>>> signalPatterns = lines.stream().map(Day8::splitSides).toList();

        long sum = signalPatterns.stream().map(a -> a.second()).mapToLong(z -> countOccurences(z, Set.of(2, 4, 3, 7)))
                .sum();
        LOGGER.info("Part 1: " + sum);

        Map<String, String> theMap = new HashMap<>();
        int sum2 = 0;
        for (Pair<List<String>, List<String>> pattern : signalPatterns) {
            String digit1 = pattern.first().stream().filter(z -> z.length() == 2).findFirst().orElseThrow();
            theMap.put(sort(pattern.first().stream().filter(z -> z.length() == 2).findFirst().orElseThrow()), "1");

            String digit4 = pattern.first().stream().filter(z -> z.length() == 4).findFirst().orElseThrow();
            theMap.put(sort(digit4), "4");

            String digit7 = pattern.first().stream().filter(z -> z.length() == 3).findFirst().orElseThrow();
            theMap.put(sort(digit7), "7");

            String digit8 = pattern.first().stream().filter(z -> z.length() == 7).findFirst().orElseThrow();
            theMap.put(sort(digit8), "8");

            String digit9 = pattern.first().stream().filter(z -> z.length() == 6).filter(z -> contains(digit4, z))
                    .findFirst().orElseThrow();
            theMap.put(sort(digit9), "9");

            String digit0 = pattern.first().stream().filter(z -> z.length() == 6).filter(z -> !z.equals(digit9))
                    .filter(z -> contains(digit1, z)).findFirst().orElseThrow();
            theMap.put(sort(digit0), "0");

            String digit6 = pattern.first().stream().filter(z -> z.length() == 6).filter(z -> !z.equals(digit9))
                    .filter(z -> !z.equals(digit0)).findFirst().orElseThrow();
            theMap.put(sort(digit6), "6");

            String digit3 = pattern.first().stream().filter(z -> z.length() == 5).filter(z -> contains(digit1, z))
                    .findFirst().orElseThrow();
            theMap.put(sort(digit3), "3");

            String digit5 = pattern.first().stream().filter(z -> z.length() == 5).filter(z -> contains(z, digit6))
                    .findFirst().orElseThrow();
            theMap.put(sort(digit5), "5");

            String digit2 = pattern.first().stream().filter(z -> z.length() == 5).filter(z -> !z.equals(digit5))
                    .filter(z -> !z.equals(digit3)).findFirst().orElseThrow();
            theMap.put(sort(digit2), "2");

            int outputValue = Integer
                    .parseInt(pattern.second().stream().map(s -> theMap.get(sort(s))).reduce("", String::concat));
            sum2 += outputValue;
        }

        LOGGER.info("Part 2: " + sum2);
    }

    private static String sort(String str) {
        char[] charArray = str.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    private static boolean contains(String a, String b) {
        Set<String> split = Arrays.stream(a.split("")).collect(Collectors.toSet());
        Set<String> split2 = Arrays.stream(b.split("")).collect(Collectors.toSet());
        return split2.containsAll(split);
    }

    private static long countOccurences(List<String> signalPattern, Set<Integer> lengthsToCount) {
        return signalPattern.stream().map(String::length).filter(lengthsToCount::contains).count();
    }

    private static Pair<List<String>, List<String>> splitSides(String line) {
        String[] split = line.split(" *\\| *");
        return new Pair<>(splitDigitString(split[0]), splitDigitString(split[1]));
    }

    private static List<String> splitDigitString(String digits) {
        return Arrays.stream(digits.split(" ")).toList();
    }
}
