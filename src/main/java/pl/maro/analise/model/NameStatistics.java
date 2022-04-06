package pl.maro.analise.model;

import io.vavr.collection.List;

import java.util.Map;
import java.util.stream.Stream;

public record NameStatistics(String name, Map<Range, Occurrence> statistics) {
	public Stream<String> toStringStream() {
		var stat = statistics.values().stream()
				.flatMap(occurrence -> Stream.of(occurrence.countStr(), occurrence.percentageString())).toList();
		return List.of(name).appendAll(stat).toJavaStream();
	}
}
