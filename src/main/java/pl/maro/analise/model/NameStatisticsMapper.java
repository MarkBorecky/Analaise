package pl.maro.analise.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NameStatisticsMapper {
    
    public static List<NameStatistics> createNamesStatistics(List<Person> people, Sum denominators) {
        return people.stream()
                .collect(Collectors.groupingBy(Person::statisticName, Collectors.groupingBy(Person::birthYear, Collectors.counting())))
                .entrySet().stream()
                .map(entry -> createNameStatistics(entry, denominators))
                .toList();
    }
    
    private static NameStatistics createNameStatistics(Map.Entry<String, Map<Integer, Long>> entry, Sum denominators) {
        var rangeMap = new HashMap<Range, Integer>();
        var name = entry.getKey();
        for (var yearSet: entry.getValue().entrySet()) {
            var year = yearSet.getKey();
            var occurrence = yearSet.getValue().intValue();
            for (var range: Range.values()) {
                var newValue = rangeMap.getOrDefault(range, 0) + 1;
                rangeMap.put(range, newValue);
            }
        }
        var result = rangeMap.entrySet().stream()
                .map(x -> map(x, denominators))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new NameStatistics(name, result);
    }
    
    private static Map.Entry<Range, Occurrence> map(Map.Entry<Range, Integer> x, Sum denominators) {
        var range = x.getKey();
        var count = x.getValue();
        var percentage = getPercentage(count, denominators.get(range));
        var occurrence = new Occurrence(count, percentage);
        return Map.entry(range, occurrence);
    }
    
    private static double getPercentage(int count, int denominator) {
        var result = (count * 100) / (double) denominator;
        return Double.parseDouble("%.2f".formatted(result).replace(",","."));
    }
}
