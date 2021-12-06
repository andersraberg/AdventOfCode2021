package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day6 {
    private static final Logger LOGGER = Logger.getLogger(Day6.class.getName());

    private Day6() {
    }

    public static void run() throws IOException {
        List<Integer> data = Arrays
                .stream(new String(Files.readAllBytes(Paths.get("inputs/input6.txt"))).trim().split(","))
                .map(Integer::parseInt).toList();

        LOGGER.info("Part 1: " + countGeneratedFishTimers(data, 80));
        LOGGER.info("Part 2: " + countGeneratedFishTimers(data, 256));
    }

    private static long countGeneratedFishTimers(List<Integer> fishTimers, int days) {
        Map<Integer, Long> timersByValue = countOccurances(fishTimers);

        List<Integer> fishTimersFirstHalfOfDays = new ArrayList<>();
        for (Integer timer : timersByValue.keySet()) {
            List<Integer> generate = generate(List.of(timer), days / 2);
            for (int i = 0; i < timersByValue.get(timer); i++) {
                fishTimersFirstHalfOfDays.addAll(generate);
            }
        }

        timersByValue = countOccurances(fishTimersFirstHalfOfDays);
        long sum = 0;
        for (Integer integer2 : timersByValue.keySet()) {
            sum += generate(List.of(integer2), days / 2).size() * timersByValue.get(integer2);
        }
        return sum;
    }

    private static Map<Integer, Long> countOccurances(List<Integer> timers) {
        return timers.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static List<Integer> generate(List<Integer> timers, int iterations) {
        List<Integer> tmp = new ArrayList<>(timers);
        for (int i = 0; i < iterations; i++) {
            int size = tmp.size();
            for (int j = 0; j < size; j++) {
                Integer item = tmp.get(j);
                if (item == 0) {
                    tmp.set(j, 6);
                    tmp.add(8);
                } else {
                    tmp.set(j, item - 1);
                }
            }
        }
        return tmp;
    }

}
