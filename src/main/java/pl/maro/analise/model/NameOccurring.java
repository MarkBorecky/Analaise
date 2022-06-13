package pl.maro.analise.model;

import io.vavr.collection.List;

public record NameOccurring(String name, int year) {
	public static NameOccurring map(String csvRow) {
		var splitRow = List.of(csvRow.split(";"));
		var name = splitRow.head();
		var year = Integer.parseInt(splitRow.last());
		return new NameOccurring(name, year);
	}
	
	public NameOccurring setName(String primaryName) {
		return new NameOccurring(primaryName, year);
	}
	
	public String toCsv() {
		return "%s;%d".formatted(name, year);
	}
}
