package pl.maro.analise.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NameStatisticsMapper {
    public static NameStatistics createNameStatistics(List<Person> people, Map<Range, Integer> denominators) {
        var occurrenceByRange = people.stream()
                .collect(Collectors.groupingBy(Person::birthYear)).entrySet().stream()
                .flatMap(NameStatisticsMapper::getRangeEntries)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).entrySet().stream()
                .map(getEntryWithOccurrence(denominators))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new NameStatistics(getName(people), occurrenceByRange);
    }

    private static String getName(List<Person> people) {
        return people.stream().map(Person::statisticName).distinct()
                .reduce((a,b) -> null)
                .orElseThrow(IllegalStateException::new);
    }

    private static Function<Map.Entry<Range, Integer>, Map.Entry<Range, Occurrence>> getEntryWithOccurrence(Map<Range, Integer> denominators) {
        return entry -> {
            var meter = entry.getValue();
            var denominator = denominators.get(entry.getKey()).doubleValue();
            return Map.entry(entry.getKey(), new Occurrence(meter, meter / denominator));
        };
    }

    private static Stream<Map.Entry<Range, Integer>> getRangeEntries(Map.Entry<Integer, List<Person>> entry) {
        return Range.stream(entry.getKey())
                .map(range -> Map.entry(range, entry.getValue().size()));
    }
}
