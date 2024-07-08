package se.anders_raberg.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import se.anders_raberg.adventofcode2021.utilities.Pair;

public class Day16 {
    private static final Logger LOGGER = Logger.getLogger(Day16.class.getName());
    private static final int SUBPACKAGES_COUNT_BITS = 11;
    private static final int SUBPACKAGES_LEN_BITS = 15;
    private static final int LITERAL_GROUP_BITS = 4;
    private static final int VERSION_BITS = 3;
    private static final int ID_BITS = 3;

    private static int counter;

    private static class Packet {
        private String _data;

        public Packet(String data) {
            _data = data;
        }

        public String remove(int n) {
            String tmp = _data.substring(0, n);
            _data = _data.substring(n);
            return tmp;
        }

        public int removeAndParse(int n) {
            return Integer.parseInt(remove(n), 2);
        }

        public String getRemaining() {
            return _data;
        }

    }

    private static final Map<Integer, Function<List<Long>, Long>> OPERATORS = Map.of( //
            0, values -> values.stream().reduce(0L, Long::sum), //
            1, values -> values.stream().reduce(1L, Math::multiplyExact), //
            2, Collections::min, //
            3, Collections::max, //
            5, values -> values.getFirst() > values.get(1) ? 1L : 0L, //
            6, values -> values.getFirst() < values.get(1) ? 1L : 0L, //
            7, values -> values.getFirst().equals(values.get(1)) ? 1L : 0L);

    private Day16() {
    }

    public static void run() throws IOException {
        String packets = new String(Files.readAllBytes(Paths.get("inputs/input16.txt")));
        String reduce = Arrays.stream(packets.trim().split("")) //
                .map(Day16::hex2bin) //
                .reduce("", String::concat);

        Pair<Long, String> result = parse(new Packet(reduce));

        LOGGER.info(() -> String.format("Part 1: %d", counter));
        LOGGER.info(() -> String.format("Part2 : %s", result.first()));
    }

    private static Pair<Long, String> parse(Packet p) {
        int version = p.removeAndParse(VERSION_BITS);
        int typeId = p.removeAndParse(ID_BITS);

        counter += version;

        long value;
        if (typeId == 4) {
            StringBuilder sb = new StringBuilder();
            while (p.remove(1).equals("1")) {
                sb.append(p.remove(LITERAL_GROUP_BITS));
            }
            sb.append(p.remove(LITERAL_GROUP_BITS));
            value = Long.parseLong(sb.toString(), 2);
        } else {
            int lengthTypeId = p.removeAndParse(1);
            List<Long> subpacketValues = new ArrayList<>();
            switch (lengthTypeId) {
            case 0:
                int bytes = p.removeAndParse(SUBPACKAGES_LEN_BITS);
                int remaining = p.getRemaining().length();
                int rem = remaining;
                while (rem > remaining - bytes) {
                    Pair<Long, String> parse = parse(p);
                    subpacketValues.add(parse.first());
                    rem = parse.second().length();
                }

                break;
            case 1:
                int asInt = p.removeAndParse(SUBPACKAGES_COUNT_BITS);
                for (int i = 0; i < asInt; i++) {
                    Pair<Long, String> parse = parse(p);
                    subpacketValues.add(parse.first());
                }
                break;

            default:
                throw new IllegalArgumentException("Unexpected value: " + lengthTypeId);
            }
            value = OPERATORS.get(typeId).apply(subpacketValues);
        }
        return new Pair<>(value, p.getRemaining());
    }

    private static String hex2bin(String digit) {
        return switch (digit) {
        case "0" -> "0000";
        case "1" -> "0001";
        case "2" -> "0010";
        case "3" -> "0011";
        case "4" -> "0100";
        case "5" -> "0101";
        case "6" -> "0110";
        case "7" -> "0111";
        case "8" -> "1000";
        case "9" -> "1001";
        case "A" -> "1010";
        case "B" -> "1011";
        case "C" -> "1100";
        case "D" -> "1101";
        case "E" -> "1110";
        case "F" -> "1111";
        default -> throw new IllegalArgumentException("Unexpected value: " + digit);
        };
    }

}
