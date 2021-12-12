package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

public class Day12 {
    private static final Logger LOGGER = Logger.getLogger(Day12.class.getName());
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

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

        walk(graph, "start", new HashSet<>());
        LOGGER.info("Part 1: " + COUNTER);
    }

    private static void walk(ImmutableGraph<String> graph, String node, Set<String> visited) {
        if (node.toLowerCase().equals(node)) {
            visited.add(node);
        }

        if (node.equals("end")) {
            COUNTER.incrementAndGet();
        } else {
            List<String> list = graph.adjacentNodes(node).stream().filter(n -> !visited.contains(n)).toList();
            for (String string : list) {
                walk(graph, string, new HashSet<>(visited));
            }
        }
    }

}
