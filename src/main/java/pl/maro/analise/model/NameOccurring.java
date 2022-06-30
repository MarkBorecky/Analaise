package pl.maro.analise.model;

import io.vavr.collection.List;
import org.apache.commons.lang3.StringUtils;

public record NameOccurring(String name, int year) implements CSV {
	
	public static final String FIRST_SPACE_UNTIL_END = " (.*)";
	
	public static NameOccurring map(String csvRow) {
		var splitRow = List.of(csvRow.split("[;/]"));
		String head = splitRow.head()
				.toLowerCase()
				.transform(StringUtils::capitalize);
		var name = cutFirstPartOfName(head);
		var year = Integer.parseInt(splitRow.last());
		return new NameOccurring(name, year);
	}
	
	private static String cutFirstPartOfName(String head) {
		return head.replaceAll(FIRST_SPACE_UNTIL_END, "")
				.toLowerCase()
				.transform(StringUtils::capitalize);
	}
	
	public NameOccurring setName(String primaryName) {
		return new NameOccurring(primaryName, year);
	}
	
	@Override
	public String toCSV() {
		return "%s;%d".formatted(name, year);
	}
}
