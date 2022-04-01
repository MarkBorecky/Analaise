package pl.maro.analise.model;

import java.util.Map;

public record NameStatistics(String name, Map<Range, Occurrence> statistics) {
}
