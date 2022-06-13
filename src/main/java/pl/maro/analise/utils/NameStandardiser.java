package pl.maro.analise.utils;


import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.apache.commons.lang.StringUtils;
import pl.maro.analise.model.NameOccurring;
import pl.maro.analise.model.NameStandard;

import java.util.function.Function;

import static pl.maro.analise.utils.FilesUtils.readFile;

public record NameStandardiser(Map<String, String> nameMap) {
	
	
	public static NameStandard map(String csvLine) {
		var split = csvLine.split(";");
		var list = List.of(split);
		var head = list.head();
		var tail = list.tail();
		return new NameStandard(head, tail);
	}
	
	public static Map<String, String> createNameMap(String names) {
		return readFile(names)
				.map(NameStandardiser::map)
				.flatMap(NameStandard::getEntryStream)
				.foldLeft(HashMap.empty(), (map, tuple) -> map.put(tuple._1, map.getOrElse(tuple._1, tuple._2)));
	}
	
	public static NameStandardiser createNameStandardizer(String names) {
		return new NameStandardiser(createNameMap(names));
	}
	
	public NameOccurring standardise(NameOccurring nameOccurring) {
		return nameMap.get(nameOccurring.name()).fold(() -> nameOccurring, nameOccurring::setName);
	}
	
	public List<NameOccurring> standard(String csv) {
		return FilesUtils.readFile(csv)
				.map(removeUnnecessaryCharacters())
				.map(NameOccurring::map)
				.map(this::standardise);
	}
	
	private Function<String, String> removeUnnecessaryCharacters() {
		return row -> row.replace("*", StringUtils.EMPTY);
	}
}
