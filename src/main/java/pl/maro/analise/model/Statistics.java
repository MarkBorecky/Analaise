package pl.maro.analise.model;

import io.vavr.collection.Array;

public record Statistics(String name, int occurrence, String percentage) implements CSV {
	@Override
	public String toCSV() {
		return "%s;%d;%s".formatted(name, occurrence, percentage);
	}
	
	public static Statistics map(String csvRow) {
		Array<String> rowContent = csvRow.transform(s -> Array.of(s.split(";")));
		return new Statistics(rowContent.get(0), Integer.parseInt(rowContent.get(1)), rowContent.get(2));
	}
}
