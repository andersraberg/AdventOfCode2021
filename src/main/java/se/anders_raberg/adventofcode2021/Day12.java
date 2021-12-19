package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

public class Day12 {
    private static final Logger LOGGER = Logger.getLogger(Day12.class.getName());
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private static final String START = "start";
    private static final String END = "end";

    private Day12() {
    }

    public static void run() throws IOException {
        List<String> edges = Files.readAllLines(Paths.get("inputs/input12.txt")).stream() //
                .toList();

        ImmutableGraph.Builder<String> graphBuilder = //
                GraphBuilder.undirected() //
                        .<String>immutable();

        for (String edge : edges) {
            String[] split = edge.split("-");
            graphBuilder.putEdge(split[0], split[1]);
        }

        ImmutableGraph<String> graph = graphBuilder.build();

        // Part 1
        walk(graph, START, new HashSet<>(), Collections.emptySet());
        LOGGER.info("Part 1: " + COUNTER);

        // Part 2
        COUNTER.set(0);
        Set<String> lowerCaseNodes = graph.nodes().stream() //
                .filter(Day12::isLowerCase) //
                .collect(Collectors.toSet());

        walk(graph, START, new HashSet<>(), lowerCaseNodes);
        LOGGER.info("Part 2: " + COUNTER);
    }

    private static void walk(ImmutableGraph<String> graph, String node, Set<String> visited, Set<String> visit2ndTime) {
        if (node.equals(END)) {
            COUNTER.incrementAndGet();
        } else {
            if (isLowerCase(node) && visited.contains(node)) {
                if (node.equals(START)) {
                    return;
                }

                if (visit2ndTime.contains(node)) {
                    visit2ndTime.clear();
                } else {
                    return;
                }
            }
            visited.add(node);
            graph.adjacentNodes(node).forEach(n -> walk(graph, n, new HashSet<>(visited), new HashSet<>(visit2ndTime)));
        }
    }

    private static boolean isLowerCase(String str) {
        return str.toLowerCase().equals(str);
    }

}
