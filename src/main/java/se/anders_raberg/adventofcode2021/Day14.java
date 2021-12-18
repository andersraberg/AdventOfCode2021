package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 {
    private static final Logger LOGGER = Logger.getLogger(Day14.class.getName());
    private static final Pattern RULE_PATTERN = Pattern.compile("(\\w)(\\w) -> (\\w)");
    private static final Map<String, List<String>> RULES = new HashMap<>();
    private static String template;

    private Day14() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input14.txt"));
        template = lines.get(0);

        for (String line : lines.subList(2, lines.size())) {
            Matcher m = RULE_PATTERN.matcher(line);
            m.find();
            RULES.put(m.group(1) + m.group(2), List.of(m.group(1) + m.group(3), m.group(3) + m.group(2)));
        }

        List<String> startPairs = new ArrayList<>();
        for (int i = 0; i < template.length() - 1; i++) {
            startPairs.add(template.substring(i, i + 2));
        }

        Map<String, Long> startMap = startPairs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        LOGGER.info(() -> String.format("Part 1: %d", runSteps(startMap, 10)));
        LOGGER.info(() -> String.format("Part 2: %d", runSteps(startMap, 40)));
    }

    private static long runSteps(Map<String, Long> startMap, int steps) {
        Map<String, Long> tmp = new HashMap<>(startMap);
        for (int i = 0; i < steps; i++) {
            tmp = oneStep(tmp);
        }

        Map<String, Long> elementCounters = new HashMap<>(Map.of(template.substring(template.length() - 1), 1L));

        tmp.entrySet().forEach(e -> {
            String firstInPair = e.getKey().substring(0, 1);
            elementCounters.merge(firstInPair, e.getValue(), Long::sum);
        });

        Collection<Long> values = elementCounters.values();
        return Collections.max(values) - Collections.min(values);
    }

    private static Map<String, Long> oneStep(Map<String, Long> input) {
        Map<String, Long> tmp = new HashMap<>();

        for (Entry<String, Long> entry : input.entrySet()) {
            List<String> newPairsFromRule = RULES.get(entry.getKey());
            for (String pair : newPairsFromRule) {
                tmp.merge(pair, entry.getValue(), Long::sum);
            }
        }
        return tmp;
    }

}
