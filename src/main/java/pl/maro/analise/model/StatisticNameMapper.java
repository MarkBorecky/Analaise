package pl.maro.analise.model;

import pl.maro.analise.stream.StreamUtils;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticNameMapper {
    public final static String data = "./src/main/resources/names.txt";
    private final static Map<String, String> map = createMap(data);

    private static Map<String, String> createMap(String path) {
        return StreamUtils.linesStream(path)
                .map(mapLineIntoDeque())
                .flatMap(mapStack())
                .collect(getEntryMapCollector());
    }

    private static Function<String, ArrayDeque<String>> mapLineIntoDeque() {
        return line -> Arrays.stream(line.split(" ")).collect(Collectors.toCollection(ArrayDeque::new));
    }

    private static Function<ArrayDeque<String>, Stream<? extends Map.Entry<String, String>>> mapStack() {
        return stack -> stack.stream()
                .map(createPair(stack.peek()))
                .filter(keyNotEqualsValue());
    }

    private static Collector<Map.Entry<String, String>, ?, Map<String, String>> getEntryMapCollector() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    private static Predicate<? super Map.Entry<String, String>> keyNotEqualsValue() {
        return entry -> !entry.getKey().equals(entry.getValue());
    }

    private static Function<String, Map.Entry<String, String>> createPair(String head) {
        return name -> Map.entry(name.toUpperCase(), head);
    }

    public static Optional<String> map(String originalName) {
        return Optional.ofNullable(map.get(originalName));
    }
}
